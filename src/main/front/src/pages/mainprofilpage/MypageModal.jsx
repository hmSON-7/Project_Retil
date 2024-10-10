import React, { useState } from 'react';
import './MypageModal.css';

function MypageModal({ isOpen, onClose, handleDeleteProfile }) {
  const [password, setPassword] = useState('');

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleDeleteClick = () => {
    if (window.confirm('비밀번호를 확인했습니다. 계정을 삭제하시겠습니까?')) {
      handleDeleteProfile(password);
    }
  };

  if (!isOpen) return null;

  return (
    <div className="mypage-modal-overlay">
      <div className="mypage-modal">
        <h2>계정 삭제 확인</h2>
        <p>계정을 삭제하려면 비밀번호를 입력하세요.</p>
        <input
          type="password"
          placeholder="비밀번호"
          value={password}
          onChange={handlePasswordChange}
          className="password-input"
        />
        <div className="mypage-modal-button-group">
          <button className="mypage-modal-delete-button" onClick={handleDeleteClick}>삭제</button>
          <button className="mypage-modal-close-button" onClick={onClose}>취소</button>
        </div>
      </div>
    </div>
  );
}

export default MypageModal;
