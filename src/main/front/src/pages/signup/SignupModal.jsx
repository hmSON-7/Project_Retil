import React from 'react';

// Modal 컴포넌트 정의
const Modal = ({ isOpen = false, children, onClose }) => {
  // isOpen prop이 false인 경우, 모달을 렌더링하지 않음
  if (!isOpen) return null;

  // 모달 컴포넌트 반환
  return (
    <div className="modal-overlay" onClick={onClose}>
      {/* 모달 내부 컨테이너 */}
      <div className="modal" onClick={(e) => e.stopPropagation()}>
        {/* 모달 내용 */}
        <div className="modal-content">
          {/* 모달 안의 자식 요소(텍스트 등) */}
          <div className="modal-text">{children}</div>
          {/* 모달 안의 닫기 버튼 */}
          <button className="modal-close-button" onClick={onClose}>
            닫기
          </button>
        </div>
      </div>
    </div>
  );
};

// Modal 컴포넌트를 내보냄
export default Modal;
