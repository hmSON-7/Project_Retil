package com.project.Retil.til.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자가 과목 목록을 추가하기 위해 입력한 정보를 받아주는 DTO
 * 순서대로 과목 이름, 컬러
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddSubjectDTO {

    @NotEmpty
    private String subjectName;

    @NotEmpty
    private String color;
}
