package com.project.Retil.til.entity;

import com.project.Retil.userAccount.Entity.User_Information;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DB TIL 에디터 테이블
 * id : 일련번호(기본값, 자동 증가)
 * tilSubject : TIL 과목 객체(N대1 관계, DB에서는 subject_id로 표기)
 * title : TIL 제목
 * content : TIL 내용. 최대 10000자 허용
 * user : 유저(작성자) 정보 객체(N대1 관계, DB에서 user_id로 표기)
 * saveTime : TIL 저장 시각
 * bookMark : TIL 북마크 여부
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Til {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 에디터 번호(자동 생성)


    @ManyToOne
    @JoinColumn(nullable = false, name = "subject_id")
    private TilSubject tilSubject;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 50000)
    private String content;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User_Information user;

    @Column
    private LocalDateTime saveTime;

    @Column
    private Boolean bookmark;

    // TIL 에디터 객체 생성자(에디터 작성 후 저장)
    public Til(TilSubject tilSubject, String title, String content, User_Information user, Boolean check) {
        this.tilSubject = tilSubject;
        this.title = title;
        this.content = content;
        this.user = user;
        this.saveTime = LocalDateTime.now();
        this.bookmark = check;
    }
}
