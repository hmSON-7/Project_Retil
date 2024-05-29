import React from 'react';
import './SignupModal.css';

const Modal = ({ isOpen = false, children, onClose }) => {
  if (!isOpen) return null;

  return (
    <div className="signup-modal-overlay" onClick={onClose}>
      <div className="signup-modal" onClick={(e) => e.stopPropagation()}>
        <div className="signup-modal-content">
          <div className="signup-modal-text">{children}</div>
          <button className="signup-modal-close-button" onClick={onClose}>
            닫기
          </button>
        </div>
      </div>
    </div>
  );
};

export default Modal;
