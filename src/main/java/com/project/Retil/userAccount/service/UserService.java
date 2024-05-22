package com.project.Retil.userAccount.service;

import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.dto.JoinRequestDTO;
import com.project.Retil.userAccount.dto.LoginRequestDTO;

public interface UserService {
	// 1. 회원가입
	User_Information signUp(JoinRequestDTO joinRequestDto);

	// 2. 로그인
	User_Information login(LoginRequestDTO LoginRequestDto);

	// 3. 비밀번호 변경을 위한 이메일 인증
	String requestPwChange(String email);

	// 4. 비밀번호 변경
	User_Information pwChange(String password);

	// 5. 회원 삭제
	void deleteUser(String password);
}


