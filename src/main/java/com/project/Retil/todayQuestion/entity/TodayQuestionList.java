package com.project.Retil.todayQuestion.entity;

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
public class TodayQuestionList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;

    @Column
    private boolean flag;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User_Information user;

    @OneToMany(mappedBy = "todayQuestionList", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TodayQuestion> questionList = new ArrayList<>();

    public TodayQuestionList(User_Information user) {
        this.date = LocalDate.now();
        this.user = user;
        this.flag = false;
        this.questionList = new ArrayList<>();
    }

    public void addQuestion(TodayQuestion tq) {
        questionList.add(tq);
        tq.changeTodayQuestionList(this);
    }

    public void changeFlag() {
        this.flag = true;
    }

}
