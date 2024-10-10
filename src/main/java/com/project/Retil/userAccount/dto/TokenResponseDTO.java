package com.project.Retil.userAccount.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 로그인 후 보안 및 회원 인증을 위해 반환하는 DTO
 * 순서대로 JWT 토큰, 유저 번호
 * 해당 정보는 웹으로 반환한 후 해당 사용자의 로컬 스토리지에 저장됨
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDTO {

    @NotEmpty
    private String token;

    @NotEmpty
    private Long id;

}
