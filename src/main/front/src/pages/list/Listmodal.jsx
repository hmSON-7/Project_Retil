// Listmodal.jsx
import React, { useState } from 'react';
import './Listmodal.css';

const Listmodal = ({ isOpen, onClose, onAddTab }) => {
  // 새로운 탭의 이름을 관리하는 상태
  const [newTabName, setNewTabName] = useState('');

  // 입력 필드의 값이 변경될 때 호출되는 함수
  const handleInputChange = (e) => {
    setNewTabName(e.target.value);
  };

  // "확인하기" 버튼을 클릭할 때 호출되는 함수
  const handleAddTab = () => {
    // 입력된 탭 이름이 유효한 경우에만 onAddTab 함수를 호출
    if (newTabName.trim()) {
      onAddTab(newTabName);
      setNewTabName(''); // 입력 필드를 초기화
    }
  };

  // 모달이 열려 있지 않으면 아무것도 렌더링하지 않음
  if (!isOpen) return null;

  // 모달이 열려 있는 경우 렌더링되는 JSX
  return (
    <div className="modal">
      <div className="modal-content">
        <h2>새 탭 추가</h2>
        <input
          type="text"
          value={newTabName}
          onChange={handleInputChange}
          placeholder="탭 제목 입력"
        />
        <button onClick={handleAddTab}>확인하기</button>
        <button onClick={onClose}>닫기</button>
      </div>
    </div>
  );
};

export default Listmodal;
