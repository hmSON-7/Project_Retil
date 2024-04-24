package com.project.Retil.User.db.dto;

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
public class ShowRankDTO {
	@Id
	@JoinColumn(name = "User_Id", referencedColumnName = "User_id")
	private String User_Id;
	@Column
	private Time Accumulate_Time;
	@Column
	private String Rank;

}

