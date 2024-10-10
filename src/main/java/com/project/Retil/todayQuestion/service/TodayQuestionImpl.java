package com.project.Retil.todayQuestion.service;

import com.project.Retil.question.entity.Question;
import com.project.Retil.question.repository.QuestionRepository;
import com.project.Retil.til.repository.TilRepository;
import com.project.Retil.todayQuestion.entity.TodayQuestion;
import com.project.Retil.todayQuestion.entity.TodayQuestionList;
import com.project.Retil.todayQuestion.repository.TodayQuestionListRepository;
import com.project.Retil.todayQuestion.repository.TodayQuestionRepository;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodayQuestionImpl {

    private final TodayQuestionListRepository todayQuestionListRepository;
    private final TodayQuestionRepository todayQuestionRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    // 오늘의 문제를 생성하는 메서드
    @Transactional
    public TodayQuestionList generateTodayQuestions(Long userId) {
        User_Information user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // 오늘의 문제 이미 존재하는지 확인하고 삭제
        TodayQuestionList existingTodayQuestions = todayQuestionListRepository.findByDateAndUser(
                LocalDate.now(), user
        );
        if (existingTodayQuestions != null) {
            todayQuestionRepository.deleteAll(existingTodayQuestions.getQuestionList());
            todayQuestionListRepository.delete(existingTodayQuestions);
            todayQuestionListRepository.flush();
            todayQuestionRepository.flush();
        }

        // 오늘의 문제 생성 시작
        List<Question> allQuestions = questionRepository.findAllByUserAndDate(user, LocalDate.now()); // 유저별 모든 문제를 조회
        Map<Long, List<Question>> questionsByTilNum = allQuestions.stream()
            .collect(Collectors.groupingBy(q -> q.getTil().getId()));

        List<Question> selectedQuestions = new ArrayList<>();
        int totalQuestions = Math.min(20, allQuestions.size());

        while (selectedQuestions.size() < totalQuestions) {
            for (List<Question> tilQuestions : questionsByTilNum.values()) {
                if (selectedQuestions.size() < totalQuestions && !tilQuestions.isEmpty()) {
                    Collections.shuffle(tilQuestions);
                    selectedQuestions.add(tilQuestions.remove(0));
                }
            }
        }

        TodayQuestionList qList = new TodayQuestionList(user);

        List<TodayQuestion> todayQuestions = selectedQuestions.stream()
            .map(q -> new TodayQuestion(qList, q, user, LocalDate.now()))
            .collect(Collectors.toList());

        for(TodayQuestion tq : todayQuestions) {
            qList.addQuestion(tq);
        }

        todayQuestionListRepository.save(qList);
        todayQuestionRepository.saveAll(todayQuestions);
        return qList;
    }

    // 오늘의 문제를 조회하는 메서드
    public TodayQuestionList getTodayQuestions(Long userId) {
        User_Information user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        return todayQuestionListRepository.findByDateAndUser(LocalDate.now(), user);
    }

    // 오늘의 문제 다시 보기 체크를 적용하는 메서드
    public TodayQuestionList applyCheck(Long userId, List<Boolean> checkList) {
        User_Information user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        TodayQuestionList tq = todayQuestionListRepository.findByDateAndUser(LocalDate.now(), user);
        tq.changeFlag();

        List<TodayQuestion> tqList = tq.getQuestionList();
        if(checkList.size() != tqList.size()) {
            throw new IllegalArgumentException("체크 리스트와 오늘의 문제 리스트의 개수가 일치하지 않습니다.");
        }

        for (int i = 0; i < checkList.size(); i++) {
            TodayQuestion getTq = tqList.get(i);
            if (checkList.get(i) != getTq.isFlag()) {
                getTq.changeFlag();
            }
        }

        todayQuestionRepository.saveAll(tqList);
        todayQuestionListRepository.save(tq);
        return tq;
    }
}
