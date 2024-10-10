package com.project.Retil.review.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.Retil.todayQuestion.entity.TodayQuestion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    @JsonBackReference
    private Review review;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private TodayQuestion todayQuestion;

    public ReviewContent(Review review, TodayQuestion todayQuestion) {
        this.review = review;
        this.todayQuestion = todayQuestion;
    }

    public void changeReview(Review review) {
        this.review = review;
    }

}
