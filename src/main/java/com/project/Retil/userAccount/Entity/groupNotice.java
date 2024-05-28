package com.project.Retil.userAccount.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;
import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class groupNotice {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private int noticeNum;


	@JoinColumn(name = "groupCode", referencedColumnName = "groupCode") // User_Information 참조함
	private String groupCode; // Group 에서 외래키 지정

	@Column(nullable = false)
	private String chatComment;  //채팅 내용

	@ManyToOne
	@JoinColumn(name = "groupMember", referencedColumnName = "id", nullable = false)
	private User_Information groupMember; // Group 에서  외래키 지정

	@Column(nullable = false)
	private LocalDate chatTimestamp; // 채팅 날짜

}

public groupNotice(String chatComment, DateTimeException chatTimestamp, long groupMember) {
	this.group = group;
	this.chatComment = chatComment;
	this.groupMember = groupMember;
	this.chatTimestamp = chatTimestamp;
}


