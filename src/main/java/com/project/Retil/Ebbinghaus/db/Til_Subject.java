package com.project.Retil.Ebbinghaus.db;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Til_Subject {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long Subject_Num;
	@Column
	@JoinColumn(name = "Til_Num", referencedColumnName = "Til_Num")
	private Long Til_Num;
	@Column
	private Long subject;
	@Column
	private String Color;


}
