package com.project.Retil.review.repository;

import com.project.Retil.review.entity.ReviewCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReviewCycleRepository extends JpaRepository<ReviewCycle, Long> {

    List<ReviewCycle> findByReviewDate(LocalDate reviewDate);
}