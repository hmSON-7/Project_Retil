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


public class Question {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)

	private Long Question_ISBN;

	@Column

	@JoinColumn(name = "Til_Num", referencedColumnName = "Til_Num")
	private String Til_Num;
	@Column
	private String Question_Comment;
	@Column
	private String Correct;
	@Column
	private int Correct_Count;
	@Column
	private int Incorrect_Count;
	@Column
	private DateTimeException Question_Date;




}
