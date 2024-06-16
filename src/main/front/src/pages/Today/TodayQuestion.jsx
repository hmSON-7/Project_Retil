import React, { useState } from 'react';
import './TodayQuestion.css';
import QuestionItem from './QuestionItem.jsx';

const TodayQuestion = ({ isOpen, onClose, questions }) => {
    const [currentPage, setCurrentPage] = useState(1);
    const questionsPerPage = 3; // 변경된 부분
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

    return (
        <div className="today_Today">
            <div className="today_test">
                <div className="todayQuestion_top">
                    <span><h2>오늘의 문제!</h2></span>
                </div>
                <div className="todayQuestion_test">
                    {currentQuestions.map((question, index) => (
                        <QuestionItem
                            key={question.id}
                            index={startIndex + index + 1}
                            question={question}
                        />
                    ))}
                </div>
                <div className="pagination">
                    <button onClick={handleClickPrev} disabled={currentPage === 1}>
                        이전
                    </button>
                    <span>{currentPage} / {totalPages}</span>
                    <button onClick={handleClickNext} disabled={currentPage === totalPages}>
                        다음
                    </button>
                </div>
                <button className="close_button" onClick={onClose}>건너뛰기</button>
            </div>
        </div>
    );
};

export default TodayQuestion;
