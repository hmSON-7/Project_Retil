package com.project.Retil.review.entity;

import com.project.Retil.question.entity.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "question_id")
    private Question question;

    @Column(nullable = false)
    private LocalDate reviewDate;

    @Column(nullable = false)
    private int cycle;  // 주기 (1, 3, 7, 15, 30, 180)
}