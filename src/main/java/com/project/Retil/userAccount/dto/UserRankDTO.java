package com.project.Retil.userAccount.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 유저 랭킹 페이지에서 요구하는 정보를 반환할 때 사용할 DTO
 * 순서대로 유저 닉네임, 유저 랭크(문자열), 총 공부량
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRankDTO {

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String userRank;

    @NotEmpty
    private Long totalTime;

}