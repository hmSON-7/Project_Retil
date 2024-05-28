package com.project.Retil.userAccount.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class groupSpec {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@JoinColumn(name = "noticeNum", referencedColumnName = "noticeNum")
	private int noticeNum; //Group_Notice에서 외래키 가져오기

	@Column
	private Date updateDate;

	@Column
	private Date minAt;

	@Column
	private DateTimeException minDt;

}
public groupSpec( ) {
	this.noticeNum = noticeNum;
	this.updateDate = updateDate;
	this.minAt = minAt;
	this.minDt = minDt;

}




