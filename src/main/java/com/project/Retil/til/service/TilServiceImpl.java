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

    /**
     * 1. TIL 리스트
     * 요청한 유저 객체로 모든 TIL을 찾아 리스트로 반환
     * @param user_id 요청한 유저 번호
     * @return 작성 일자 기준 최신순으로 정렬된 TIL 리스트 반환
     */
    @Override
    public ArrayList<TilListDTO> showList(Long user_id) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        ArrayList<Til> tilList = tilRepository.findAllByUser(user);
        return makeList(tilList);
    }

    /**
     * 2. 과목별 TIL 리스트
     * 요청한 유저 객체와 과목명에 해당하는 모든 TIL을 찾아 리스트로 반환
     * @param user_id 요청한 유저 번호
     * @param subjectName 사용자가 선택한 과목명
     * @return 작성 일자 기준 최신순으로 정렬된 과목별 TIL 리스트 반환
     */
    @Override
    public ArrayList<TilListDTO> showListInSubject(Long user_id, String subjectName) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        ArrayList<TilSubject> subjectList = tilSubjectRepository.findAllByUser(user);
        if (subjectList == null) {
            throw new IllegalArgumentException("아직 과목이 없습니다.");
        }

        TilSubject subject = null;
        for (TilSubject s : subjectList) {
            if (s.getSubjectName().equals(subjectName)) {
                subject = s;
                break;
            }
        }

        if (subject == null) {
            throw new IllegalArgumentException("존재하지 않는 과목입니다.");
        }

        ArrayList<Til> tilList = tilRepository.findAllByTilSubject(subject);
        return makeList(tilList);
    }

    /**
     * 3. TIL 리스트 생성
     * 사용자의 요구에 따른 TIL 리스트 반환을 위한 리스트 생성 메서드
     * @param tilList 사용자의 요구에 맞는 TIL 객체로 구성된 리스트
     * @return 상기한 리스트에서 북마크 여부, 과목명, 컬러, 제목, 작성 시간 정보를 추출하여 반환
     */
    public ArrayList<TilListDTO> makeList(ArrayList<Til> tilList) {
        ArrayList<TilListDTO> requestedList = new ArrayList<>();
        for (Til til : tilList) {
            requestedList.add(new TilListDTO(
                    til.getId(),
                    til.getBookmark(),
                    til.getTilSubject().getSubjectName(),
                    til.getTitle(),
                    til.getTilSubject().getColor(),
                    til.getSaveTime()
            ));
        }
        requestedList.sort(Comparator.comparing(TilListDTO::getSaveTime).reversed());
        return requestedList;
    }

    /**
     * 4. TIL 에디터 보기
     * 사용자가 선택한 TIL 리스트의 내용 반환
     * @param user_id 요청한 사용자의 번호
     * @param til_id 열람 요청을 받은 TIL 에디터의 번호
     * @return 사용자 번호와 TIL 번호로 요청에 맞는 TIL을 찾아서 반환
     */
    @Override
    public Til show(Long user_id, Long til_id) {
        return tilRepository.findByIdAndUserId(til_id, user_id).orElse(null);
    }

    /**
     * 5. 공부 시간 저장 및 최신화
     * TIL 저장 및 임시 저장에 모두 사용하는 공용 메서드
     * 요청한 사용자가 공부한 시간을 웹에서 받아 사용자의 총 공부량, 금일 공부량, 과목별 공부량 최신화
     * @param user 요청한 사용자 객체
     * @param time 사용자가 공부한 시간
     * @param subject TIL 에디터 작성시
     * @return User_Rank의 금일 공부량과 총 공부량, TilSubject의 과목별 공부량 값을 변경하여 다시 DB에 저장
     */
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
        userRank.changeTotalStudyTime(totalTime);
        userRank.changeTodayStudyTime(todayTime);
        userRank.changeLatestAccessed(LocalDate.now());
        userRank.changeUserRank(switchRank(totalTime));

        subject.changeStudyTime(subjectTime);

        return userRankRepository.save(userRank);
    }

    /**
     * 6. TIL 세이브
     * 유저 객체와 과목 객체를 찾아 TIL 생성 후 DB에 저장 및 공부한 시간 추가
     * 저장된 내용을 바탕으로 GhatGPT에서 문제 생성
     * @param tilCreateDto 과목명, 에디터 제목, 내용, 공부한 시간 값
     * @param user_id TIL 저장 요청한 사용자 번호
     * @return DB에 저장된 TIL 객체를 반환
     */
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

        List<Question> questions = chatGPTService.generateQuestions(savedTil, user);
        questions.forEach(this::saveUniqueQuestion);

        return savedTil;
    }

    /**
     * 7. GPT 문제 리스트 생성 메서드
     * 상기한 TIL 세이브 메서드에서 사용하는 GPT API를 활용하여 문제를 생성하고 DB에 저장하는 메서드
     * @param question 생성된 문제를 순서대로 받아와서 DB에 저장
     */
    public void saveUniqueQuestion(Question question) {
        Optional<Question> existingQuestion = questionRepository.findByContentAndTil(
            question.getContent(), question.getTil());
        if (existingQuestion.isEmpty()) {
            questionRepository.save(question);
        }
    }

    /**
     * 8. TIL 삭제
     * 삭제를 요청한 사용자와 대상 TIL을 찾아 해당 TIL을 DB에서 삭제
     * @param user_id 요청한 사용자 번호
     * @param til_id 삭제 대상 TIL 번호
     * @return DB에서 대상 TIL을 삭제한 후 해당 TIL 객체를 반환
     */
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

    /**
     * 9. 과목 추가 메서드
     * 사용자가 설정한 과목 이름과 컬러를 받아 새 과목 생성
     * @param user_id 요청한 사용자 번호
     * @param subjectName 지정한 새 과목명
     * @param color 지정한 과목 표기 색상
     * @return 새 Subject 객체 생성. DB에 저장 후 반환
     */
    @Override
    public TilSubject addSubject(Long user_id, String subjectName, String color) {
        User_Information user = userRepository.findById(user_id).orElse(null);

        if (user == null) {
            return null;
        }

        Color selected = Color.decode(color);
        TilSubject subject = new TilSubject(
                subjectName, user, color, 0L
        );

        return tilSubjectRepository.save(subject);
    }

    /**
     * 10. 과목 찾기
     * 선택된 특정 과목을 찾기 위한 메서드
     * 요청한 사용자에 대한 과목 리스트에서 요청 받은 과목명에 해당하는 Subject를 반환
     * @param subjectName 요청 대상 과목명
     * @param user 요청한 사용자 객체
     * @return 요청한 사용자에 대한 과목 리스트에서 해당 과목이 존재할 경우 Subject 객체 반환, 없으면 null 반환
     */
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

    /**
     * 11. 과목 리스트 출력
     * 요청한 유저가 설정한 과목 리스트 전체 출력
     * @param user_id 요청한 사용자 번호
     * @return Subject 리스트를 DB에서 가져온 후 과목명 리스트 반환
     */
    @Override
    public ArrayList<String> showSubjectList(Long user_id) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        if (user == null) {
            throw new RuntimeException("존재하지 않는 유저입니다.");
        }

        List<TilSubject> subjectList = tilSubjectRepository.findAllByUser(user);

        ArrayList<String> list = new ArrayList<>();
        for (TilSubject sub : subjectList) {
            list.add(sub.getSubjectName());
        }

        return list;
    }

    /**
     * 12. 과목 삭제
     * 사용자가 삭제 요청한 과목 제거
     * @param user_id 요청한 사용자의 번호
     * @param subjectName 요청 대상 과목명
     * @return 대상 과목을 DB에서 삭제하고 대상을 반환
     */
    @Override
    public TilSubject deleteSubject(Long user_id, String subjectName) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        if(user == null) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        TilSubject target = tilSubjectRepository.findByUserAndSubjectName(user, subjectName).orElse(null);
        if(target == null) {
            throw new IllegalArgumentException("존재하지 않는 과목입니다.");
        }

        tilSubjectRepository.delete(target);
        return target;
    }

    /**
     * 13. 총 공부량 기준 유저 랭크 지정 알고리즘
     * @param time 유저의 총 공부량
     * @return 특정 시간을 초과한 경우 그에 맞는 랭크 지정
     */
    public String switchRank(Long time) {
        if (time < 3600) {
            return "unRanked";
        } else if (time < 3600 * 10) {
            return "Bronze";
        } else if (time < 3600 * 50) {
            return "Silver";
        } else if (time < 3600 * 100) {
            return "Gold";
        } else if (time < 3600 * 500) {
            return "Platinum";
        } else {
            return "Diamond";
        }
    }
}
