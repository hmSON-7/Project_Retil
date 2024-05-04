package com.project.retil.user.db.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
