import React, { useState, useEffect } from 'react';
import axiosInstance from "../../api/axiosInstance.js";
import TodayQuestion from "./TodayQuestion";
import Listprogress from "./Listprogress";

const ParentComponent = () => {
    const [questions, setQuestions] = useState([]);
    const [currentStep, setCurrentStep] = useState(0);
    const [missedSteps, setMissedSteps] = useState([true, true, true, true, true, true]);

    // 사용자 정보 가져오기
    const user_id = localStorage.getItem("user_id");
    const token = localStorage.getItem("token");

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axiosInstance.get(`/til/${user_id}/`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                const tilData = response.data;

                const currentStepIndex = getCurrentStep(tilData);
                const missedStepsArray = getMissedSteps(tilData);

                setCurrentStep(currentStepIndex);
                setMissedSteps(missedStepsArray);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, [user_id, token]);

    const getCurrentStep = (tilData) => {
        if (tilData.sixMonths) return 5;
        if (tilData.twoMonths) return 4;
        if (tilData.aMonth) return 3;
        if (tilData.fifteenDays) return 2;
        if (tilData.aWeek) return 1;
        if (tilData.threeDays) return 0;
        if (tilData.aDay) return 0;
        return 0; // Default to the first step
    };

    const getMissedSteps = (tilData) => {
        return [
            !tilData.aDay,
            !tilData.threeDays,
            !tilData.aWeek,
            !tilData.fifteenDays,
            !tilData.aMonth,
            !tilData.twoMonths,
            !tilData.sixMonths,
        ];
    };

    const handleSubmitToday = async (flags) => {
        try {
            // Submit today's questions API 호출
            await axiosInstance.post(`/til/submit/${user_id}/`, { flags }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            // 데이터 다시 가져오기
            const response = await axiosInstance.get(`/til/${user_id}/`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            const tilData = response.data;

            const currentStepIndex = getCurrentStep(tilData);
            const missedStepsArray = getMissedSteps(tilData);

            setCurrentStep(currentStepIndex);
            setMissedSteps(missedStepsArray);
        } catch (error) {
            console.error('Error submitting today\'s questions:', error);
        }
    };

    return (
        <div>
            <TodayQuestion
                isOpen={true}
                onClose={() => {}}
                questions={questions}
                setQuestions={setQuestions}
                onSubmitToday={handleSubmitToday}
            />
            <Listprogress
                currentStep={currentStep}
                missedSteps={missedSteps}
            />
        </div>
    );
};

export default ParentComponent;
