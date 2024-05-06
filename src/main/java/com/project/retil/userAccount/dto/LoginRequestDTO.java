
package com.project.Retil.userAccount.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
