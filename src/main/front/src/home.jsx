import React, { useEffect, useState } from 'react';
function Home() {
    const [message, setMessage] = useState("");

    useEffect(() => {
        fetch('/api/home')
            .then(response => response.text())
            .then(data => {
                setMessage(data);
            });
    }, []);

    const style = {
        border: '30px solid #4CAF50', // 테두리 색상을 변경하려면 이 값을 변경하세요.
        padding: '10px',
        margin: '10px',
        borderRadius: '5px'
    };

    return (
        <div style={style}>
            {message}
        </div>
    );
}

export default Home;
