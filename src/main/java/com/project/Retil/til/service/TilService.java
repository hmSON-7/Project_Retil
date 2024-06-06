package com.project.Retil.til.service;

import com.project.Retil.til.dto.TilCreateDTO;
import com.project.Retil.til.dto.TilListDTO;
import com.project.Retil.til.entity.Til;
import com.project.Retil.til.entity.TilSubject;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Entity.User_Rank;

import java.util.ArrayList;

public interface TilService {
    // 1. 리스트 전체 조회
    ArrayList<TilListDTO> showList(Long user_id);

    // 2. 과목별 리스트 조회
    ArrayList<TilListDTO> showListInSubject(Long user_id, String subjectName);

    // 3. TIL 단일 조회
    Til show(Long user_id, Long til_id);

    // 4. TIL 작성(임시 저장)
    User_Rank timeSave(User_Information user, Long time, TilSubject subject);

    // 5. TIL 작성 완료 후 저장
    Til save(TilCreateDTO tilCreateDto, Long user_id);

    // 6. TIL 삭제
    Til delete(Long user_id, Long til_id);

    // 7. 과목 저장
    TilSubject addSubject(Long user_id, String subjectName, String color);

    TilSubject searchSubject(String subjectName, User_Information user);

    // 8. 과목 리스트 제공
    ArrayList<String> showSubjectList(Long user_id);
}
