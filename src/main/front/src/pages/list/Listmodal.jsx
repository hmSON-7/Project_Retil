import React from 'react';
import "./Listmodal.css";

function ListModal({ showModal, closeModal, newTabName, setNewTabName, addTab, selectedColor, setSelectedColor }) {
  const handleColorChange = (e) => {
    setSelectedColor(e.target.value);
  };

  return (
    showModal && (
      <div className="modal">
        <div className="modal-content">
          <span className="close" onClick={closeModal}>&times;</span>
          <input
            type="text"
            value={newTabName}
            onChange={(e) => setNewTabName(e.target.value)}
            placeholder="카테고리 입력"
            className="category-input"
          />
          {/* 색상 피커 추가 */}
          <input
            type="color"
            value={selectedColor}
            onChange={handleColorChange}
            className="color-picker"
          />
          <button onClick={addTab} className="add-button">추가하기</button>
        </div>
      </div>
    )
  );
}

export default ListModal;
