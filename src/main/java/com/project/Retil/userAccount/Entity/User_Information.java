package com.project.Retil.userAccount.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User_Information {

    // 유저의 모든 정보 , (회원가입 할 때 쓰인다.)
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
    private int accountConnection; //계정 연결(현재 보류)

    @Column
    private String profileImage;

    @Column(nullable = true)
    private String resetToken;

    public User_Information(String nickname, String email, String password) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = "";
        this.accountConnection = 0;
    }
}

