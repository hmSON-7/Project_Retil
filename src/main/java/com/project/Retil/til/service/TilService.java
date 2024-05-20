package com.project.Retil.til.service;

import java.util.ArrayList;

public interface TilService {
    // 1. 리스트 전체 조회
    ArrayList<Til> showList(Long user_id);

    // 2. 과목별 리스트 조회

    // 3. TIL 단일 조회
    Til show(Long til_num);

    // 4. TIL 작성(임시 저장)

    // 5. TIL 작성 완료 후 저장
    Til save(TilCreateDTO tilCreateDto, Long user_id);

    // 6. TIL 삭제
    Til delete(Long user_id, Long til_num);
}
