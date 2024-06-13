package com.project.Retil.todayQuestion.repository;

import com.project.Retil.todayQuestion.entity.TodayQuestion;
import com.project.Retil.userAccount.Entity.User_Information;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodayQuestionRepository extends JpaRepository<TodayQuestion, Long> {

    List<TodayQuestion> findByUserAndDate(User_Information user, LocalDate date);
}
