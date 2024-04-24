package com.project.Retil.User.db.dto;

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
public class JoinRequestDTO {

	@NotEmpty
	private String ID;
	@NotEmpty
	private String password;
	@NotEmpty
	private String email;
	@NotEmpty
	private String name;
	@NotEmpty
	private String job;
	@NotEmpty
	@Email
	private boolean emailCertification;

	// 회원가입시 필요한 DTO
}
