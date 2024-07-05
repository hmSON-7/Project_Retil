import React from 'react';
import './Todayquestion.css'; // 스타일 파일을 가져옵니다.
import todoreview1 from '../../../public/images/ico/todoreview1.png';
import todoreview2 from '../../../public/images/ico/todoreview2.png';
import todoreview3 from '../../../public/images/ico/todoreview3.png';

const Todayquestion = () => {
    return (
        <section id="todaymoon">
            <div className="hr-box">
            <div className="hr-sect"><h1>| Today Question |</h1></div>
            </div>
            <div className="sub-context">
            <p className="subheading"><b>AI</b>, <b>Test</b>, <b>Review</b> in <b>RE:TIL</b></p>
            <p className="subsubheading">오늘의 문제를 통해 학습 내용을 확인하고, 복습 효과를 높이세요!</p>
            </div>
            <div className="Today_box">
                <img className="todoreview1-image" src={todoreview1} alt="복습 1" />
                <div className="image-text1">오늘의 문제 생성<br/><br/> 요약본을 생성하고 AI를 이용한 <br/>
                 '오늘의 문제'를 자동 생성합니다</div>
                <img className="todoreview2-image" src={todoreview2} alt="복습 2" />
                <div className="image-text2">주기적 복습<br/><br/>직접 구현한 알고리즘으로 <br/>사용자는 주기적으로 복습할 문제를 <br/>
                제시해 기억을 강화합니다</div>
                <img className="todoreview3-image" src={todoreview3} alt="복습 3" />
                <div className="image-text3">학습 결과 분석<br/><br/> 학습 결과를 분석하고 정답을 확인하여
                    <br/>복습 학습 효과를 확인합니다</div>
                </div>
                
                
        </section>
    );
};

export default Todayquestion;
