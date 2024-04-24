package com.project.Retil.User.db.dto;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowProfileDTO {

	private String User_Id;
	@Column
	private String Nickname;
	@Column
	private Blob Profile_Image; //SQL 연동해야 됨.G
}
