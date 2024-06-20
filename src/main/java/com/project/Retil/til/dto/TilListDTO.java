package com.project.Retil.til.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * TIL 리스트 출력에 필요한 값을 반환하는 DTO
 * 순서대로 북마크 여부, 과목 이름, 과목 컬러, TIL 제목, TIL 별 오늘의 문제 진행도, 작성 시각
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TilListDTO {

    @NotEmpty
    private Long til_id;

    @NotEmpty
    private Boolean bookmark;

    @NotEmpty
    private String subjectName;

    @NotEmpty
    private String title;

    @NotEmpty
    private String color;

    @NotEmpty
    private LocalDateTime saveTime;

    @NotEmpty
    private boolean aDay;

    @NotEmpty
    private boolean threeDays;

    @NotEmpty
    private boolean aWeek;

    @NotEmpty
    private boolean fifteenDays;

    @NotEmpty
    private boolean aMonth;

    @NotEmpty
    private boolean twoMonths;

    @NotEmpty
    private boolean sixMonths;
}
