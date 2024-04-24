package com.project.Retil.Ebbinghaus.db;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Question_Today {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long Today_Question;

	private Long Question_ISBN; // Question에서 외래키

}
