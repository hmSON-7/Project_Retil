package com.project.Retil.review.service;

import com.project.Retil.review.dto.ReviewListDTO;
import com.project.Retil.review.entity.Review;
import com.project.Retil.review.entity.ReviewContent;
import com.project.Retil.review.repository.ReviewContentRepository;
import com.project.Retil.review.repository.ReviewRepository;
import com.project.Retil.til.entity.Til;
import com.project.Retil.til.entity.TilProgress;
import com.project.Retil.til.repository.TilProgressRepository;
import com.project.Retil.todayQuestion.entity.TodayQuestion;
import com.project.Retil.todayQuestion.repository.TodayQuestionRepository;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository; // 사용자 정보를 조회하는 리포지토리
    private final ReviewContentRepository reviewContentRepository; // 복습 콘텐츠를 저장하는 리포지토리
    private final TodayQuestionRepository todayQuestionRepository; // 오늘의 문제를 조회하는 리포지토리
    private final ReviewRepository reviewRepository; // 복습 정보를 저장하는 리포지토리
    private final TilProgressRepository tilProgressRepository; // TIL 진행도 정보를 저장하는 리포지토리

    // 복습 주기 사이클
    private final List<Integer> cycles = Arrays.asList(1, 3, 7, 15, 30, 60, 180);
    // 복습 주기별 문제 출제 비율
    private final Map<Integer, Double> cycleRatios = Map.of(
              1, 0.4,   // 1일 전 문제는 40%
              3, 0.25,  // 3일 전 문제는 25%
              7, 0.15,  // 7일 전 문제는 15%
              15, 0.1,  // 15일 전 문제는 10%
              30, 0.05, // 30일 전 문제는 5%
              60, 0.03, // 60일 전 문제는 3%
              180, 0.02 // 180일 전 문제는 2%
    );
    private final int totalQuestions = 40; // 총 문제 수

    // 복습 주기 문제 모음집 제공
    public List<ReviewContent> showReview(LocalDate today, Long userId) {
        User_Information user = userRepository.findById(userId)
                  .orElseThrow(() -> new IllegalArgumentException("Invalid user ID")); // 사용자 정보 조회

        Review review = reviewRepository.findByDateAndUser(today, user);

        if(review != null) {
            if(review.isFlag()) {
                throw new IllegalArgumentException("오늘자 복습 주기 문제는 이미 풀었습니다. 리스트를 통해 확인하세요!");
            }
            return review.getQuestionList();
        } else {
            return getReviewQuestions(today, user);
        }
    }

    // 복습 주기 문제 모음집 생성
    public List<ReviewContent> getReviewQuestions(LocalDate today, User_Information user) {
        List<TodayQuestion> reviewQuestions = new ArrayList<>(); // 복습 문제 리스트

        Map<Integer, List<TodayQuestion>> cycleQuestionsMap = new HashMap<>(); // 주기별 문제 리스트 맵
        for (int cycle : cycles) {
            LocalDate reviewDate = today.minusDays(cycle); // 각 주기에 해당하는 날짜 계산
            List<TodayQuestion> questionsForCycle = todayQuestionRepository.findAllByUserAndDate(user, reviewDate); // 해당 날짜의 문제 조회
            cycleQuestionsMap.put(cycle, questionsForCycle); // 주기별로 문제 저장
        }

        int totalAvailableQuestions = cycleQuestionsMap.values().stream().mapToInt(List::size).sum(); // 사용 가능한 총 문제 수

        // 각 주기에서 최소한의 문제를 확보
        for (int cycle : cycles) {
            List<TodayQuestion> questionsForCycle = cycleQuestionsMap.get(cycle);
            int numberOfQuestions = (int) (totalQuestions * cycleRatios.get(cycle)); // 주기별 문제 수 계산
            Collections.shuffle(questionsForCycle); // 문제를 섞음
            reviewQuestions.addAll(questionsForCycle.subList(0, Math.min(numberOfQuestions, questionsForCycle.size()))); // 문제 추가
        }

        // 부족한 문제를 다른 주기에서 보충
        int remainingQuestions = totalQuestions - reviewQuestions.size();
        if (remainingQuestions > 0) {
            List<TodayQuestion> allRemainingQuestions = cycleQuestionsMap.values().stream()
                      .flatMap(List::stream)
                      .collect(Collectors.toList());
            Collections.shuffle(allRemainingQuestions);
            reviewQuestions.addAll(allRemainingQuestions.subList(0, Math.min(remainingQuestions, allRemainingQuestions.size())));
        }

        Collections.shuffle(reviewQuestions); // 문제를 다시 섞음
        Review review = new Review(user); // 새로운 복습 정보 생성

        List<ReviewContent> reviewContents = reviewQuestions.stream()
                  .map(q -> new ReviewContent(review, q))
                  .collect(Collectors.toList());

        for (ReviewContent rc : reviewContents) {
            review.addReview(rc); // 복습 정보에 문제 추가
        }

        reviewRepository.save(review); // 복습 정보 저장
        reviewContentRepository.saveAll(reviewContents); // 복습 콘텐츠 저장

        return review.getQuestionList(); // 생성된 문제 리스트 반환
    }

    // 복습 주기 문제 리스트 제공
    public List<ReviewListDTO> showReviewList(Long userId) {
        User_Information user = userRepository.findById(userId)
                  .orElseThrow(() -> new IllegalArgumentException("Invalid user ID")); // 사용자 정보 조회

        List<Review> reviewList = reviewRepository.findAllByUser(user); // 사용자에 해당하는 모든 복습 정보 조회
        List<ReviewListDTO> reviews = new ArrayList<>();

        for (Review r : reviewList) {
            List<ReviewContent> contents = r.getQuestionList(); // 복습 문제 리스트 조회
            List<String> tilList = contents.stream()
                      .map(c -> c.getTodayQuestion().getQuestion().getTil().getTitle()) // 문제의 제목을 리스트로 변환
                      .collect(Collectors.toList());

            reviews.add(new ReviewListDTO(
                      r.getId(), r.getDate(), tilList, r.isFlag() // 복습 정보를 DTO로 변환하여 리스트에 추가
            ));
        }

        return reviews; // 복습 리스트 반환
    }

    public Review applyCheck(Long userId, List<Boolean> checkList) {
        User_Information user = userRepository.findById(userId)
                  .orElseThrow(() -> new IllegalArgumentException("Invalid user ID")); // 사용자 정보 조회

        Review review = reviewRepository.findByDateAndUser(LocalDate.now(), user); // 오늘 날짜에 해당하는 복습 정보 조회
        review.changeFlag(); // 복습 완료 상태 변경

        List<ReviewContent> reviewList = reviewContentRepository.findAllByReview(review);
        if (checkList.size() != reviewList.size()) {
            throw new IllegalArgumentException("체크 리스트와 오늘의 문제 리스트의 개수가 일치하지 않습니다."); // 체크 리스트와 문제 리스트의 개수 불일치 시 예외 발생
        }

        List<Til> tilList = new ArrayList<>();
        for (int i = 0; i < checkList.size(); i++) {
            TodayQuestion getTq = reviewList.get(i).getTodayQuestion();
            if (checkList.get(i) != getTq.isFlag()) {
                getTq.changeFlag(); // 체크 리스트에 따라 문제의 상태 변경
            }

            // 해당 복습 문제 리스트에 관여한 Til에 대해 리스트 생성
            Til til = getTq.getQuestion().getTil();
            if(!tilList.contains(til)) tilList.add(til);
        }

        for(Til til : tilList) {
            TilProgress progress = tilProgressRepository.findByTil(til);
            if(progress.getTwoMonths()) progress.changeSixMonths();
            else if(progress.getAMonth()) progress.changeTwoMonths();
            else if(progress.getFifteenDays()) progress.changeAMonth();
            else if(progress.getAWeek()) progress.changeFifteenDays();
            else if(progress.getThreeDays()) progress.changeAWeek();
            else if(progress.getADay()) progress.changeThreeDays();
            else progress.changeADay();

            tilProgressRepository.save(progress);
        }

        reviewContentRepository.saveAll(reviewList); // 변경된 복습 콘텐츠 저장
        reviewRepository.save(review); // 변경된 복습 정보 저장
        return review; // 변경된 복습 정보 반환
    }
}
