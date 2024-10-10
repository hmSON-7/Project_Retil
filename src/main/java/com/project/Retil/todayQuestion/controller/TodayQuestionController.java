package com.project.Retil.todayQuestion.controller;

import com.project.Retil.todayQuestion.dto.QuestionCheckDTO;
import com.project.Retil.todayQuestion.entity.TodayQuestionList;
import com.project.Retil.todayQuestion.service.TodayQuestionImpl;
import com.project.Retil.todayQuestion.entity.TodayQuestion;
import com.project.Retil.userAccount.Entity.User_Information;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{user_id}/today-questions")
@RequiredArgsConstructor
public class TodayQuestionController {

    private final TodayQuestionImpl todayQuestionImpl;

    // 오늘의 문제를 생성하는 엔드포인트
    @PostMapping("/generate")
    public ResponseEntity<TodayQuestionList> generateTodayQuestions(@PathVariable("user_id") Long userId) {
        TodayQuestionList qList = todayQuestionImpl.generateTodayQuestions(userId);

        return (qList != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 오늘의 문제를 조회하는 엔드포인트
    @GetMapping
    public List<TodayQuestion> getTodayQuestions(@PathVariable("user_id") Long userId) {
        return todayQuestionImpl.getTodayQuestions(userId).getQuestionList();
    }

    // 오늘의 문제 풀이 여부 체크
    @PostMapping("/clear")
    public ResponseEntity<QuestionCheckDTO> saveResult(@PathVariable("user_id") Long userId,
                                                       @RequestBody QuestionCheckDTO checkList) {
        // 0. 웹에서 받아야 하는 값 : 리스트< 문제별 다시 보기 체크 여부 > v
        // 1. 유저 객체 찾아서 v
        // 2. 유저와 오늘 날짜에 대한 TodayQuestionList 객체 찾고, v
        // 3. TodayQuestionList의 flag : true로 변경,
        // 4. 리스트별 각 문제 조회해서 다시 보기 체크된 문제의 flag : true로 변경
        TodayQuestionList tq = todayQuestionImpl.applyCheck(userId, checkList.getCheck());

        return (tq != null) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
