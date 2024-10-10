
package com.project.Retil.userAccount.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자가 로그인을 위해 입력한 정보를 받아주는 DTO
 * 순서대로 이메일, 비밀번호
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO{

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	private String password;

	// 로그인시 필요한 DTO
}
