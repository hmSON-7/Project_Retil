package com.project.Retil.userAccount.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DB 유저 정보 테이블
 * id : 일련번호(기본값, 자동 증가)
 * email : 이메일(후보키)
 * password : 비밀번호(DB 저장시 암호화)
 * nickname : 유저 닉네임
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User_Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; //이메일

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String nickname;

    @Column
    private LocalDateTime latestPwChange;

    @Column
    private String setToken;

    public User_Information(String nickname, String email, String password) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.latestPwChange = LocalDateTime.now();
        this.setToken = "";
    }

    // 유저 객체 생성자(회원가입)
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }
}
