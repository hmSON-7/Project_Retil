package com.project.Retil.til.entity;

import com.project.Retil.userAccount.Entity.User_Information;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DB TIL 과목 목록 테이블
 * id : 일련번호(기본값, 자동 증가)
 * subjectName : 과목 이름
 * user : 유저 정보 객체(N대1 관계, DB에서 user_id로 표기)
 * color : 과목에 지정한 색상(16진수 코드 -> #000000)
 * studyTime : 과목별 공부한 시간
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TilSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subjectName;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User_Information user;

    @Column
    private String color;

    @Column
    private Long studyTime;

    // TIL 과목 객체 생성자
    public TilSubject(String subjectName, User_Information user, String color, Long studyTime) {
        this.subjectName = subjectName;
        this.user = user;
        this.color = color;
        this.studyTime = studyTime;
    }

    public void changeStudyTime(Long studyTime) {
        this.studyTime = studyTime;
    }
}
