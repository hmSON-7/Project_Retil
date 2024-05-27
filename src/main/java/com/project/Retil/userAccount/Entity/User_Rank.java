package com.project.Retil.userAccount.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User_Rank {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User_Information user;
	@Column
	private Long totalStudyTime; // 총 공부 시간
	@Column
	private String userRank; //랭크

	public User_Rank(User_Information user, Long time, String userRank) {
		this.user = user;
		this.totalStudyTime = time;
		this.userRank = userRank;
	}
}