import React, { useState } from 'react';
import './GroupChat.css';
import { getTier } from "../../../tier/get_tier.js";
import { rankConverter } from '../tier/rankConverter.js';

const GroupChat = ({ chatData, userId }) => {
    const [message, setMessage] = useState('');
    const sortedChatData = chatData.sort((a, b) => new Date(a.saveTime) - new Date(b.saveTime));

    const handleSendMessage = () => {
        if (message.trim() === '') return; // 빈 메시지 전송 방지
        // 여기에 전송 버튼 클릭 시 메시지 전송 로직을 추가하세요.
        console.log("Message sent:", message);
        setMessage(''); // 메시지 전송 후 입력창 초기화
    };

    const handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            handleSendMessage();
        }
    };

    return (
        <div className="group_chat">
            <div className="chat-container">
                {sortedChatData.map((chat, index) => (
                    <div key={index} className="chat-wrapper">
                        <div className={`chat-message ${chat.user_id === userId ? 'my-message' : 'other-message'}`}>
                            <img
                                className="user-rank"
                                src={getTier(rankConverter(chat.user_rank))}
                                alt={chat.user_rank}
                            />
                            <div className="chat-content">
                                <div className="chat-nickname">{chat.nickname}</div>
                                <div className="chat-text">{chat.text}</div>
                            </div>
                        </div>
                        <div className={`chat-time ${chat.user_id === userId ? 'my-time' : 'other-time'}`}>
                            {new Date(chat.saveTime).toLocaleTimeString()}
                        </div>
                    </div>
                ))}
            </div>
            <div className="chat-input-container">
                <input
                    type="text"
                    className="chat-input"
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    onKeyPress={handleKeyPress}
                    placeholder="메시지를 입력하세요..."
                />
                <button onClick={handleSendMessage} className="chat-send-button">전송</button>
            </div>
        </div>
    );
};

export default GroupChat;
