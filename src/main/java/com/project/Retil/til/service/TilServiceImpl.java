package com.project.Retil.til.service;

import com.project.Retil.til.dto.TilCreateDTO;
import com.project.Retil.til.entity.Til;
import com.project.Retil.til.entity.TilSubject;
import com.project.Retil.til.repository.TilRepository;
import com.project.Retil.til.repository.TilSubjectRepository;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class TilServiceImpl implements TilService {
    private final TilRepository tilRepository;
    private final TilSubjectRepository tilSubjectRepository;
    private final UserRepository userRepository;

    /**
     * 1. TIL 리스트 출력
     * 유저의 id 값을 받아 해당 유저에 대한 TIL 전체를 리스트로 반환
     * @param user_id 사용자의 id
     * @return 해당 유저에 대한 TIL 전체 리스트
     */
    @Override
    public ArrayList<Til> showList(Long user_id) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        return user != null ? tilRepository.findAllByUser(user) : null;
    }

    /**
     * 2. 선택된 subject에 대한 TIL 리스트만 출력
     * @param user_id 사용자 id
     * @param subjectName 사용자가 선택한 과목 이름
     * @return 선택된 과목에 대한 리스트만 출력
     */
    @Override
    public ArrayList<Til> showListInSubject(Long user_id, String subjectName) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        ArrayList<TilSubject> subjectList = tilSubjectRepository.findAllByUser(user);
        if(user != null && subjectList != null) {
            for(TilSubject s : subjectList) {
                if(s.getSubjectName().equals(subjectName)) {
                    return tilRepository.findAllByTilSubject(s);
                }
            }
        }

        return null;
    }

    /**
     * 3. TIL 내용 출력
     * @param id
     * @return
     */
    @Override
    public Til show(Long id) {
        return tilRepository.findById(id).orElse(null);
    }

    @Override
    public Til save(TilCreateDTO tilCreateDto, Long user_id) {
        TilSubject subject = null;
        User_Information user = userRepository.findById(user_id).orElse(null);
        ArrayList<TilSubject> subjectList = tilSubjectRepository.findAllByUser(user);
        for(TilSubject s : subjectList) {
            if(s.getSubjectName().equals(tilCreateDto.getSubjectName())) {
                subject = s;
                break;
            }
        }

        if(subject == null) {
            return null;
        }

        Til til = new Til(
                subject,
                tilCreateDto.getTitle(),
                tilCreateDto.getContent(),
                user
        );

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
    public TilSubject addSubject(Long user_id, String subjectName) {
        User_Information user = userRepository.findById(user_id).orElse(null);

        if(user == null) return null;

        TilSubject subject = new TilSubject(
                subjectName, user
        );

        return tilSubjectRepository.save(subject);
    }
}
