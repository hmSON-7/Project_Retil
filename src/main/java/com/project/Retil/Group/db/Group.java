package com.project.Retil.Group.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Group {
	// 그룹의 전체 정보가 나타나고 있습니다.
	@Id
	private String Group_Code;

	@Column
	@JoinColumn(name = "UserId", referencedColumnName = "Group_Owner") // User_Information 참조함
	private String Group_Owner; //User_Profile에서 User_ID 외래키 참조하는데 도메인 다름 이슈

	@Column
	private  String Group_Introduce;

	@Column
	private int People_Cut;

	@Column
	private int Current_People;
}
