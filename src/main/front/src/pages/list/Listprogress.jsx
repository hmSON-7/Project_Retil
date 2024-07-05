import React, { useState, useEffect } from 'react';
import './Listprogress.css';

const Listprogress = ({ til }) => {
  const steps = ["1", "3", "7", "30", "60", "6M"];
  const stepWidths = [0, 10, 20, 40, 69, 100]; // 수정된 너비 값

  const getCurrentStep = (til) => {
    if (til < 1) return -1; // 1일 미만
    if (til <= 1) return 0; // 1일 이하
    if (til <= 3) return 1; // 3일 이하
    if (til <= 7) return 2; // 7일 이하
    if (til <= 30) return 3; // 30일 이하
    if (til <= 60) return 4; // 60일 이하
    return 5; // 6개월 이상
  };

  const [currentStep, setCurrentStep] = useState(getCurrentStep(til));
  const [missedSteps, setMissedSteps] = useState([false,false,false,false,false,false]);

  useEffect(() => {
    setCurrentStep(getCurrentStep(til));

    const checkMissedSteps = () => {
      const newMissedSteps = [
        !til.aday,
        !til.threeDays,
        !til.aweek,
        !til.fifteenDays,
        !til.amonth,
        !til.twoMonths && !til.sixMonths // 두 달 또는 6개월을 만족하지 않는 경우
      ];
      setMissedSteps(newMissedSteps);
    };

    checkMissedSteps();
  }, [til]);

  const getProgressWidth = (stepIndex) => {
    if (stepIndex === -1) return '0%';
    return `${stepWidths[stepIndex]}%`;
  };

  return (
      <div className="step-progress-bar">
        <div className="step-labels">
          {steps.map((step, index) => (
              <div
                  key={index}
                  className={`step step-${index} ${index <= currentStep && currentStep !== -1 ? 'current' : ''} ${missedSteps[index] ? 'missed' : ''} ${til[step.toLowerCase()] ? 'completed' : ''}`}
              >
                {step}
              </div>
          ))}
        </div>
        <div className="progress-line" style={{ width: getProgressWidth(currentStep) }}></div>
      </div>
  );
};

export default Listprogress;
