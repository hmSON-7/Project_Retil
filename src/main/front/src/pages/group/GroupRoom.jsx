import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./GroupRoom.css";
import Mainp from "../mainprofilpage/Mainp";
import GroupMember from "./GroupMember.jsx";
import GroupChat from "./GroupChat.jsx";
import axiosInstance from "../../api/axiosInstance.js";
import groupChat from "./GroupChat.jsx";

const mockChat = [
    {
        groupId: 1,
        user_id: 1,
        user_rank:"unRanked",
        nickname:"닭볶음탕수육",
        text: "아 똥 마렵다",
        saveTime: new Date(new Date().getTime() + 5000).toISOString(),
    },
    {
        groupId: 1,
        user_id: 2,
        user_rank:"Diamond",
        nickname:"배배",
        text: "아 똥 마렵다",
        saveTime: new Date(new Date().getTime() + 3000).toISOString(),
    },
    {
        groupId: 1,
        user_id: 3,
        user_rank:"unRanked",
        nickname:"닭볶음탕수육ㅇㄹㅁㄴㅇㄹ",
        text: "아 똥 마렵다",
        saveTime: new Date(new Date().getTime() + 4000).toISOString(),
    },
    {
        groupId: 1,
        user_id: 3,
        user_rank:"unRanked",
        nickname:"닭볶음탕수육ㅇㄹㅁㄴㅇㄹ",
        text: "아 똥 마렵다",
        saveTime: new Date(new Date().getTime() + 4000).toISOString(),
    }
    ,
    {
        groupId: 1,
        user_id: 3,
        user_rank:"unRanked",
        nickname:"닭볶음탕수육ㅇㄹㅁㄴㅇㄹ",
        text: "아 똥 마렵다",
        saveTime: new Date(new Date().getTime() + 4000).toISOString(),
    }
    ,
    {
        groupId: 1,
        user_id: 3,
        user_rank:"unRanked",
        nickname:"닭볶음탕수육ㅇㄹㅁㄴㅇㄹ",
        text: "아 똥 마렵다",
        saveTime: new Date(new Date().getTime() + 4000).toISOString(),
    }
    ,
    {
        groupId: 1,
        user_id: 3,
        user_rank:"unRanked",
        nickname:"닭볶음탕수육ㅇㄹㅁㄴㅇㄹ",
        text: "아 똥 마렵다",
        saveTime: new Date(new Date().getTime() + 4000).toISOString(),
    }
];

const GroupRoom = () => {
    const { groupId } = useParams();
    const [memberList, setMemberList] = useState([]);
    const token = localStorage.getItem("token");
    const user_id = parseInt(localStorage.getItem("user_id"), 10);
    const [memberMe, setMemberMe] = useState(null);
    const [chatList, setChatList] = useState([])
    useEffect(() => {
        const fetchGroupData = async () => {
            try {
                const response = await axiosInstance.get(`/group/${groupId}/details`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                const m_List = response.data.memberList;
                setMemberList(m_List);
                const me = m_List.find(member => member.userId === user_id);
                setMemberMe(me);
            } catch (error) {
                console.error('Failed to fetch group data:', error);
            }
        };
        const fetchChatData = async () => {
            try {
                const response = await axiosInstance.get(`/group/${groupId}/chat`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setChatList(response.data);
            } catch (error) {
                console.error('Failed to fetch chat data:', error);
            }
        };

        fetchGroupData();
        fetchChatData();
    }, [groupId, token, user_id]);


    return (
        <>
            <Mainp />
            <div className="group_contain">
                <div className="group_member">
                    {memberMe && <GroupMember memberList={memberList} memberMe={memberMe} />}
                </div>
                <div className="group_Chat">
                    <GroupChat chatData={chatList} userId={user_id} groupId={groupId} />
                </div>
                <div></div>
            </div>
        </>
    );
};

export default GroupRoom;
