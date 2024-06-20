package com.project.Retil.review.repository;

import com.project.Retil.review.entity.Review;
import com.project.Retil.review.entity.ReviewContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewContentRepository extends JpaRepository<ReviewContent, Long> {
    List<ReviewContent> findAllByReview(Review review);
}
