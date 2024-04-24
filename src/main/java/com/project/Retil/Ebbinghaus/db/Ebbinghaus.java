package com.project.Retil.Ebbinghaus.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ebbinghaus {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long Til_Num; // TIL 에서 외래키 가져오기

	@Column
	private boolean Now;

	@Column
	private boolean Day_1;

	@Column
	private boolean Day_3;

	@Column
	private boolean Week;

	@Column
	private boolean Week_Double;

	@Column
	private boolean Month;

	@Column
	private boolean Month_Double;

}
