package com.project.Retil.userAccount.service;

import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.dto.JoinRequestDTO;
import com.project.Retil.userAccount.dto.LoginRequestDTO;

public interface UserService {
	User_Information signUp(JoinRequestDTO joinRequestDto);
	User_Information login(LoginRequestDTO LoginRequestDto);
	User_Information pwchange(String email); // 보류

	boolean isEmailUnique(String email);

	//변수 객체는 유저인포메이션 매개변수는
	// DTO 를 넣어라 그런데 회원가입 기능이까
	//회원가입 관련된걸로 넣어야함
}


