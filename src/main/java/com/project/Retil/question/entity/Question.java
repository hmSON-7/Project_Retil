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

    @ManyToOne
    @JoinColumn(nullable = false, name = "til_id")
    private Til til;

    public Question(String content, Til til) {
        this.content = content;
        this.til = til;
    }
}
