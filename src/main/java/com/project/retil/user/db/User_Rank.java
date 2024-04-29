package com.project.retil.user.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
class User_Rank {

	//유저의 랭크이다.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User_Information user;
	@Column
	private LocalDateTime accumulateTime;
	@Column
	private String userRank; //랭크

	//한명의 회원에는 한개의 랭크가 주어진다.
	//한명의 회원에는 한개의 회원 아이디가 주어진다.
	public User_Rank(User_Information user) {
		this.user = user;
		this.accumulateTime = null;
		this.userRank = "";
	}
}