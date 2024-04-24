package com.project.Retil.User.db;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
class User_Information {

	// 유저의 모든 정보 , (회원가입할때 쓰인다.)
	@Id
	private String UserId; //유저아이디, 기본키


	@Column
	private String Password; // 비밀번호
	@Column
	private String Email; //이메일
	@Column
	private String Job; // 대학생,중학생,회사원인지 분류 (랭크를 이용해서 카테고리화 할거임)
	@Column
	private int Account_Connection; //계정 연결(현재 보류)
	@Column
	private boolean Email_Certification; // 이메일 인증, 아이디 비밀번호 찾기시 사용

}
