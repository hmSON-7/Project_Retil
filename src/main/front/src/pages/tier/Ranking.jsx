import "./Ranking.css";
import { useState } from "react";

function Ranking({ id, nickname, time, rank }) {
  const timeObject = new Date(time);
  const hours = timeObject.getHours();
  const minutes = timeObject.getMinutes();
  const seconds = timeObject.getSeconds();
  const formattedTime = `${hours}:${minutes}:${seconds}`;

  return (
    <div className="ranking">
      <div className="rank">{rank}</div>
      <div className="nickname">{nickname}</div>
      <div className="studyTime">{`${hours}:${minutes}:${seconds}`}</div>
    </div>
  );
}

export default Ranking;
