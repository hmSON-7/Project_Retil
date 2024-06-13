package com.project.Retil.todayQuestion.service;

import com.project.Retil.question.entity.Question;
import com.project.Retil.question.repository.QuestionRepository;
import com.project.Retil.todayQuestion.entity.TodayQuestion;
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

    private final TodayQuestionRepository todayQuestionRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    // 오늘의 문제를 생성하는 메서드
    @Transactional
    public List<TodayQuestion> generateTodayQuestions(Long userId) {
        User_Information user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        LocalDate today = LocalDate.now();

        // 오늘의 문제 이미 존재하는지 확인하고 삭제
        List<TodayQuestion> existingTodayQuestions = todayQuestionRepository.findByUserAndDate(user,
            today);
        if (!existingTodayQuestions.isEmpty()) {
            todayQuestionRepository.deleteAll(existingTodayQuestions);
        }

        List<Question> allQuestions = questionRepository.findAllByUser(user); // 유저별 모든 문제를 조회
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

        List<TodayQuestion> todayQuestions = selectedQuestions.stream()
            .map(q -> new TodayQuestion(q, user, today))
            .collect(Collectors.toList());

        return todayQuestionRepository.saveAll(todayQuestions);
    }

    // 오늘의 문제를 조회하는 메서드
    public List<TodayQuestion> getTodayQuestions(Long userId) {
        User_Information user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return todayQuestionRepository.findByUserAndDate(user, LocalDate.now());
    }
}
