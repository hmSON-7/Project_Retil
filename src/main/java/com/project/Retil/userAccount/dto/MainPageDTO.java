package com.project.Retil.userAccount.dto;

import com.project.Retil.til.dto.TilListDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 메인 페이지 접근에 필요한 값을 반환하는 DTO
 * 순서대로 금일 공부량, 총 공부량, 유저 랭크, TIL 리스트
 * 금일 공부량의 최대값은 86400000 밀리초(1일)
 * TIL 리스트는 작성되지 않은 시기를 고려하여 NULL 값을 허용
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MainPageDTO {

    @NotEmpty
    @Size(max = 86400000)
    private Long todayStudied;

    @NotEmpty
    private Long totalStudied;

    @NotEmpty
    private String userRank;

    @NotEmpty
    private ArrayList<TilListDTO> tilList;

}
