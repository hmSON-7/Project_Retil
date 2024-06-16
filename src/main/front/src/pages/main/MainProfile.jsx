import React, { useEffect, useState } from "react";
import "./MainProfile.css";
import {useNavigate} from "react-router-dom";
import MainProgress from "./MainProgress";
import TimeSettingModal from "./TimeSettingModal";
import prebutton from "../../assets/prebuttongroup.png";
import nextbutton from "../../assets/nextbuttongroup.png";

import { getTier } from "../../../tier/get_tier.js";
import { rankConverter } from '../tier/rankConverter.js';
import axiosInstance from "../../api/axiosInstance.js";

const token = localStorage.getItem("token");
const user_id = localStorage.getItem("user_id");

const MainProfile = () => {
    const [group, setGroup] = useState([]);
    const [hour, setHour] = useState(0);
    const [minute, setMinute] = useState(0);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [currentIndex, setCurrentIndex] = useState(0);
    const [todayStudyTime, setTodayStudyTime] = useState(0);
    const [userRank, setUserRank] = useState(1);

    const openModal = () => setIsModalOpen(true);
    const closeModal = () => setIsModalOpen(false);

    const navigate = useNavigate(); // Initialize useNavigate

    useEffect(() => {
        const fetchMainData = async () => {
            try {
                const response = await axiosInstance.get(`users/main/${user_id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                const data = response.data;
                setTodayStudyTime(data.todayStudied);
                setUserRank(data.userRank);
            } catch (error) {
                console.error("Error fetching main data:", error);
            }
        };

        const fetchMyGroupData = async () => {
            try {
                const response = await axiosInstance.get(`/group/${user_id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setGroup(response.data);
            } catch (error) {
                console.error("Error fetching my group data:", error);
            }
        };

        fetchMainData();
        fetchMyGroupData();
    }, []);

    const handleSave = (newHour, newMinute) => {
        setHour(newHour);
        setMinute(newMinute);
    };

    const calculateTotalMinutes = (hours, minutes) => {
        return hours * 60 + minutes;
    };

    const targetTimeInMinutes = calculateTotalMinutes(hour, minute);
    const todayStudyTimeInMinutes = Math.floor(todayStudyTime / 60);
    const progressPercent = targetTimeInMinutes > 0 ? (todayStudyTimeInMinutes / targetTimeInMinutes) * 100 : 0;

    const handleNext = () => {
        setCurrentIndex((prevIndex) => (prevIndex + 1) % group.length);
    };

    const handlePrev = () => {
        setCurrentIndex((prevIndex) => (prevIndex === 0 ? group.length - 1 : prevIndex - 1));
    };

    const formatTime = (totalSeconds) => {
        const hours = Math.floor(totalSeconds / 3600);
        const minutes = Math.floor((totalSeconds % 3600) / 60);
        const seconds = totalSeconds % 60;
        return `${hours}시간 ${minutes}분 ${seconds}초`;
    };
    const formattedTime = formatTime(todayStudyTime);
    const tierImageSrc = getTier(rankConverter(userRank));

    const handleGroupClick = (groupId) => {
        navigate(`/groupRoom/${groupId}`);
    };


    return (
        <>
            <div className="profile">
                <span>오늘 공부한 시간 / 내 목표 시간</span>
                <div className="target_Time">
                <span>
                    {formattedTime} / {hour}시간 {minute}분
                </span>
                </div>
                <div className="setting_Button">
                    <button onClick={openModal}></button>
                </div>
                <div className="setting">
                    <span>내 목표시간을 설정해 보세요.</span>
                </div>
                <div className="main_Progress">
                    <MainProgress percent={progressPercent}/>
                </div>
                <div className="division_line"></div>
                <div className="profile_Tier">
                    <img src={tierImageSrc} alt="Tier"/>
                </div>
                <div className="profile_group">
                    <button className="pre_button" onClick={handlePrev}></button>
                    {group.length > 0 ? (
                        <div className="profile_group_item" onClick={() => handleGroupClick(group[currentIndex].id)}>
                            <span>{group[currentIndex].groupName}</span>
                        </div>
                    ) : (
                        <div className="profile_group_item">
                            <span>내 그룹이 없습니다.</span>
                        </div>
                    )}
                    <button className="next_button" onClick={handleNext}></button>
                </div>
                <div className="division_line2"></div>
                <TimeSettingModal
                    isOpen={isModalOpen}
                    onRequestClose={closeModal}
                    onSave={handleSave}
                />

            </div>

        </>

    );
};

export default MainProfile;
