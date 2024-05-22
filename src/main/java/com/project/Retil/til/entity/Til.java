package com.project.Retil.til.entity;

import com.project.Retil.userAccount.Entity.User_Information;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Til {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tilNum; // 에디터 번호(자동 생성)

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User_Information user;

    @Column
    private LocalDateTime saveTime;

    public Til(String title, String content, User_Information user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.saveTime = LocalDateTime.now();
    }
}
