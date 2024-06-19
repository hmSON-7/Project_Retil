// TodayQuestion 컴포넌트에서 Listprogress 컴포넌트로 submissionDate을 전달하는 예시

import React, { useState } from 'react';
import './TodayQuestion.css';
import QuestionItem from './QuestionItem';
import Listprogress from '../list/Listprogress'; // Listprogress 컴포넌트를 import

const TodayQuestion = ({ isOpen, onClose, questions, setQuestions, onSubmitToday }) => {
    const [currentPage, setCurrentPage] = useState(1);
    const questionsPerPage = 3;
    const totalPages = Math.ceil(questions.length / questionsPerPage);
    const [submissionDate, setSubmissionDate] = useState(null); // 제출일 상태 추가

    if (!isOpen) return null;

    const handleClickPrev = () => {
        setCurrentPage(prevPage => Math.max(prevPage - 1, 1));
    };

    const handleClickNext = () => {
        setCurrentPage(prevPage => Math.min(prevPage + 1, totalPages));
    };

    const startIndex = (currentPage - 1) * questionsPerPage;
    const currentQuestions = questions.slice(startIndex, startIndex + questionsPerPage);

    const handleFlagChange = (id, flag) => {
        const updatedQuestions = questions.map(question =>
            question.id === id ? { ...question, flag } : question
        );
        setQuestions(updatedQuestions);
    };

    const handleTodaySubmit = () => {
        const flags = questions.map(question => question.flag);
        onSubmitToday(flags);
        setSubmissionDate(new Date()); // 제출일 설정
        onClose();
    };

    return (
        <div className="today_Today">
            <div className="today_test">
                <div className="todayQuestion_top">
          <span>
            <h2>오늘의 문제!</h2>
          </span>
                </div>
                <div className="todayQuestion_test">
                    {currentQuestions.map((question, index) => (
                        <QuestionItem
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
                    <button onClick={handleClickNext} disabled={currentPage === totalPages}>
                        다음
                    </button>
                </div>
                {onSubmitToday && (
                    <button className="flag_button" onClick={handleTodaySubmit}>
                        제출하기
                    </button>
                )}
                <button className="close_button" onClick={onClose}>
                    건너뛰기
                </button>
            </div>
            {/* Listprogress 컴포넌트 추가 */}
            {submissionDate && (
                <Listprogress currentStep={0} missedSteps={[true, true, true, true, true, true]} submissionDate={submissionDate} />
            )}
        </div>
    );
};

export default TodayQuestion;
