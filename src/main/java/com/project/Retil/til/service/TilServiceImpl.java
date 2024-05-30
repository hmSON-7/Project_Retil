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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TilServiceImpl implements TilService {
    private final TilRepository tilRepository;
    private final TilSubjectRepository tilSubjectRepository;
    private final UserRepository userRepository;
    private final UserRankRepository userRankRepository;

    /**
     * 1. TIL 리스트 작성 일자 순으로 출력
     * 유저의 id 값을 받아 해당 유저에 대한 TIL 전체를 리스트로 반환
     * @param user_id 사용자 id
     * @return 사용자 id로 찾은 TIL 리스트를 작성 일자 순으로 정렬해서 반환
     */
    @Override
    public ArrayList<TilListDTO> showList(Long user_id) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        if(user == null) return null;

        ArrayList<Til> tilList = tilRepository.findAllByUser(user);
        return makeList(tilList);
    }

    /**
     * 2. 선택된 subject에 대한 TIL 리스트만 작성 일자 순으로 출력
     * @param user_id 사용자 id
     * @param subjectName 사용자가 선택한 과목 이름
     * @return 선택된 과목에 대한 리스트만 작성 일자 순으로 정렬해서 출력
     */
    @Override
    public ArrayList<TilListDTO> showListInSubject(Long user_id, String subjectName) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        if(user == null) throw new RuntimeException("존재하지 않는 사용자입니다.");

        ArrayList<TilSubject> subjectList = tilSubjectRepository.findAllByUser(user);
        if(subjectList == null) throw new RuntimeException("아직 과목이 없습니다.");

        TilSubject subject = null;
        for(TilSubject s : subjectList) {
            if(s.getSubjectName().equals(subjectName)) {
                subject = s; break;
            }
        }

        ArrayList<Til> tilList = tilRepository.findAllByTilSubject(subject);
        return makeList(tilList);
    }

    public ArrayList<TilListDTO> makeList(ArrayList<Til> tilList) {
        ArrayList<TilListDTO> requestedList = new ArrayList<>();
        for(Til til : tilList) {
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

    /**
     * 3. TIL 내용 출력
     * @param id TIL id
     * @return 해당 id를 가진 TIL을 찾아서 반환
     */
    @Override
    public Til show(Long user_id, Long til_id) {
        return tilRepository.findByIdAndUserId(til_id, user_id).orElse(null);
    }
    @Override
    public User_Rank timeSave(User_Information user, Long time, TilSubject subject) {
        if(user == null) return null;

        User_Rank userRank = userRankRepository.findByUser(user);
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
        TilSubject subject = searchSubject(tilCreateDto.getSubjectName(), user);

        if(subject == null) {
            return null;
        }

        Til til = new Til(
                subject,
                tilCreateDto.getTitle(),
                tilCreateDto.getContent(),
                user,
                false
        );

        timeSave(user,tilCreateDto.getTime(), subject);

        return tilRepository.save(til);
    }

    @Override
    public Til delete(Long user_id, Long til_id) {
        Til target = tilRepository.findById(til_id).orElse(null);

        if(target == null) {
            return null;
        }
        if(!target.getUser().getId().equals(user_id)) {
            return null;
        }

        tilRepository.delete(target);
        return target;
    }

    @Override
    public TilSubject addSubject(Long user_id, String subjectName, String color) {
        User_Information user = userRepository.findById(user_id).orElse(null);

        if(user == null) return null;

        Color selected = Color.decode(color);
        TilSubject subject = new TilSubject(
                subjectName, user, selected, 0L
        );

        return tilSubjectRepository.save(subject);
    }

    @Override
    public TilSubject searchSubject(String subjectName, User_Information user) {
        ArrayList<TilSubject> subjectList = tilSubjectRepository.findAllByUser(user);
        for(TilSubject s : subjectList) {
            if(s.getSubjectName().equals(subjectName)) {
                return s;
            }
        }

        return null;
    }

    public String switchRank(Long time) {
        if(time < 3600000) {
            return "unRanked";
        } else if(time < 3600000 * 10) {
            return "Bronze";
        } else if(time < 3600000 * 50) {
            return "Silver";
        } else if(time < 3600000 * 100) {
            return "Gold";
        } else if(time < 3600000 * 500) {
            return "Platinum";
        } else {
            return "Diamond";
        }
    }
}
