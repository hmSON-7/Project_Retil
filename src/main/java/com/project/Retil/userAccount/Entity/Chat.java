package com.project.Retil.userAccount.Entity;


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

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long ChatISBN;

	@Column
	@JoinColumn(name = "groupCode", referencedColumnName = "groupCode", nullable = false)
	private Long groupCode; // Group 클래스에서 외래키 가져옴

	@Column
	private String chatComment;

	@Column
	@JoinColumn(name = "groupMember", referencedColumnName = "id" , nullable = false)
	private Long groupMember;

	@Column
	private DateTimeException chatTimestamp;

}

public Chat(String chatComment , Long groupMember , DateTimeException chatTimestamp ) {
	this.ChatISBN = ChatISBN;
	this.groupCode = groupCode;
	this.chatComment = chatComment;
	this.groupMember = groupMember;
	this.chatTimestamp = chatTimestamp;
}



