import React, { useState, useEffect } from "react";
import "./Tier.css";
import MainP from "../mainprofilpage/Mainp";
import Progressbar from "./Progressbar";
import Ranking from "./Ranking";
import { getTier } from "../../../tier/get_tier.js";
import { rankConverter } from './rankConverter';
import axiosInstance from "../../api/axiosInstance.js";

const token = localStorage.getItem("token");
const user_id = localStorage.getItem("user_id");

const tierThresholds = [
  { tier: 'Bronze', time: 3600 },
  { tier: 'Silver', time: 36000 },
  { tier: 'Gold', time: 360000 },
  { tier: 'Platinum', time: 1800000 },
  { tier: 'Diamond', time: Infinity }
];

const getCurrentAndNextTier = (totalTime) => {
  for (let i = 0; i < tierThresholds.length; i++) {
    if (totalTime < tierThresholds[i].time) {
      return {
        currentTier: i === 0 ? 'unRanked' : tierThresholds[i - 1].tier,
        nextTier: tierThresholds[i].tier,
        timeToNextTier: tierThresholds[i].time - totalTime,
        nextTierTime: tierThresholds[i].time
      };
    }
  }
  return {
    currentTier: 'Diamond',
    nextTier: 'Maxed Out',
    timeToNextTier: 0,
    nextTierTime: Infinity
  };
};

function Tier() {
  const [myRank, setMyRank] = useState({});
  const [rank, setRank] = useState([]);
  const [search, setSearch] = useState("");
  const [isFlashing, setIsFlashing] = useState(false);

  const formatTime = (totalSeconds) => {
    const hours = Math.floor(totalSeconds / 3600);
    const minutes = Math.floor((totalSeconds % 3600) / 60);
    const seconds = totalSeconds % 60;
    return `${hours}:${minutes}:${seconds}`;
  };

  const formatTime1 = (totalSeconds) => {
    const hours = Math.floor(totalSeconds / 3600);
    const minutes = Math.floor((totalSeconds % 3600) / 60);
    return `${hours}:${minutes}`;
  };

  const sortRank = (rankData) => {
    const sortedRank = [...rankData].sort((a, b) => b.totalTime - a.totalTime);
    setRank(sortedRank);
  };

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const [myRankResponse, rankResponse] = await Promise.all([
          axiosInstance.get(`/users/rank/${user_id}`, {
            headers: {
              Authorization: `Bearer ${token}`,
            }
          }),
          axiosInstance.get('/users/rank', {
            headers: {
              Authorization: `Bearer ${token}`,
            }
          })
        ]);

        const myRankData = myRankResponse.data;
        const rankedDataWithId = rankResponse.data.map((item, index) => ({
          ...item,
          id: index + 1,
        }));

        sortRank(rankedDataWithId);

        // Find my rank based on nickname
        const myRankIndex = rankedDataWithId.findIndex(rank => rank.nickname === myRankData.nickname);
        if (myRankIndex !== -1) {
          setMyRank({ ...myRankData, rank: myRankIndex + 1 });
        } else {
          setMyRank({ ...myRankData, rank: 'N/A' });
        }
      } catch (error) {
        console.error('Error fetching user data:', error);
        alert('사용자 정보를 가져오는 데 문제가 발생했습니다.');
      }
    };

    fetchUserData();
  }, []);

  useEffect(() => {
    // 번쩍임을 트리거한 후 1초 뒤에 번쩍임 상태를 비활성화
    setIsFlashing(true);
    const timer = setTimeout(() => {
      setIsFlashing(false);
    }, 7000); // 애니메이션 시간과 일치해야 함

    return () => clearTimeout(timer);
  }, [myRank.totalTime]); // totalTime 변경 시마다 트리거

  const utcTime = new Date().getTime();
  const options = { year: "numeric", month: "numeric", day: "numeric" };
  const koreaTime = new Date(utcTime + 9 * 60 * 60 * 1000).toLocaleString("ko-KR", options);

  const { currentTier, nextTier, timeToNextTier, nextTierTime } = getCurrentAndNextTier(myRank.totalTime || 0);
  const formattedTime = formatTime(myRank.totalTime || 0);
  const formattedTime1 = formatTime1(myRank.totalTime || 0);
  const formattedTimeToNextTier = formatTime(timeToNextTier);
  const formattedNextTierTime = formatTime(nextTierTime);

  const progressPercentage = ((myRank.totalTime || 0) / nextTierTime) * 100;

  const onChangeSearch = (e) => {
    setSearch(e.target.value);
  };

  const getFilter = () => {
    if (search === "") {
      return rank;
    }
    return rank.filter((rank) => {
      return rank.nickname.toLowerCase().includes(search.toLowerCase());
    });
  };

  const tierImageSrc = getTier(rankConverter(myRank.userRank));
  const filteredRank = getFilter();

  return (
      <>
        <MainP />

        <div className="myTier">
          <div className="Tname">{myRank.nickname}님</div>
          <div className="oo">
            다음 레벨까지  <span className={isFlashing ? 'flash-text' : ''}>{formattedTimeToNextTier}</span>  남았습니다!
          </div>
          <div className="rank_time">{formattedTime} / {formattedNextTierTime}</div>
          <div className="my_rank">{myRank.rank === 'N/A' ? myRank.rank : `${myRank.rank}등`}</div>
          <img className="Tiericon" src={tierImageSrc} alt="Tier" />
          <div className="my_progress">
            <Progressbar progressPercentage={progressPercentage} />
          </div>
          <div className="tierdivision_line"></div>
          <div className="my_studyTime">총 공부량 (누적시간):</div>
          <div className="my_studyTimesecond">{formattedTime}</div>
        </div>

        <div className="aaaa">

          <span className="sun">순위</span>
          <span className="date">최근 업데이트 {koreaTime}</span>
        </div>
        <div className="bb">
          <input
              value={search}
              onChange={onChangeSearch}
              className="input_search"
              placeholder=" 검색어를 입력하세요"
          ></input>
        </div>

        <div className="rank_wrapper">
          {filteredRank.map((rank, index) => {
            return <Ranking key={rank.id} {...rank} rank={index + 1} />;
          })}
        </div>

      </>
  );
}

export default Tier;
