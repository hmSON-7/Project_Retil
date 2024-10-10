import "./Ranking.css";
import React from "react";
import { rankConverter } from './rankConverter';
import { getTier } from "../../../tier/get_tier.js";

function Ranking({ id, nickname, totalTime, userRank, rank }) {

    const formatTime = (totalSeconds) => {
        const hours = Math.floor(totalSeconds / 3600);
        const minutes = Math.floor((totalSeconds % 3600) / 60);
        const seconds = totalSeconds % 60;
        return `${hours}:${minutes}:${seconds}`;
    };

    const formattedTime = formatTime(totalTime);
    const tierImageSrc = getTier(rankConverter(userRank));

    return (
        <div className="ranking">
            <div className="rank">{rank}</div>
            <div className="nickname">{nickname}</div>
            <img className="user_tier" src={tierImageSrc} alt="Tier"/>
            <div className="studyTime">{formattedTime}</div>
        </div>
    );
}

export default Ranking;
