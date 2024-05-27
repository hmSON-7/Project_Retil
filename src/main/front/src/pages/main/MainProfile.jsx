import "./MainProfile.css";
import MainProgress from "./MainProgress";
const MainProfile = () => {
  return (
    <div className="profile">
      <span>오늘 공부한 시간 / 내 목표 시간</span>
      <div className="target_Time">
        <span>0시간 0분 / 0시간 0분</span>
      </div>
      <button className="setting_Button"></button>
      <div className="setting">
        <span>내 목표시간을 설정해 보세요.</span>
      </div>
      <div className="main_Progress">
        <MainProgress />
      </div>
      <div className="division_line"></div>
      <div className="division_line2"></div>
      <div></div>
    </div>
  );
};

export default MainProfile;
