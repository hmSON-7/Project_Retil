import "../tier/Tier.css";
import Progressbar from "./Progressbar";
import Ranking from "./Ranking";
import { useState, useEffect } from "react";
//여기부터는 목업 데이터
const now = new Date();
const sixHoursLater = new Date(now.getTime() + 6 * 60 * 60 * 1000);
const fourHoursLater = new Date(now.getTime() + 4 * 60 * 60 * 1000);
const mData = [
  {
    id: 1,
    nickname: "호빈",
    time: sixHoursLater.getTime(),
    rank: 1,
  },

  {
    id: 2,
    nickname: "뽕따",
    time: fourHoursLater.getTime(),
    rank: 2,
  },
  {
    id: 3,
    nickname: "송이",
    time: fourHoursLater.getTime(),
    rank: 2,
  },
];
const myData = { id: 3, nickname: "나다", time: 6, rank: 3 };

//여기까지

// -------------------------
function Tier() {
  const [rank, setRank] = useState([]);
  const [search, setSearch] = useState("");

  const sortRank = () => {
    const sortedRank = [...mData].sort((a, b) => a.rank - b.rank);
    setRank(sortedRank);
  };

  useEffect(() => {
    sortRank(); // 컴포넌트가 마운트될 때 mData를 rank순으로 정렬하여 state에 저장
  }, []);

  const utcTime = new Date().getTime();
  const options = { year: "numeric", month: "numeric", day: "numeric" };
  const koreaTime = new Date(utcTime + 9 * 60 * 60 * 1000).toLocaleString(
    "ko-KR",
    options
  );

  const onChangeSearch = (e) => {
    setSearch(e.target.value);
  };

  const getFilter = () => {
    // 이 함수가 필터링 된 todos를 반환해 줘야하니까
    if (search === "") {
      return rank;
    }
    return rank.filter((rank) => {
      return rank.nickname.toLowerCase().includes(search.toLowerCase());
    }); // 화살표 함수에 중괄호 치면 return 꼭 해줘야함 이시끼야
  };

  const filteredRank = getFilter();

  return (
    <div className="tier">
      <MainP />

      <div className="my_Tier">
        <div className="myTier">🏅</div>
        <div className="myRank">{myData.nickname}</div>
        <div className="myTime">~~까지{myData.time}시간 남았습니다.</div>
        {/* 지금은 ~~시간이라고 했지만 나중에 디비에서 시간 데이터를 받으면 더 생각해 보아야할듯 ex) ~~분 ~~시간 남았습니다로 해야할듯 */}
        <Progressbar />
      </div>

      <p>순위</p>
      <div className="date">최근 업데이트 {koreaTime}</div>
      <input
        value={search}
        onChange={onChangeSearch}
        placeholder="검색어를 입력하세요"
      ></input>
      <div className="rank_wrapper">
        {filteredRank.map((rank) => {
          return <Ranking key={rank.id} {...rank} />;
        })}
      </div>
    </div>
  );
}

export default Tier;
