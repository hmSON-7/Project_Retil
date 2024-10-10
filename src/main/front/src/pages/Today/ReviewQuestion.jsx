import React, { useState } from "react";
import "./ReviewQuestion.css";
import ReviewItem from "./ReviewItem.jsx";

const ReviewQuestion = ({ isOpen, onClose, questions, setQuestions, onSubmitReview }) => {
    const [currentPage, setCurrentPage] = useState(1);
    const questionsPerPage = 3;
    const totalPages = Math.ceil(questions.length / questionsPerPage);

    if (!isOpen) return null;

    const handleClickPrev = () => {
        setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
    };

    const handleClickNext = () => {
        setCurrentPage((prevPage) => Math.min(prevPage + 1, totalPages));
    };

    const startIndex = (currentPage - 1) * questionsPerPage;
    const currentQuestions = questions.slice(startIndex, startIndex + questionsPerPage);

    const handleFlagChange = (id, flag) => {
        const updatedQuestions = questions.map((question) =>
            question.id === id ? { ...question, flag } : question
        );
        setQuestions(updatedQuestions);
    };

    const handleReviewSubmit = () => {
        const flags = questions.map((question) => question.flag);
        onSubmitReview(flags);
        onClose();
    };

    return (
        <div className="reviewQuestion_Review">
            <div className="review_test">
                <div className="reviewQuestion_top">
                    <span>
                        <h2>복습 주기 문제 확인하기</h2>
                    </span>
                </div>
                <div className="reviewQuestion_test">
                    {currentQuestions.map((question, index) => (
                        <ReviewItem
                            key={question.id}
                            index={startIndex + index + 1}
                            question={question}
                            onFlagChange={handleFlagChange}
                        />
                    ))}
                </div>
                <div className="pagination">
                    <button onClick={handleClickPrev} disabled={currentPage === 1}>
                        이전
                    </button>
                    <span>
                        {currentPage} / {totalPages}
                    </span>
                    <button
                        onClick={handleClickNext}
                        disabled={currentPage === totalPages}
                    >
                        다음
                    </button>
                </div>
                {onSubmitReview && (
                    <button className="flag_button" onClick={handleReviewSubmit}>
                        제출하기
                    </button>
                )}
                <button className="close_button" onClick={onClose}>
                    건너뛰기
                </button>
            </div>
        </div>
    );
};

export default ReviewQuestion;
