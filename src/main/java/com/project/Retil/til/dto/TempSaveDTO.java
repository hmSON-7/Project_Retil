package com.project.Retil.til.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자가 에디터 임시 저장을 위해 입력한 정보를 받아주는 DTO
 * 순서대로 과목 이름, 공부한 시간
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TempSaveDTO {

    @NotEmpty
    private String subjectName;

    @NotEmpty
    private Long time;
}
