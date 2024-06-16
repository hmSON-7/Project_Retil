import { useState } from "react";
import Modal from "react-modal";
import "./TimeSettingModal.css"; // CSS 파일 임포트

const TimeSettingModal = ({ isOpen, onRequestClose, onSave }) => {
  const [hour, setHour] = useState(0);
  const [minute, setMinute] = useState(0);
  const [error, setError] = useState("");

  const handleHourChange = (amount) => {
    const newHour = hour + amount;
    if (newHour >= 0 && newHour <= 24) {
      setHour(newHour);
      if (newHour === 24) {
        setMinute(0);
      }
    }
  };

  const handleMinuteChange = (amount) => {
    const newMinute = minute + amount;
    if (newMinute >= 0 && newMinute < 60 && hour < 24) {
      setMinute(newMinute);
    }
  };

  const handleSave = () => {
    if (hour === 24 && minute > 0) {
      setError("시간이 24일 때 분은 0이어야 합니다.");
      setMinute(0);
    } else {
      setError("");
      onSave(hour, minute);
      onRequestClose();
    }
  };

  return (
      <Modal
          isOpen={isOpen}
          onRequestClose={onRequestClose}
          contentLabel="시간 설정"
          ariaHideApp={false}
          className="time-setting-modal" // 모달 클래스 이름 추가
          overlayClassName="time-setting-modal-overlay" // 오버레이 클래스 이름 추가
      >
        <div className="time-setting-modal-content">
          <h2>목표 시간 설정</h2>
          {error && <p className="error-message">{error}</p>}
          <div className="time-setting-input">
            <label>
              시간:
              <button onClick={() => handleHourChange(-1)}>-</button>
              <input type="text" value={hour} readOnly />
              <button onClick={() => handleHourChange(1)}>+</button>
            </label>
          </div>
          <div className="time-setting-input">
            <label>
              분:
              <button onClick={() => handleMinuteChange(-1)}>-</button>
              <input type="text" value={minute} readOnly />
              <button onClick={() => handleMinuteChange(1)}>+</button>
            </label>
          </div>
          <button className="save-button" onClick={handleSave}>
            저장
          </button>
          <button className="cancel-button" onClick={onRequestClose}>
            취소
          </button>
        </div>
      </Modal>
  );
};

export default TimeSettingModal;
