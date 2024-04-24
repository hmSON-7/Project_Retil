package com.project.Retil.User.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
class User_Profile {
	// 유저의 프로필이다.

	@JoinColumn(name = "UserId", referencedColumnName = "userId") // User_Information 참조함
	@Id
	private String User_Id; //유저아이디, 기본키
	@Column
	private String Nickname; // 닉네임
	@Column
	private String Profile_Image; //프로필 이미지, sql 연동할 것
}