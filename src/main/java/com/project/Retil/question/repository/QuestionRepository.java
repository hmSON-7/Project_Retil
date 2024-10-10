package com.project.Retil.question.repository;

import com.project.Retil.question.entity.Question;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.til.entity.Til;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByContentAndTil(String content, Til til);

    List<Question> findAllByUser(User_Information user); // 유저별 모든 문제 조회를 위한 메서드 추가

    List<Question> findAllByUserAndDate(User_Information user, LocalDate date);
}
