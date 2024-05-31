package com.project.Retil.userAccount.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
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
	private Long todayStudyTime; // 당일 공부 시간

	@Column
	private LocalDate latestAccessed; // 최근 접속 시간

	@Column
	private String userRank; //랭크

	public User_Rank(User_Information user, Long totalTime, Long todayTime, LocalDate accessed, String userRank) {
		this.user = user;
		this.totalStudyTime = totalTime;
		this.todayStudyTime = todayTime;
		this.latestAccessed = accessed;
		this.userRank = userRank;
	}
}