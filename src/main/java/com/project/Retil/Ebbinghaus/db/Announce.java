package com.project.Retil.Ebbinghaus.db;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Announce {

	// 게시물에 관한 엔티티
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)

	private int Announce_Num;
	@Column
	private Date Announce_Date;
	@Column
	private String Announce_Title;
	@Column
	private String Admin;
	@Column
	private String Announce_Comment;




}
