import React from "react";
import "./GroupMember.css";
import { getTier } from "../../../tier/get_tier.js";
import { rankConverter } from '../tier/rankConverter.js';

const GroupMember = ({ memberList, memberMe }) => {
    const otherMembers = memberList.filter(member => member.userId !== memberMe.userId).slice(0, 4);
    const myTierImageSrc = getTier(rankConverter(memberMe.userRank));

    const formatTime = (totalSeconds) => {
        const hours = Math.floor(totalSeconds / 3600);
        const minutes = Math.floor((totalSeconds % 3600) / 60);
        const seconds = totalSeconds % 60;
        return `${hours}시간 ${minutes}분 ${seconds}초`;
    };

    return (
        <div className="member_list">
            <div className="member_me">
                <div>{memberMe.nickname}</div>
                <div className="member_card">
                    <img src={myTierImageSrc} alt="Tier" className="tier_image"/>
                </div>
                <div>오늘 공부한 시간: {formatTime(memberMe.todayStudyTime)}</div>
            </div>
            <div className="member_you">
                {otherMembers.map((member) => {
                    const memberTierImageSrc = getTier(rankConverter(member.userRank));
                    return (
                        <div key={member.userId}>
                            <div className="member_nickname">{member.nickname}</div>
                            <div className="member_card">
                                <img src={memberTierImageSrc} alt="Tier" className="tier_image"/>
                            </div>
                            <div>오늘 공부한 시간: {formatTime(member.todayStudyTime)}</div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
};

export default GroupMember;
