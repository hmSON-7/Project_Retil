import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './Mypage.css';
import picon from '/images/ico/picon.png';
import check from '/images/ico/check.png';
import Modal from '../../pages/signup/SignupModal'; // 모달 컴포넌트 경로 수정

function Mypage() {
  const [isModalOpen, setIsModalOpen] = useState(false); // 모달 열림/닫힘 상태를 관리

  // 모달 열기 핸들러
  const openModal = () => {
    setIsModalOpen(true);
  };

  // 모달 닫기 핸들러
  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className="container">
      <div className="p-box"></div>
      <div className="p-button"></div>
      <Link to="/pwSearch" className="p-change">
        비밀번호 변경
      </Link>
      <div className="update">최근 업데이트: 2024-03-08</div>
      <div className="p">비밀번호</div>
      <div className="accountbox">
        <img className="picon" src={picon} alt="picon" />
        <div className="a-check">인증 완료</div>
        <img className="check" src={check} alt="check" />
        <div className="name">김한배</div>
        <div className="email">Kimhanbae@naver.com</div>
        <div className="a-button"></div>
        <div className="a-buttontext">수정</div>
        <div className="info">기본정보</div>
        <div className="deletebox">
          <div className='a-delete'>계정 삭제</div>
          {/* 삭제하기 버튼에 클릭 이벤트 핸들러 추가 */}
          <div className="deletebutton" onClick={openModal}></div>
          <div className="deletem" onClick={openModal}>
            삭제하기
          </div>
          <div className="deletemessage">
            계정 삭제 시 프로필 및 모든 활동 기록이 삭제됩니다.
          </div>
        </div>
      </div>
      <div className="m-menubox">
        <div className="m-menu1">계정 관리</div>
        <div className="m-menu2">오늘의 문제 기록</div>
        <div className="m-menu3">나의 그룹</div>
        <div className="m-menu4">나의 활동</div>
      </div>
      <div className="m-account">
        내 정보 관리
        <br />
        <div className="account">계정 관리</div>
      </div>

      {/* 모달 컴포넌트 조건부 렌더링 */}
      <Modal isOpen={isModalOpen} onClose={closeModal}>
        {/* 모달 내용 */}
        <div>
          계정 삭제 시 프로필 및 모든 활동 기록이 삭제됩니다.
        </div>
      </Modal>
    </div>
  );
}

export default Mypage;
