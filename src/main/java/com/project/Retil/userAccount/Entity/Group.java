package com.project.Retil.userAccount.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public static class Group {
	@Id
	private String groupCode;

	@Column
	@OneToOne
	@JoinColumn(name = "groupOwner", referencedColumnName = "id", nullable = false) // User_Information 참조함
	private Long groupOwner;

	@Column(length = 30) //그룹 소개란 최대 30자 제한
	private  String groupIntroduce; // 그룹 소개란

	@Column
	private int peopleCut;  //그룹에 들어갈 수 있는 수?

	@Column
	private int currentPeople; // 현재 인원수

}

public Group(Long groupOwner, String groupCode, String groupIntroduce) {
	this.groupCode = groupCode;
	this.groupOwner = groupOwner;
	this.groupIntroduce = groupIntroduce;
	this.peopleCut = 20; // 최대 인원수 20명으로 제한
	this.currentPeople = ""; // 초기 인원수 0명 그런데 방장이 있으니까 1명인가?
}

// 최대 인원수 초과 여부를 확인하는 메서드
public boolean canAddMember() {
	return this.currentPeople < this.peopleCut;
	}
}
