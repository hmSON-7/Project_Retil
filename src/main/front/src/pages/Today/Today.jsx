import React, { useState } from 'react';
import "./Today.css";
import axiosInstance from "../../api/axiosInstance.js";
import TodayQuestion from '../Today/TodayQuestion.jsx';

const Today = () => {
    const token = localStorage.getItem("token");
    const user_id = localStorage.getItem("user_id");
    const [todayQuestions, setTodayQuestions] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);

    const onClickCreate = async () => {
        try {
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
            const extractedQuestions = response.data.map(question => ({
                id: question.id,
                content: question.question.content,
                answer: question.question.answer,
            }));
            setTodayQuestions(extractedQuestions); // Set the questions in the state
            setIsModalOpen(true); // Open the modal
        } catch (error) {
            console.error(error); // Handle error appropriately
        }
    };

    return (
        <div className="today">
            <div className="today_top">
                <button className="create_button" onClick={onClickCreate}>문제 생성</button>
            </div>
            <div className="today_mid">
                <button className="today_question" onClick={onClickTodayQuestion}>오늘 복습 문제</button>
                <button>복습 주기 문제 확인하기</button>
            </div>
            <TodayQuestion
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                questions={todayQuestions}
            />
        </div>
    );
}

export default Today;
