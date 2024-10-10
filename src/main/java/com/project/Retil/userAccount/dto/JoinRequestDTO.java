package com.project.Retil.userAccount.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자가 회원 가입을 위해 입력한 정보를 받아주는 DTO
 * 순서대로 유저 닉네임, 이메일, 비밀번호, 비밀번호 확인
 * 이메일은 반드시 이메일 형태로 입력해야 함
 * 비밀번호는 최소 8자, 최대 20자, (알파벳, 숫자, 특수문자) 전부 반드시 포함
 * 상기한 조건 미충족 시 메세지 로깅
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequestDTO {

	@NotEmpty
	private String nickname;

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	@Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 작성해주세요.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\W).*$", message = "비밀번호는 알파벳, 숫자, 특수문자를 포함해야 합니다. ")
	private String password;

	@NotEmpty
	private String confirmPassword;

}
