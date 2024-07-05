import React from 'react';
import TypingTitle from './TypingTitle'; // TypingTitle 컴포넌트 import
import './FirstTier.css';
import bronze from "../../assets/bronze.jpg";
import silver from "../../assets/silver.jpg";
import gold from "../../assets/gold.jpg";
import Diamond from "../../assets/diamond.jpg";
import platinum from "../../assets/platinum.jpg";

const FirstTier = () => {
    return (
        <div className="first-tier-container">
            <TypingTitle /> {/* TypingTitle 컴포넌트 사용 */}

            <div className="cards-container">
                <div className="card-container">
                    <div className="card">
                        <div className="card-back">
                            <img src={bronze} alt="Bronze" />
                        </div>
                        <div className="card-front">
                            학습 여정을 시작한 <br /> 초심자
                        </div>
                    </div>
                </div>
                <div className="card-container">
                    <div className="card">
                        <div className="card-back">
                            <img src={silver} alt="Silver" />
                        </div>
                        <div className="card-front">
                            기본기를 다지고 있는 <br /> 학습자
                        </div>
                    </div>
                </div>
                <div className="card-container">
                    <div className="card">
                        <div className="card-back">
                            <img src={gold} alt="Gold" />
                        </div>
                        <div className="card-front">
                            꾸준한 노력으로 <br />높은 성과를 보이는 <br /> 학습자
                        </div>
                    </div>
                </div>
                <div className="card-container">
                    <div className="card">
                        <div className="card-back">
                            <img src={platinum} alt="Platinum"/>
                        </div>
                        <div className="card-front">
                            우수한 성과로 커뮤니티에서 인정받는 학습자
                        </div>
                    </div>
                </div>
                <div className="card-container">
                    <div className="card">
                        <div className="card-back">
                            <img src={Diamond} alt="Diamond" />
                        </div>
                        <div className="card-front">
                            최상위 성과를 달성한 <br />최고 수준의 학습자
                        </div>
                    </div>
                </div>
            </div>
            <div className="additional-description">
                학습 시간, 문제 풀이 성적 등을 기반으로 한 사용자 랭킹 제공 !<br />
                사용자별 프로필 페이지 제공, 학습 성과를 시각적으로 보여줌 <br />
                상위 랭커에게 배지를 제공하여 학습 동기 부여
            </div>
        </div>
    );
};

export default FirstTier;
