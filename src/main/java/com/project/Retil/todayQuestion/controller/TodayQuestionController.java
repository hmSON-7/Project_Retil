package com.project.Retil.todayQuestion.controller;

import com.project.Retil.todayQuestion.service.TodayQuestionImpl;
import com.project.Retil.todayQuestion.entity.TodayQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{user_id}/today-questions")
@RequiredArgsConstructor
public class TodayQuestionController {

    private final TodayQuestionImpl todayQuestionImpl;

    // 오늘의 문제를 생성하는 엔드포인트
    @PostMapping("/generate")
    public List<TodayQuestion> generateTodayQuestions(@PathVariable("user_id") Long userId) {
        return todayQuestionImpl.generateTodayQuestions(userId);
    }

    // 오늘의 문제를 조회하는 엔드포인트
    @GetMapping
    public List<TodayQuestion> getTodayQuestions(@PathVariable("user_id") Long userId) {
        return todayQuestionImpl.getTodayQuestions(userId);
    }
}
