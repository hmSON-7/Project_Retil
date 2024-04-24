package com.project.Retil.User.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User_Rank {
	//유저의 랭크이다.
	@Id
	@JoinColumn(name = "User_Id", referencedColumnName = "User_id")
	private String User_Id; // 유저 아이디, 기본키
	@Column
	private Time Accumulate_Time; //시간축적
	@Column
	private String Rank; //랭크

	//한명의 회원에는 한개의 랭크가 주어진다.
	//한명의 회원에는 한개의 회원 아이디가 주어진다. -> 1대1 관계이지만,
	//일대일 속성 타입은 String일 수가 없다고 뜸.
}
