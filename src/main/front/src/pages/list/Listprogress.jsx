import React, { useState, useEffect } from 'react';
import './Listprogress.css';

const Listprogress = ({ til }) => {
  const steps = ["1", "3", "7", "30", "60", "6M"];

  // 각 단계별 너비 정의 (%)
  const stepWidths = [0, 10, 20, 40, 69, 100]; // 수정된 너비 값

  // til 값에 따라 currentStep 결정
  const getCurrentStep = (til) => {
    if (til <= 1) return 0; // 1일 이하
    if (til <= 3) return 1; // 3일 이하
    if (til <= 7) return 2; // 7일 이하
    if (til <= 30) return 3; // 30일 이하
    if (til <= 60) return 4; // 60일 이하
    return 5; // 6개월 이상
  };

  const [currentStep, setCurrentStep] = useState(getCurrentStep(til));
  const [missedSteps, setMissedSteps] = useState([false, true, false, false, false, false]); // 3일 스텝만 missed

  useEffect(() => {
    setCurrentStep(getCurrentStep(til));
  }, [til]);

  // 진행 막대의 너비를 계산하는 함수
  const getProgressWidth = (stepIndex) => {
    return `${stepWidths[stepIndex]}%`;
  };

  return (
    <div className="step-progress-bar">
      {/* 단계 표시 */}
      <div className="step-labels">
        {steps.map((step, index) => (
          <div
            key={index}
            className={`step step-${index} ${index <= currentStep ? 'current' : ''} ${missedSteps[index] ? 'missed' : ''}`}
            onClick={() => setCurrentStep(index)}
          >
            {step}
          </div>
        ))}
      </div>

      {/* 진행 막대 */}
      <div className="progress-line" style={{ width: getProgressWidth(currentStep) }}></div>
    </div>
  );
};

export default Listprogress;
