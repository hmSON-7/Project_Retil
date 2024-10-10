import React, { useState } from "react";
import "./QuestionItem.css";

const QuestionItem = ({ index, question, onFlagChange }) => {
    const [userAnswer, setUserAnswer] = useState('');
    const [showAnswer, setShowAnswer] = useState(false);
    const [re, setRe] = useState(question.flag); // Initialize with question.flag

    const handleShowAnswer = () => {
        setShowAnswer(!showAnswer);
    };

    const handleRe = () => {
        const newRe = !re;
        setRe(newRe);
        onFlagChange(question.id, newRe);
    };

    return (
        <div className="question_item">
            <p><strong>문제 {index}:</strong> {question.content}</p>
            <input
                type="text"
                value={userAnswer}
                onChange={(e) => setUserAnswer(e.target.value)}
                placeholder="정답을 입력해주세요!"
                disabled={showAnswer}
            />
            <input
                type="text"
                className="question_answer"
                value={showAnswer ? question.answer : ''}
                placeholder="정답을 보려면 버튼을 클릭하세요!"
                readOnly
            />
            <button onClick={handleShowAnswer}>
                {showAnswer ? '정답 숨기기' : '정답 보기'}
            </button>
            <button className="re_button" onClick={handleRe}>
                {re ? '다시 보기 취소' : '다시 보기'}
            </button>
        </div>
    );
};

export default QuestionItem;
