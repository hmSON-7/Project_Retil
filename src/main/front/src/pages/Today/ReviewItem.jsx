import React, {useState} from "react";
import "./ReviewItem.css";

const ReviewItem = ({ index, question, onFlagChange }) => {
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
        <div className="reviewItem">
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
                className="review_answer"
                value={showAnswer ? question.answer : ''}
                placeholder="정답을 보려면 버튼을 클릭하세요!"
                readOnly
            />
            <button onClick={handleShowAnswer}>
                {showAnswer ? '정답 숨기기' : '정답 보기'}
            </button>
            <button className="review_button" onClick={handleRe}>
                {re ? '다시 보기 취소' : '다시 보기'}
            </button>
</div>
)
    ;
};

export default ReviewItem;
