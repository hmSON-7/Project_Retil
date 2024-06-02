package com.project.Retil.til.service;

import com.project.Retil.til.dto.TilCreateDTO;
import com.project.Retil.til.dto.TilListDTO;
import com.project.Retil.til.entity.Til;
import com.project.Retil.til.entity.TilSubject;
import com.project.Retil.til.repository.TilRepository;
import com.project.Retil.til.repository.TilSubjectRepository;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Entity.User_Rank;
import com.project.Retil.userAccount.Repository.UserRankRepository;
import com.project.Retil.userAccount.Repository.UserRepository;
import com.project.Retil.question.entity.Question;
import com.project.Retil.question.repository.QuestionRepository;
import com.project.Retil.chatgpt.ChatGPTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TilServiceImpl implements TilService {

    private final TilRepository tilRepository;
    private final TilSubjectRepository tilSubjectRepository;
    private final UserRepository userRepository;
    private final UserRankRepository userRankRepository;
    private final QuestionRepository questionRepository;
    private final ChatGPTService chatGPTService;

    @Override
    public ArrayList<TilListDTO> showList(Long user_id) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        if (user == null) {
            return null;
        }

        ArrayList<Til> tilList = tilRepository.findAllByUser(user);
        return makeList(tilList);
    }

    @Override
    public ArrayList<TilListDTO> showListInSubject(Long user_id, String subjectName) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        if (user == null) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }

        ArrayList<TilSubject> subjectList = tilSubjectRepository.findAllByUser(user);
        if (subjectList == null) {
            throw new RuntimeException("아직 과목이 없습니다.");
        }

        TilSubject subject = null;
        for (TilSubject s : subjectList) {
            if (s.getSubjectName().equals(subjectName)) {
                subject = s;
                break;
            }
        }

        if (subject == null) {
            throw new RuntimeException("존재하지 않는 과목입니다.");
        }

        ArrayList<Til> tilList = tilRepository.findAllByTilSubject(subject);
        return makeList(tilList);
    }

    public ArrayList<TilListDTO> makeList(ArrayList<Til> tilList) {
        ArrayList<TilListDTO> requestedList = new ArrayList<>();
        for (Til til : tilList) {
            requestedList.add(new TilListDTO(
                    til.getBookmark(),
                    til.getTilSubject().getSubjectName(),
                    til.getTitle(),
                    til.getSaveTime()
            ));
        }
        requestedList.sort(Comparator.comparing(TilListDTO::getSaveTime).reversed());
        return requestedList;
    }

    @Override
    public Til show(Long user_id, Long til_id) {
        return tilRepository.findByIdAndUserId(til_id, user_id).orElse(null);
    }

    @Override
    public User_Rank timeSave(User_Information user, Long time, TilSubject subject) {
        if (user == null) {
            return null;
        }

        User_Rank userRank = userRankRepository.findByUser(user);
        if (userRank == null) {
            throw new RuntimeException("존재하지 않는 사용자 등급입니다.");
        }

        Long totalTime = time + userRank.getTotalStudyTime();
        Long subjectTime = time + subject.getStudyTime();
        Long todayTime = Objects.equals(userRank.getLatestAccessed(), LocalDate.now()) ?
                time + userRank.getTodayStudyTime() : time;
        userRank.setTodayStudyTime(todayTime);
        userRank.setTotalStudyTime(totalTime);
        userRank.setLatestAccessed(LocalDate.now());
        userRank.setUserRank(switchRank(totalTime));

        subject.setStudyTime(subjectTime);

        return userRankRepository.save(userRank);
    }

    @Override
    public Til save(TilCreateDTO tilCreateDto, Long user_id) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        if (user == null) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }

        TilSubject subject = searchSubject(tilCreateDto.getSubjectName(), user);
        if (subject == null) {
            throw new RuntimeException("존재하지 않는 과목입니다.");
        }

        Til til = new Til(
                subject,
                tilCreateDto.getTitle(),
                tilCreateDto.getContent(),
                user,
                false
        );

        timeSave(user, tilCreateDto.getTime(), subject);

        Til savedTil = tilRepository.save(til);

        List<Question> questions = chatGPTService.generateQuestions(savedTil);
        questions.forEach(this::saveUniqueQuestion);

        return savedTil;
    }


    public void saveUniqueQuestion(Question question) {
        Optional<Question> existingQuestion = questionRepository.findByContentAndTil(
                question.getContent(), question.getTil());
        if (existingQuestion.isEmpty()) {
            questionRepository.save(question);
        }
    }

    @Override
    public Til delete(Long user_id, Long til_id) {
        Til target = tilRepository.findById(til_id).orElse(null);

        if (target == null) {
            return null;
        }
        if (!target.getUser().getId().equals(user_id)) {
            return null;
        }

        tilRepository.delete(target);
        return target;
    }

    @Override
    public TilSubject addSubject(Long user_id, String subjectName, String color) {
        User_Information user = userRepository.findById(user_id).orElse(null);

        if (user == null) {
            return null;
        }

        Color selected = Color.decode(color);
        TilSubject subject = new TilSubject(
                subjectName, user, selected, 0L
        );

        return tilSubjectRepository.save(subject);
    }

    @Override
    public TilSubject searchSubject(String subjectName, User_Information user) {
        ArrayList<TilSubject> subjectList = tilSubjectRepository.findAllByUser(user);
        for (TilSubject s : subjectList) {
            if (s.getSubjectName().equals(subjectName)) {
                return s;
            }
        }

        return null;
    }

    public String switchRank(Long time) {
        if (time < 3600000) {
            return "unRanked";
        } else if (time < 3600000 * 10) {
            return "Bronze";
        } else if (time < 3600000 * 50) {
            return "Silver";
        } else if (time < 3600000 * 100) {
            return "Gold";
        } else if (time < 3600000 * 500) {
            return "Platinum";
        } else {
            return "Diamond";
        }
    }
}
