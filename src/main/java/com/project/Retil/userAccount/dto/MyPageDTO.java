package com.project.Retil.userAccount.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 마이 페이지 접근에 필요한 값을 반환하는 DTO
 * 순서대로 유저의 닉네임, 이메일
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyPageDTO {

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String email;
}
