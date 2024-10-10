package com.project.Retil.question.entity;

import com.project.Retil.til.entity.Til;
import com.project.Retil.userAccount.Entity.User_Information;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(nullable = false, name = "til_id")
    private Til til;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User_Information user;

    public Question(String content, String answer, Til til, User_Information user) {
        this.content = content;
        this.answer = answer;
        this.date = LocalDate.now();
        this.til = til;
        this.user = user;
    }
}
