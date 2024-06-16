import React, { useState, useEffect } from 'react';
import './Listprogress.css';

const Listprogress = ({ til }) => {
  const steps = ["1일", "3일", "7일", "30일", "60일", "6개월"];

  // 각 단계별 너비 정의 (%)
  const stepWidths = [0, 10, 30, 50, 70, 100];

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
            className={`step step-${index} ${index <= currentStep ? 'current' : ''}`}
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
