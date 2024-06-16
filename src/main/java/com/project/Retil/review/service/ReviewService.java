package com.project.Retil.review.service;

import com.project.Retil.question.entity.Question;
import com.project.Retil.review.entity.ReviewCycle;
import com.project.Retil.review.repository.ReviewCycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewCycleRepository reviewCycleRepository;

    private final List<Integer> cycles = Arrays.asList(1, 3, 7, 15, 30, 180);
    private final Map<Integer, Double> cycleRatios = Map.of(
            1, 0.4,
            3, 0.3,
            7, 0.2,
            15, 0.05,
            30, 0.03,
            180, 0.02
    );

    public List<Question> getReviewQuestions(LocalDate today) {
        List<Question> reviewQuestions = new ArrayList<>();

        for (int cycle : cycles) {
            LocalDate reviewDate = today.minusDays(cycle);
            List<ReviewCycle> reviewCycles = reviewCycleRepository.findByReviewDate(reviewDate);

            List<Question> questionsForCycle = reviewCycles.stream()
                    .map(ReviewCycle::getQuestion)
                    .collect(Collectors.toList());

            int numberOfQuestions = (int) (questionsForCycle.size() * cycleRatios.get(cycle));
            Collections.shuffle(questionsForCycle);
            reviewQuestions.addAll(questionsForCycle.subList(0,
                    Math.min(numberOfQuestions, questionsForCycle.size())));
        }

        Collections.shuffle(reviewQuestions);
        return reviewQuestions.size() > 20 ? reviewQuestions.subList(0, 20) : reviewQuestions;
    }
}