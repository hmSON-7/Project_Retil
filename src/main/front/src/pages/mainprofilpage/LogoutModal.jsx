import React from "react";
import Modal from "react-modal";
import "./LogoutModal.css";

const LogoutModal = ({ isOpen, onRequestClose, onConfirm }) => {
  return (
    <>
      {isOpen && <div className="scrim"></div>}
      <Modal
        isOpen={isOpen}
        onRequestClose={onRequestClose}
        contentLabel="로그아웃 확인"
        className="logout-modal"
        overlayClassName="logout-modal-overlay"
        ariaHideApp={false}
      >
        <div className="logout-modal-content">
          <h2>로그아웃</h2>
          <span>정말 로그아웃 하시겠습니까?</span>
          <button onClick={onConfirm} className="Lconfirm-button">
            확인
          </button>
          <button onClick={onRequestClose} className="Lcancel-button">
            취소
          </button>
        </div>
      </Modal>
    </>
  );
};

export default LogoutModal;
