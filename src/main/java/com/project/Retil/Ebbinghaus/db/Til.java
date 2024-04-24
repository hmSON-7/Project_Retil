package com.project.Retil.Ebbinghaus.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Til {

	@JoinColumn(name = "User_Id", referencedColumnName = "userId")
	@Id
	private String User_Id; // 외래키인데 표시 안함 이슈

	private Long Til_Num;

	@Column
	private String Til_Title;

	@Column
	private String Til_Comment;

	@Column
	private DateTimeException Til_Write;


}
