import React, {useState, useEffect} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import './Mypage.css';
import picon from '/images/ico/picon.png';
import check from '/images/ico/check.png';
import Modal from './MypageModal';
import axiosInstance from "../../api/axiosInstance.js";
import MainP from '../mainprofilpage/Mainp';

function Mypage() {
    const navigate = useNavigate();
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const [nickname, setNickname] = useState(''); // 초기 닉네임 설정
    const [email, setEmail] = useState('');

    const token = localStorage.getItem("token");
    const user_id = localStorage.getItem("user_id");

    useEffect(() => {
        if (!token) {
            alert('세션이 만료되었습니다.');
            navigate("/");
        }else{
            fetchUserData();
        }
    }, [navigate]);

    const fetchUserData = async () => {
        try {
            const response = await axiosInstance.get(`/users/${user_id}/my_page`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            });
            const user = response.data;
            if(user) {
                setNickname(user.nickname);
                setEmail(user.email);
            }
        } catch(error){
                console.error('Error fetching user data:', error);
                alert('사용자 정보를 가져오는 데 문제가 발생했습니다.');
            }
    };

    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };
    const handleDeleteProfile = (password) => {
        axiosInstance
            .delete(`users/${user_id}/delete`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                data: {
                    password: password
                }
            })
            .then(() => {
                localStorage.clear();
                alert('그동안 이용해주셔서 감사합니다.');
                window.location.href = '/'; // 페이지 리디렉션
            })
            .catch((err) => alert(err.response.data.message));
    };
    const handleNicknameChange = (event) => {
        setNickname(event.target.value);
    };

    const handleNicknameSave = async () => {
        try {
            const response = await axiosInstance.patch(`/users/${user_id}/my_page/nickname`, {
                nickname: nickname
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            });
            setNickname(response.data.nickname);
            setIsEditing(false);
            alert('닉네임이 저장되었습니다.');
        } catch (error) {
            console.error('Error saving nickname:', error);
            alert('닉네임을 저장하는 데 문제가 발생했습니다.');
        }
    };
        // 닉네임 저장 로직 추가 (서버에 저장 등)


    return (
        <>
            <MainP/>
            <div className="container">
                <div className="m-menubox">
                    <div className="m-account">
                        내 정보 관리
                        <br/>
                    </div>
                    <div className="m-menu1">계정 관리</div>
                    <div className="m-menu2">나의 그룹</div>
                    <div className="m-menu3">나의 활동</div>
                </div>
                <div className="info">기본정보</div>
                <div className="account">계정 관리</div>
                <div className="accountbox">
                    <img className="picon" src={picon} alt="picon"/>
                    <img className="check" src={check} alt="check"/>
                    {isEditing ? ( // 수정 중일 때 입력 폼 표시
                        <input
                            type="text"
                            className="nickname-input"
                            value={nickname}
                            onChange={handleNicknameChange}
                        />
                    ) : (
                        <div className="name">{nickname}</div> // 수정 중이 아닐 때 닉네임 표시
                    )}
                    <div className="a-check">인증 완료</div>

                    <div className="email">{email}</div>
                    <div className="a-button"></div>
                    {isEditing ? ( // 수정 중일 때 저장 버튼 표시
                        <div className="a-buttontext" onClick={handleNicknameSave}>저장</div>
                    ) : (
                        <div className="a-buttontext" onClick={() => setIsEditing(true)}>수정</div> // 수정 버튼
                    )}
                    <div className="p-box"></div>
                    <div className="p-button"></div>
                    <Link to="/pwSearch" className="p-change">
                        비밀번호 변경
                    </Link>
                    <div className="update">비밀번호가 변경됩니다.</div>
                    <div className="myp">비밀번호</div>


                    <div className="deletebox">
                        <div className='a-delete'>계정 삭제</div>
                        <div className="deletebutton"></div>
                        <div className="deletem" onClick={openModal}>
                            삭제하기
                        </div>
                        <div className="deletemessage">
                            계정 삭제 시 모든 활동 기록이 삭제됩니다.
                        </div>
                    </div>
                </div>


                <Modal isOpen={isModalOpen} onClose={closeModal} handleDeleteProfile={handleDeleteProfile}>
                    <div>계정 삭제 시 프로필 및 모든 활동 기록이 삭제됩니다.</div>
                </Modal>
            </div>
        </>
    );
}

export default Mypage;
