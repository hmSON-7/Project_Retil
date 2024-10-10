package com.project.Retil.todayQuestion.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.Retil.question.entity.Question;
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
public class TodayQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 엔티티의 고유 ID

    @ManyToOne
    @JoinColumn(nullable = false, name = "today_question_list_id")
    @JsonBackReference
    private TodayQuestionList todayQuestionList;

    @OneToOne
    @JoinColumn(nullable = false, name = "question_id")
    private Question question; // 연결된 Question 엔티티

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User_Information user; // 연결된 User_Information 엔티티

    @Column(nullable = false)
    private LocalDate date; // 문제 생성 날짜

    @Column
    private boolean flag;

    public TodayQuestion(TodayQuestionList questionList, Question question, User_Information user, LocalDate date) {
        this.todayQuestionList = questionList;
        this.question = question;
        this.user = user;
        this.date = date;
        this.flag = false;
    }

    public void changeFlag() {
        this.flag = !this.flag;
    }

    public void changeTodayQuestionList(TodayQuestionList tqList) {
        this.todayQuestionList = tqList;
    }
}
