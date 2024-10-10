import React, { useState, useEffect, useRef } from 'react';

const TypingTitle = () => {
    const [text, setText] = useState('');
    const [count, setCount] = useState(0);
    const [isVisible, setIsVisible] = useState(false);
    const completionWord = '다른 사용자들과 경쟁하여 학습 동기를 높이세요! | 랭킹을 통해 나의 학습 수준을 확인해보세요';
    const splitChar = '|';
    const textRef = useRef(null);

    useEffect(() => {
        const observer = new IntersectionObserver(
            ([entry]) => {
                if (entry.isIntersecting) {
                    setIsVisible(true);
                    observer.unobserve(entry.target);
                }
            },
            { threshold: 0.1 }
        );

        if (textRef.current) {
            observer.observe(textRef.current);
        }

        return () => {
            if (textRef.current) {
                observer.unobserve(textRef.current);
            }
        };
    }, []);

    useEffect(() => {
        if (isVisible && count < completionWord.length) {
            const typingInterval = setInterval(() => {
                setText((prevText) => prevText + completionWord[count]);
                setCount((prevCount) => prevCount + 1);
            }, 100); // 타이핑 속도 (100ms)

            return () => clearInterval(typingInterval); // 컴포넌트가 언마운트될 때 인터벌 정리
        }
    }, [isVisible, count, completionWord]);

    const renderTextWithBreaks = (text) => {
        return text.split(splitChar).map((item, index) => (
            <React.Fragment key={index}>
                {item}
                {index < text.split(splitChar).length - 1 && <br />}
            </React.Fragment>
        ));
    };

    return <h1 ref={textRef} className="typing-style">{renderTextWithBreaks(text)}</h1>;
};

export default TypingTitle;
