import React, { useEffect } from "react";
import Lottie from "react-lottie";
import Ggg from "../pages/animation/G.json";
import "./G.css";

const G = () => {
  useEffect(() => {
    const steps = document.querySelectorAll(".step");
    const progressBar = document.querySelector(".indicator");
    let currentStep = 1;

    const updateSteps = () => {
      currentStep = currentStep < steps.length ? currentStep + 1 : 1;

      steps.forEach((step, index) => {
        step.classList.toggle("active", index < currentStep);
      });

      progressBar.style.width = `${
          ((currentStep - 1) / (steps.length - 1)) * 100
      }%`;
    };

    const intervalId = setInterval(updateSteps, 570);

    return () => clearInterval(intervalId);
  }, []);

  const defaultOptions = {
    loop: true,
    autoplay: true,
    animationData: Ggg,
    rendererSettings: {
      preserveAspectRatio: "xMidYMid slice",
    },
  };

  return (
      <>
        <div className="lottie-container">
          <Lottie
              isClickToPauseDisabled={true}
              options={defaultOptions}
              height={"100%"}
              width={"100%"}
          />
        </div>
        <div className="gcontainer">
          <div className="steps">
            <div className="step">
              <span className="circle active">1</span>
              <span className="progress-label">1일</span>
            </div>
            <div className="step">
              <span className="circle">3</span>
              <span className="progress-label">3일</span>
            </div>
            <div className="step">
              <span className="circle">7</span>
              <span className="progress-label">7일</span>
            </div>
            <div className="step">
              <span className="circle">15</span>
              <span className="progress-label">15일</span>
            </div>
            <div className="step">
              <span className="circle">30</span>
              <span className="progress-label">30일</span>
            </div>
            <div className="step">
              <span className="circle">60</span>
              <span className="progress-label">60일</span>
            </div>
            <div className="step">
              <span className="circle">180</span>
              <span className="progress-label">180일</span>
            </div>
            <div className="progress-bar">
              <span className="indicator"></span>
            </div>
          </div>
        </div>
      </>
  );
};

export default G;
