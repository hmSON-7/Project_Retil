import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import './Mypage.css';
import picon from '/images/ico/picon.png';
import check from '/images/ico/check.png';


function Mypage() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [nickname, setNickname] = useState('김한배'); // 초기 닉네임 설정

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleDeleteProfile = () => {
    if (window.confirm('확인을 누르면 회원 정보가 삭제됩니다.')) {
      axios
        .delete(`${process.env.REACT_APP_PROXY_URL}/members/${parsed.memberId}`, {
          headers: {
            Authorization: 'Bearer ' + localStorage.getItem('ACCESS_TOKEN'),
          },
        })
        .then(() => {
          localStorage.clear();
          alert('그동안 이용해주셔서 감사합니다.');
          window.location.href = '/'; // 페이지 리디렉션
        })
        .catch((err) => alert(err.response.data.message));
    }
  };

  const handleNicknameChange = (event) => {
    setNickname(event.target.value);
  };

  const handleNicknameSave = () => {
    // 닉네임 저장 로직 추가 (서버에 저장 등)
    setIsEditing(false); // 수정 상태 종료
    // 여기서 서버에 닉네임을 저장하는 요청을 보내고, 성공 시에 메시지를 표시하거나 다른 작업을 수행할 수 있습니다.
    alert('닉네임이 저장되었습니다.');
  };

  return (
    <div className="container">
      <div className="p-box"></div>
      <div className="p-button"></div>
      <Link to="/pwSearch" className="p-change">
        비밀번호 변경
      </Link>
      <div className="update">비밀번호가 변경됩니다.</div>
      <div className="p">비밀번호</div>
      <div className="accountbox">
        <img className="picon" src={picon} alt="picon" />
        <div className="a-check">인증 완료</div>
        <img className="check" src={check} alt="check" />
        {isEditing ? ( // 수정 중일 때 입력 폼 표시
          <input
          type="text"
          value={nickname}
          onChange={handleNicknameChange}
          style={{
            color: '#000000', // 글자 색상
            fontSize: '28px', // 글자 크기
            fontWeight: 500, // 글자 두께
            width: '266px', // 너비
            height: '40px', // 높이
            position: 'absolute', // 위치 지정
            left: '35%', // 가로 위치 중앙 정렬
            top: '55%', // 세로 위치 중앙 정렬
            padding: '5px', // 내부 여백
            textAlign: 'center', // 텍스트 가운데 정렬
            border: '1px solid #d9d9d9', // 테두리 스타일
            borderRadius: '5px', // 테두리 둥글기
          }}
          />
        ) : (
          <div className="name">{nickname}</div> // 수정 중이 아닐 때 닉네임 표시
        )}
        <div className="email">Kimhanbae@naver.com</div>
        <div className="a-button"></div>
        {isEditing ? ( // 수정 중일 때 저장 버튼 표시
          <div className="a-buttontext" onClick={handleNicknameSave}>저장</div>
        ) : (
          <div className="a-buttontext" onClick={() => setIsEditing(true)}>수정</div> // 수정 버튼
        )}
        <div className="info">기본정보</div>
        <div className="deletebox">
          <div className='a-delete'>계정 삭제</div>
          <div className="deletebutton"></div>
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
        <div className="m-menu2">나의 그룹</div>
        <div className="m-menu3">나의 활동</div>
      </div>
      <div className="m-account">
        내 정보 관리
        <br />
        <div className="account">계정 관리</div>
      </div>
      
      <Modal isOpen={isModalOpen} onClose={closeModal} handleDeleteProfile={handleDeleteProfile}>
        <div>계정 삭제 시 프로필 및 모든 활동 기록이 삭제됩니다.</div>
      </Modal>
    </div>
  );
}

export default Mypage;
