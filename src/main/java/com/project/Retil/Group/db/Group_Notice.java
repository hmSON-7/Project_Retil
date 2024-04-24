package com.project.Retil.Group.db;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Group_Notice {
 	// 그룹을 홍보 할 시에, 나타나는 홍보창
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private int Notice_Num;

	@Column
	@JoinColumn(name = "Group_Code", referencedColumnName = "Group_code") // User_Information 참조함
	private  String Group_Code; // Group 에서 외래키 지정

	@Column
	private String Chat_Comment;

	@Column
	private Long Group_Member; // Group 에서  외래키 지정

	@Column
	private DateTimeException Chat_Timestamp;


}


