package com.project.Retil.til.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자가 에디터 작성 완료 및 저장시 입력한 정보를 받아주는 DTO
 * 순서대로 과목 이름, TIL 제목, 내용, 공부한 시간
 * 에디터의 내용은 반드시 5자 이상 적어야 함
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TilCreateDTO {

    private String subjectName;

    @NotEmpty
    private String title;

    @NotEmpty
    @Size(min = 5)
    private String content;

    @NotEmpty
    private Long time;

}
