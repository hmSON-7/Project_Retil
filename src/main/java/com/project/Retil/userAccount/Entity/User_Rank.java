package com.project.Retil.userAccount.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DB 유저 랭크 정보 테이블
 * id : 일련번호(기본값, 자동 증가)
 * user : 유저 정보 객체(1대1 관계). DB에서는 user_id로 저장
 * totalStudyTime : 총 공부량
 * todayStudyTime : 금일 공부량
 * latestAccessed : 최근 접속한 날짜
 * userRank : 유저 랭크
 */
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
	private Long todayStudyTime; // 당일 공부 시간

	@Column
	private LocalDate latestAccessed; // 최근 접속 날짜

	@Column
	private String userRank; //랭크

	// 유저 랭크 객체 생성자(회원가입)
	public User_Rank(User_Information user, Long totalTime, Long todayTime, LocalDate accessed, String userRank) {
		this.user = user;
		this.totalStudyTime = totalTime;
		this.todayStudyTime = todayTime;
		this.latestAccessed = accessed;
		this.userRank = userRank;
	}

	public void changeTotalStudyTime(Long totalStudyTime) {
		this.totalStudyTime = totalStudyTime;
	}

	public void changeTodayStudyTime(Long todayStudyTime) {
		this.todayStudyTime = todayStudyTime;
	}

	public void changeLatestAccessed(LocalDate latestAccessed) {
		this.latestAccessed = latestAccessed;
	}

	public void changeUserRank(String userRank) {
		this.userRank = userRank;
	}
}