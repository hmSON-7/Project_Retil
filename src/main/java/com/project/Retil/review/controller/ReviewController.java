package com.project.Retil.review.controller;

import com.project.Retil.review.dto.ReviewListDTO;
import com.project.Retil.review.entity.Review;
import com.project.Retil.review.entity.ReviewContent;
import com.project.Retil.review.service.ReviewService;
import com.project.Retil.todayQuestion.dto.QuestionCheckDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 1. 복습 주기 문제 제공
    @GetMapping("/{user_id}/review-questions")
    public ResponseEntity<List<ReviewContent>> getReviewQuestions(@PathVariable("user_id") Long userId) {

        List<ReviewContent> list = reviewService.showReview(LocalDate.now(), userId);

        return (list != null) ?
                ResponseEntity.status(HttpStatus.OK).body(list) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 2. 복습 주기 문제 리스트 제공(미완)
    @GetMapping("/{user_id}/review-list")
    public List<ReviewListDTO> showReviewList(@PathVariable("user_id") Long userId) {
        // 1. 유저 아이디로 유저 객체 찾아서
        // 2. 유저의 모든 Review 객체를 리스트로 정리
        // 3. 리스트의 id, 생성 일자, 문제를 제공한 TIL 제목 리스트, 문제 풀이 여부를 DTO 리스트로 정리 후 반환
        return reviewService.showReviewList(userId);
    }

    // 3. 복습 주기 문제 풀이 후 정답 체크(미완)
    @PostMapping("/{user_id}/review-questions")
    public ResponseEntity<QuestionCheckDTO> checkAnswer(@PathVariable("user_id") Long userId,
                                                        @RequestBody QuestionCheckDTO checkList) {
        // 0. 웹에서 받아야 하는 값 : 리스트< 문제별 다시 보기 체크 여부 > v
        // 1. 유저 객체 찾아서 v
        // 2. 유저와 오늘 날짜에 대한 Review 객체 찾고, v
        // 3. Review의 flag : true로 변경,
        // 4. 리스트별 각 문제 조회해서 다시 보기 체크된 문제의 flag : true로 변경
        Review review = reviewService.applyCheck(userId, checkList.getCheck());
        return (review != null) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}