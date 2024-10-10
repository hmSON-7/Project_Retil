package com.project.Retil.todayQuestion.repository;

import com.project.Retil.todayQuestion.entity.TodayQuestionList;
import com.project.Retil.userAccount.Entity.User_Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TodayQuestionListRepository extends JpaRepository<TodayQuestionList, Long> {
    TodayQuestionList findByDateAndUser(LocalDate date, User_Information user);
}
