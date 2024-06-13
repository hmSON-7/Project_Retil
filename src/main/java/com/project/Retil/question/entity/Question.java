package com.project.Retil.question.entity;

import com.project.Retil.til.entity.Til;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(nullable = false, name = "til_id")
    private Til til;

    public Question(String content, String answer, Til til) {
        this.content = content;
        this.answer = answer;
        this.til = til;
    }
}
