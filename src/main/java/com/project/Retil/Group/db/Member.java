package com.project.Retil.Group.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	// 그룹을 생성했을때 그룹 안의 유저 부분이다. (방장도 포함될 것)
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)

	private Long Group_Num;

	private String Group_Code; // Group 에서 외래키 가져오기

	@JoinColumn(name = "UserId", referencedColumnName = "Group_Member") // User_Information 참조함
	private String Group_Member; // User_ Profile 에서 외래키
}
