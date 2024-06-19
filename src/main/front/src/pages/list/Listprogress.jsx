import React from 'react';
import './Listprogress.css';

const Listprogress = ({ submissionDate }) => {
  const steps = ["1", "3", "7", "30", "60", "6M"];
  const stepWidths = [0, 10, 20, 40, 69, 100];

  // 각 단계별 진행 상황을 날짜에 따라 계산하는 함수
  const calculateProgress = (submissionDate) => {
    const currentDate = new Date(); // 현재 날짜
    const daysDiff = Math.ceil((currentDate - new Date(submissionDate)) / (1000 * 60 * 60 * 24)); // 제출일로부터 몇 일이 지났는지 계산

    if (daysDiff >= 0) {
      // daysDiff에 따라 단계별 진행 상황 계산
      if (daysDiff >= 180) return 5;   // 6개월 이상
      if (daysDiff >= 60) return 4;    // 2개월 이상
      if (daysDiff >= 30) return 3;    // 한 달 이상
      if (daysDiff >= 15) return 2;    // 15일 이상
      if (daysDiff >= 7) return 1;     // 일주일 이상
      if (daysDiff >= 1) return 0;     // 1일 이상
      return 0;  // 1일 미만은 첫 번째 단계
    } else {
      return 0; // 제출일이 현재 날짜보다 미래일 경우 첫 번째 단계
    }
  };

  // 진행 막대의 너비를 계산하는 함수
  const getProgressWidth = (stepIndex) => {
    return `${stepWidths[stepIndex]}%`;
  };

  // 제출일을 기준으로 현재 단계 계산
  const calculatedStep = calculateProgress(submissionDate);

  return (
      <div className="step-progress-bar">
        {/* 단계 표시 */}
        <div className="step-labels">
          {steps.map((step, index) => (
              <div
                  key={index}
                  className={`step step-${index} ${index <= calculatedStep ? 'current' : ''}`}
              >
                {step}
              </div>
          ))}
        </div>

        {/* 진행 막대 */}
        <div className={`progress-line`} style={{ width: getProgressWidth(calculatedStep) }}></div>
      </div>
  );
};

export default Listprogress;
