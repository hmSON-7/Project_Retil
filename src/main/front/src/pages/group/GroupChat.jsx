import React, { useState, useEffect } from 'react';
import './GroupChat.css';
import { getTier } from "../../../tier/get_tier.js";
import { rankConverter } from '../tier/rankConverter.js';
import axiosInstance from "../../api/axiosInstance.js";

const GroupChat = ({ userId, groupId }) => {
    const [chatData, setChatData] = useState([]);
    const [message, setMessage] = useState('');
    const token = localStorage.getItem("token");

    const fetchChatData = async () => {
        try {
            const response = await axiosInstance.get(`/group/${groupId}/chat`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setChatData(response.data);
        } catch (error) {
            console.error('Failed to fetch chat data:', error);
        }
    };

    useEffect(() => {
        fetchChatData();
    }, [groupId]);

    const handleSendMessage = async () => {
        if (message.trim() === '') return; // 빈 메시지 전송 방지

        try {
            const response = await axiosInstance.post(
                `/group/${groupId}/chat`,
                { user_id: userId, chat: message },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json"
                    }
                }
            );

            if (response.status === 200) {
                // 전송이 성공하면 채팅 데이터를 다시 불러오거나 업데이트 로직 추가
                fetchChatData(); // Refresh chat data after sending message
            } else {
                console.error("Failed to send message");
            }
        } catch (error) {
            console.error("Error sending message:", error);
        }

        setMessage(''); // 메시지 전송 후 입력창 초기화
    };

    const handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            handleSendMessage();
        }
    };

    const sortedChatData = chatData.sort((a, b) => new Date(a.stampTime) - new Date(b.stampTime));

    return (
        <div className="group_chat">
            <div className="chat-container">
                {sortedChatData.map((chat, index) => (
                    <div key={index} className="chat-wrapper">
                        <div className={`chat-message ${chat.user_id === userId ? 'my-message' : 'other-message'}`}>
                            <img
                                className="user-rank"
                                src={getTier(rankConverter(chat.userRank))}
                                alt={chat.userRank}
                            />
                            <div className="chat-content">
                                <div className="chat-nickname">{chat.nickname}</div>
                                <div className="chat-text">{chat.chat}</div>
                            </div>
                        </div>
                        <div className={`chat-time ${chat.user_id === userId ? 'my-time' : 'other-time'}`}>
                            {new Date(chat.stampTime).toLocaleTimeString()}
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
