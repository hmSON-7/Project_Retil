import "./MainProfile.css";
import { useState } from "react";
import MainProgress from "./MainProgress";
import TimeSettingModal from "./TimeSettingModal"; // 모달 컴포넌트 임포트

const MainProfile = () => {
  const [tier, setTier] = useState("");
  const [group, setGroup] = useState("");
  const [hour, setHour] = useState(19); //목표 시간
  const [minute, setMinute] = useState(0); // 목표 분
  const [isModalOpen, setIsModalOpen] = useState(false); // 모달 상태 관리
  const [actualStudyTime, setActualStudyTime] = useState(6); // 실제 공부한 시간 (테스트용)

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  const handleSave = (newHour, newMinute) => {
    setHour(newHour);
    setMinute(newMinute);
  };

  const calculateTotalMinutes = (hours, minutes) => {
    return hours * 60 + minutes;
  };

  const targetTimeInMinutes = calculateTotalMinutes(hour, minute);
  const actualStudyTimeInMinutes = calculateTotalMinutes(actualStudyTime, 0); // 분 단위는 0으로 가정

  const progressPercent =
    targetTimeInMinutes > 0
      ? (actualStudyTimeInMinutes / targetTimeInMinutes) * 100
      : 0;

  return (
    <div className="profile">
      <span>오늘 공부한 시간 / 내 목표 시간</span>
      <div className="target_Time">
        <span>
          {Math.floor(actualStudyTimeInMinutes / 60)}시간{" "}
          {actualStudyTimeInMinutes % 60}분 / {hour}시간 {minute}분
        </span>
      </div>
      <div className="setting_Button">
        <button onClick={openModal}></button>
      </div>
      <div className="setting">
        <span>내 목표시간을 설정해 보세요.</span>
      </div>
      <div className="main_Progress">
        <MainProgress percent={progressPercent} />
      </div>
      <div className="division_line"></div>
      <div className="profile_Tier">
        {" "}
        <img src="./images/ico/bronze.jpg" />
      </div>
      <div className="profile_group"></div>
      <div className="division_line2"></div>

      <TimeSettingModal
        isOpen={isModalOpen}
        onRequestClose={closeModal}
        onSave={handleSave}
      />
    </div>
  );
};

export default MainProfile;
