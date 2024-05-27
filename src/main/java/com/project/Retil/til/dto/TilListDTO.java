package com.project.Retil.til.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TilListDTO {
    @NotEmpty
    private Boolean bookmark;

    @NotEmpty
    private String subjectName;

    @NotEmpty
    private String title;

    // 내용 추가 필요 : 오늘의 문제 진행도

    @NotEmpty
    private LocalDateTime saveTime;
}
