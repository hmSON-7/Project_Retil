import React, { useState, useRef, useEffect } from 'react';
import './ListIntroduce.css';
import listpage1 from '../../assets/listpage1.png';

const ListIntroduce = () => {
    const [isInViewport, setIsInViewport] = useState(false);
    const imageRef = useRef(null);

    useEffect(() => {
        if (!imageRef.current) return;

        const callback = (entries) => {
            entries.forEach((entry) => {
                if (entry.isIntersecting) {
                    setIsInViewport(true);
                }
            });
        };

        const options = {
            root: null,
            rootMargin: '0px',
            threshold: 0.1
        };

        const observer = new IntersectionObserver(callback, options);
        observer.observe(imageRef.current);

        return () => {
            observer.disconnect();
        };
    }, []);

    return (
        <section id="list">
            <div className="list-content">
                <div className="list-left">
                    <div className="list-text">
                        <div className="bold-text">리스트</div>
                     
                    </div>
                    <div className="additional-text1">
                        당신의 학습을 체계적으로 관리하세요!<br />
                        카테고리를 색상을 지정하여 학습한 내용을 시각적으로 구분하세요
                    </div>
                    <div className="additional-text2">
                        
                        복습 일정을 설정하고 단기기억을 장기기억으로 변환하세요!!
                    </div>
                </div>
                <div className={`list-right ${isInViewport ? 'frame-in' : ''}`} ref={imageRef}>
                    <img src={listpage1} alt="리스트 페이지" className="list-image" />
                </div>
            </div>
        </section>
    );
};

export default ListIntroduce;
