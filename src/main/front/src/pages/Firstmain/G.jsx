import React, { useEffect } from "react";
import Lottie from "react-lottie";
import Ggg from "../animation/G.json";
import "./G.css";

const G = () => {
  useEffect(() => {
    const first_steps = document.querySelectorAll(".first_step");
    const first_progressBar = document.querySelector(".first_indicator");
    let first_currentStep = 1;

    const updateFirstSteps = () => {
      first_currentStep = first_currentStep < first_steps.length ? first_currentStep + 1 : 1;

      first_steps.forEach((first_step, index) => {
        first_step.classList.toggle("first_active", index < first_currentStep);
      });

      first_progressBar.style.width = `${
        ((first_currentStep - 1) / (first_steps.length - 1)) * 100
      }%`;
    };

    const first_intervalId = setInterval(updateFirstSteps, 570);

    return () => clearInterval(first_intervalId);
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
        <div className="first_steps">
          <div className="first_step">
            <span className="first_circle first_active">1</span>
            <span className="first_progress-label">1일</span>
          </div>
          <div className="first_step">
            <span className="first_circle">3</span>
            <span className="first_progress-label">3일</span>
          </div>
          <div className="first_step">
            <span className="first_circle">7</span>
            <span className="first_progress-label">7일</span>
          </div>
          <div className="first_step">
            <span className="first_circle">15</span>
            <span className="first_progress-label">15일</span>
          </div>
          <div className="first_step">
            <span className="first_circle">30</span>
            <span className="first_progress-label">30일</span>
          </div>
          <div className="first_step">
            <span className="first_circle">60</span>
            <span className="first_progress-label">60일</span>
          </div>
          <div className="first_step">
            <span className="first_circle">180</span>
            <span className="first_progress-label">180일</span>
          </div>
          <div className="first_progress-bar">
            <span className="first_indicator"></span>
          </div>
        </div>
      </div>
    </>
  );
};

export default G;
