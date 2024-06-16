import React from 'react';
import './Todayquestion.css'; // 스타일 파일을 가져옵니다.

const Todayquestion = () => {
  return (
    <section id="todaymoon">
        <img className="AiIconimage" src="src/assets/AiIcon.png" alt="ai사진" />
            <span className='todaymoon-sepcial'>특별한 복습 효과</span>
            

        <div className="todaymoon-answer" >오늘의 문제를 통해 학습 내용을 확인하고,<br></br> 복습 효과를 높이세요!</div>
        {/* <img className="todaymoonIcon" src="src/assets/todaymoonIcon.png" alt="오늘문제사진" /> */}


    </section>
  );
};

export default Todayquestion;
