package com.project.Retil.review.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.Retil.userAccount.Entity.User_Information;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User_Information user;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ReviewContent> questionList = new ArrayList<>();

    @Column
    private boolean flag;

    public Review(User_Information user) {
        this.date = LocalDate.now();
        this.user = user;
        this.questionList = new ArrayList<>();
        this.flag = false;
    }

    public void changeFlag() {
        this.flag = true;
    }

    public void addReview(ReviewContent reviewContent) {
        questionList.add(reviewContent);
        reviewContent.changeReview(this);
    }
}