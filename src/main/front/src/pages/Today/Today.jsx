import React, { useState } from "react";
import "./Today.css";
import axiosInstance from "../../api/axiosInstance.js";
import TodayQuestion from "../Today/TodayQuestion.jsx";
import ReviewQuestion from "./ReviewQuestion.jsx";

const Today = () => {
  const token = localStorage.getItem("token");
  const user_id = localStorage.getItem("user_id");
  const [todayQuestions, setTodayQuestions] = useState([]);
  const [reviewQuestions , setReviewQuestions] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isQuestion, setIsQuestion] = useState(false);
  const [isCreating, setIsCreating] = useState(false); // Add state for button disable

  const onClickCreate = async () => {
    try {
      setIsCreating(true); // Disable the button
      const response = await axiosInstance.post(
          `/api/${user_id}/today-questions/generate`,
          {},
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
      );
      console.log(response.data); // Handle the response data as needed
    } catch (error) {
      console.error(error); // Handle error appropriately
    } finally {
      setIsCreating(false); // Enable the button
    }
  };

  const onClickTodayQuestion = async () => {
    try {
      const response = await axiosInstance.get(
          `/api/${user_id}/today-questions`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
      );
      const extractedQuestions = response.data.map((question) => ({
        id: question.id,
        content: question.question.content,
        answer: question.question.answer,
        flag: question.flag,
      }));
      setTodayQuestions(extractedQuestions); // Set the questions in the state
      setIsModalOpen(true); // Open the modal
    } catch (error) {
      console.error(error); // Handle error appropriately
    }
  };

  const onClickReviewQuestion = async () => {
    try {
      const response = await axiosInstance.get(`/${user_id}/review-questions`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      const extractedQuestions = response.data.map((reviewContent) => ({
        id: reviewContent.id,
        content: reviewContent.todayQuestion.question.content,
        answer: reviewContent.todayQuestion.question.answer,
        flag: reviewContent.todayQuestion.flag,
      }));
      setReviewQuestions(extractedQuestions); // Set the questions in the state
      setIsQuestion(true); // Open the modal
    } catch (error) {
      console.error(error); // Handle error appropriately
    }
  };

  const onSubmitToday = async (flags) => {
    try {
      await axiosInstance.post(
          `/api/${user_id}/today-questions/clear`,
          { check: flags },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
      );
      console.log("Today questions submitted successfully!");
    } catch (error) {
      console.error("Error submitting today questions:", error);
      // Handle error appropriately
    }
  };

  const onSubmitReview = async (flags) => {
    try {
      await axiosInstance.post(
          `/${user_id}/review-questions`,
          { check: flags },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
      );

      console.log("Review questions submitted successfully!");
    } catch (error) {
      console.error("Error submitting review questions:", error);
      // Handle error appropriately
    }
  };

  return (
      <div className="today">
        <div className="today_top">
          <button
              className="create_button"
              onClick={onClickCreate}
              disabled={isCreating} // Disable the button if isCreating is true
          >
            {isCreating ? "Creating..." : "문제 생성"}
          </button>
        </div>
        <div className="today_mid">
          <button className="today_question" onClick={onClickTodayQuestion}>
            오늘 복습 문제
          </button>
          <button className="review_question" onClick={onClickReviewQuestion}>
            복습 주기 문제 확인하기
          </button>
        </div>
        <TodayQuestion
            isOpen={isModalOpen}
            onClose={() => setIsModalOpen(false)}
            questions={todayQuestions}
            setQuestions={setTodayQuestions} // Pass setQuestions to update the state
            onSubmitToday={onSubmitToday} // Pass onSubmitToday function

        />
        <ReviewQuestion
            isOpen={isQuestion}
            onClose={() => setIsQuestion(false)}
            questions={reviewQuestions}
            setQuestions={setReviewQuestions} // Pass setQuestions to update the state
            onSubmitReview={onSubmitReview} // Pass onSubmitReview function
        />
      </div>
  );
};

export default Today;
