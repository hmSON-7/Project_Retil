import React, { useEffect, useState } from 'react';
import './G.css'; // CSS 파일 import

const G = () => {
  const [animate1, setAnimate1] = useState(false); // 1일에서 3일로 애니메이션 상태
  const [animate2, setAnimate2] = useState(false); // 3일에서 한 달로 애니메이션 상태
  const [animate3, setAnimate3] = useState(false); // 한 주에서 한 달로 애니메이션 상태
  const [animate4, setAnimate4] = useState(false); // 6개월에서 추가 기간으로 애니메이션 상태

  useEffect(() => {
    // 1일에서 3일로 애니메이션 시작
    if (!animate1) {
      setTimeout(() => {
        setAnimate1(true);
      }, 1000); // 1초 후 애니메이션 시작
    }

    // 1일에서 일주일로 애니메이션 시작
    if (animate1 && !animate2) {
      setTimeout(() => {
        setAnimate2(true);
      }, 2000); // 2초 후 애니메이션 시작
    }

    // 1일에서 한달로 애니메이션 시작
    if (animate1 && !animate3) {
        setTimeout(() => {
          setAnimate3(true);
        }, 3000); // 3초 후 애니메이션 시작
      }

    // 1일에서 6개월로 애니메이션 시작
    if (animate1 && !animate4) {
        setTimeout(() => {
          setAnimate4(true);
        }, 4000); // 4초 후 애니메이션 시작
      }
    }, [animate1, animate2, animate3, animate4]);


  return (
    <div className="graph-container">
      <svg className="graph-svg" viewBox="0 0 100 50" xmlns="http://www.w3.org/2000/svg">
        {/* 직선 그리기 */}
        <line x1="-150" y1="25" x2="400" y2="25" stroke="black" strokeWidth="2" />

        {/* 1일에서 3일로 곡선 애니메이션 */}
        <path
            className={`animation-path ${animate1 ? 'animate' : ''}`}
            d="M-35,25 Q-10,-20 -5,25"
            fill="none"
            stroke="blue"
            strokeWidth="1"
/>


        {/* 1일에서 일주일로 곡선 애니메이션 */}
        <path
            className={`animation-path2 ${animate2 ? 'animate' : ''}`}
            d="M10,25 Q40,-10 100,25"
            fill="none"
            stroke="red"
            strokeWidth="1"
        />

        {/* 한 주에서 한 달로 곡선 애니메이션 */}
        <path
          className={`animation-path3 ${animate3 ? 'animate' : ''}`}
          d="M120,25 Q140,-10 180,25"
          fill="none"
          stroke="green"
          strokeWidth="1"
        />

        {/* 6개월에서 추가 기간으로 곡선 애니메이션 */}
        <path
          className={`animation-path4 ${animate4 ? 'animate' : ''}`}
          d="M180,25 Q220,-10 260,25"
          fill="none"
          stroke="purple"
          strokeWidth="1"
        />

        {/* 나머지 원형 모양과 마일스톤 라벨 */}
        <circle cx="-35" cy="25" r="5" fill="rgb(77, 173, 228)" stroke="black" strokeWidth="1" />
        <text x="-35" y="40" textAnchor="middle" fontSize="8">1일</text>

        <circle cx="-5" cy="25" r="5" fill="rgb(77, 173, 228)" stroke="black" strokeWidth="1" />
        <text x="-5" y="40" textAnchor="middle" fontSize="8">3일</text>

        <circle cx="40" cy="25" r="5" fill="rgb(77, 173, 228)" stroke="black" strokeWidth="1" />
        <text x="40" y="40" textAnchor="middle" fontSize="8">1주일</text>

        <circle cx="100" cy="25" r="5" fill="rgb(77, 173, 228)" stroke="black" strokeWidth="1" />
        <text x="100" y="40" textAnchor="middle" fontSize="8">한달</text>

        <circle cx="180" cy="25" r="5" fill="rgb(77, 173, 228)" stroke="black" strokeWidth="1" />
        <text x="180" y="40" textAnchor="middle" fontSize="8">6개월</text>
      </svg>
    </div>
  );
};

export default G;
