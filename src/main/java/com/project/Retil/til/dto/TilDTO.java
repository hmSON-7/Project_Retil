package com.project.Retil.til.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 사용자가 요청한 TIL 에디터의 내용을 반환하는 DTO
 * 순서대로 TIL 제목, 과목명, 내용, 작성 시간
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TilDTO {

    @NotEmpty
    private String title;

    @NotEmpty
    private String subjectName;

    @NotEmpty
    private String content;

    @NotEmpty
    private LocalDateTime saveTime;

}