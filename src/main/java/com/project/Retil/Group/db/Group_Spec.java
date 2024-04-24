package com.project.Retil.Group.db;

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
public class Group_Spec {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private int Notice_Num; //Group_Notice에서 외래키 가져오기

	@Column
	private Date Update_Date;

	@Column
	private Date Min_At; //sql이 아닌 java

	@Column
	private DateTimeException Min_Dt; //sql이 아닌 java

}
