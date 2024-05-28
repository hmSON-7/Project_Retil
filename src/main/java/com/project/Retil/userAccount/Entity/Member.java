package com.project.Retil.userAccount.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long groupNum;

	@JoinColumn(name = groupCode , referencedColumnName = "groupCode")
	private String groupCode; // Group에서 외래키 가져오기

	@JoinColumn(name = "id", referencedColumnName = "groupMember") // User_Information 참조함
	private Long groupMember; // User_Profile에서 외래키
}

public Member( ) {
	this.groupNum = groupNum;
	this.groupCode = groupCode;
	this.groupMember = groupMember;

}

