
package com.project.Retil.User.db.dto;

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
	private String userId;
	@NotEmpty
	private String password;

	// 로그인시 필요한 DTO
}
