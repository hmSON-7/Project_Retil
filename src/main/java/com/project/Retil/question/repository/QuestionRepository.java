package com.project.Retil.question.repository;

import com.project.Retil.question.entity.Question;
import com.project.Retil.til.entity.Til;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByContentAndTil(String content, Til til);
}

