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
public class Chat {

	// 그룹 내에서, 채팅을 할 시에 보여주는 정보
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long Chat_ISBN;

	@Column
	private String Group_Code; // Group 클래스에서 외래키 가져옴

	@Column
	private String Chat_Comment;
	@Column
	private Long Group_Member;
	@Column
	private DateTimeException Chat_Timestamp;

}
