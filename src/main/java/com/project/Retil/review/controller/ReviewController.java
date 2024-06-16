package com.project.Retil.review.controller;

import com.project.Retil.question.entity.Question;
import com.project.Retil.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/review-questions")
    public List<Question> getReviewQuestions() {
        LocalDate today = LocalDate.now();
        return reviewService.getReviewQuestions(today);
    }
}