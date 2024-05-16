import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Modal from './SignupModal';
import userIcon from '/images/ico/smile.png';
import emailIcon from '/images/ico/mail.png';
import passwordIcon from '/images/ico/password.png';
import './L-signup.css';
import axios from 'axios';

function Signup() {
  const [nickname, setNickname] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [passwordMatch, setPasswordMatch] = useState(false);
  const [agreeAll, setAgreeAll] = useState(false);
  const [agreeTerms, setAgreeTerms] = useState(false);
  const [agreePrivacy, setAgreePrivacy] = useState(false);
  const [emailError, setEmailError] = useState('');
  const [showTermsModal, setShowTermsModal] = useState(false);
  const [showPrivacyModal, setShowPrivacyModal] = useState(false);

  const navigate = useNavigate();

  const validateEmail = (email) => {
    const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return regex.test(email);
  };

  useEffect(() => {
    const isNicknameValid = nickname.trim() !== '';
    const isEmailValid = validateEmail(email);
    const isPasswordValid = password.trim() !== '';
    const isConfirmPasswordValid = confirmPassword.trim() !== '';
    const isPasswordMatch = password === confirmPassword;
    const isAllChecked = agreeTerms && agreePrivacy;

    setPasswordMatch(isPasswordMatch);
  }, [nickname, email, password, confirmPassword, agreeTerms, agreePrivacy]);

 const handleSubmit = async (e) => {
     e.preventDefault();
     if (nickname && email && validateEmail(email) && password && confirmPassword && passwordMatch && agreeTerms && agreePrivacy) {
       try {
         const response = await axios.post('http://localhost:8080/api/users/join', {
           nickname: nickname,
           email: email,
           password: password,
           confirmPassword: confirmPassword,
           // 필요한 다른 필드 추가
         });
         console.log('회원가입 요청을 보냅니다.', response.data);

         // 회원가입 성공 시 페이지 이동
         navigate('/L-signupC'); // props에서 history 객체 사용
       } catch (error) {
         console.error('회원가입 요청 중 에러 발생:', error);
         // 에러 처리 (예: 사용자에게 에러 메시지 표시)
       }
     } else {
       console.log('필수 정보를 모두 입력하세요.');
     }
   };


  const handleNicknameChange = (e) => {
    setNickname(e.target.value);
  };

  const handleEmailChange = (e) => {
    const value = e.target.value;
    setEmail(value);

    const isValidEmail = validateEmail(value);
    setEmailError(!isValidEmail && value.trim() !== '' ? '올바른 이메일 형식으로 입력해주세요.' : '');
  };

  const handlePasswordChange = (e) => {
    const value = e.target.value;
    setPassword(value);

    const passwordRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,20})/;
    setPasswordError(!passwordRegex.test(value) && value.trim() !== '' ? '영문자, 숫자, 특수문자를 포함하여 8~20자로 입력하세요.' : '');
  };

  const handleConfirmPasswordChange = (e) => {
    setConfirmPassword(e.target.value);
  };

  const handleAgreeAll = () => {
    const newValue = !agreeAll;
    setAgreeAll(newValue);
    setAgreeTerms(newValue);
    setAgreePrivacy(newValue);
  };

  const handleAgreeTerms = () => {
    setAgreeTerms((prev) => !prev);
    if (!agreeTerms) setAgreeAll(false);
  };

  const handleAgreePrivacy = () => {
    setAgreePrivacy((prev) => !prev);
    if (!agreePrivacy) setAgreeAll(false);
  };

  return (
    <div className="signup-container">
      <div className="signup-header">
        <Link to="/">
          <img className="logo-image" src="/images/ico/retil.png" alt="로고" />
        </Link>
      </div>
      <div className="signup-form">
        <form className="signup-details" onSubmit={handleSubmit}>
          <div className={`input-container ${emailError ? 'error' : ''}`} id="nickname-container">
            <label htmlFor="nickname" className="input-title">닉네임</label>
            <div className="input-with-icon">
              <img className="input-icon" src={userIcon} alt="사용자 아이콘" />
              <input
                type="text"
                id="nickname"
                placeholder="닉네임을 입력해 주세요"
                value={nickname}
                onChange={handleNicknameChange}
              />
            </div>
          </div>
          <div className={`input-container ${emailError ? 'error' : ''}`} id="email-container">
            <label htmlFor="email" className="input-title">이메일</label>
            <div className="input-with-icon">
              <img className="input-icon" src={emailIcon} alt="이메일 아이콘" />
              <input
                type="text"
                id="email"
                placeholder="예) retil@retil.co.kr"
                value={email}
                onChange={handleEmailChange}
              />
            </div>
            {emailError && <div className="input-Emailerror">{emailError}</div>}
          </div>
          <div className="input-container" id="password-container">
            <label htmlFor="password" className="input-title">비밀번호</label>
            <div className="input-with-icon">
              <img className="input-icon" src={passwordIcon} alt="비밀번호 아이콘" />
              <input
                type="password"
                id="password"
                placeholder="영문자, 숫자, 특수문자 포함 최소 8~20자"
                value={password}
                onChange={handlePasswordChange}
              />
            </div>
            {passwordError && <div className="input-error">{passwordError}</div>}
          </div>
          <div className="input-container" id="confirmPassword-container">
            <div className="input-with-icon">
              <img className="input-icon" src={passwordIcon} alt="비밀번호 아이콘" />
              <input
                type="password"
                id="confirmPassword"
                placeholder="비밀번호를 확인해주세요"
                value={confirmPassword}
                onChange={handleConfirmPasswordChange}
              />
            </div>
            {confirmPassword && (
              <div className={passwordMatch ? 'password-match' : 'password-mismatch'}>
                {passwordMatch ? '비밀번호가 일치합니다.' : '비밀번호가 일치하지 않습니다.'}
              </div>
            )}
          </div>
          <button
            className={`signup-button ${nickname && email && validateEmail(email) && password && confirmPassword && passwordMatch && agreeTerms && agreePrivacy ? 'active' : ''}`}
            type="submit"
            disabled={!nickname || !email || !validateEmail(email) || !password || !confirmPassword || !passwordMatch || !agreeTerms || !agreePrivacy || !!passwordError}
          >
            회원가입
          </button>

          <div className="terms-agreement">
            <div className="terms-checkbox">
              <input type="checkbox" id="agreeAll" checked={agreeAll} onChange={handleAgreeAll} />
              <label htmlFor="agreeAll">전체동의</label>
            </div>
            <div className="terms-checkbox">
              <input type="checkbox" id="agreeTerms" checked={agreeTerms} onChange={handleAgreeTerms} />
              <label htmlFor="agreeTerms">
                이용약관
                <Link to="#" onClick={() => setShowTermsModal(true)}>
                  [보기]
                </Link>
              </label>
            </div>
            <div className="terms-checkbox">
              <input type="checkbox" id="agreePrivacy" checked={agreePrivacy} onChange={handleAgreePrivacy} />
              <label htmlFor="agreePrivacy">
                개인정보 취급방침
                <Link to="#" onClick={() => setShowPrivacyModal(true)}>
                  [보기]
                </Link>
              </label>
            </div>
          </div>
        </form>
      </div>
      <Modal isOpen={showTermsModal} onClose={() => setShowTermsModal(false)}>
        <div className="modal-content">
          <p>이용약관 내용을 여기에 넣어주세요.</p>
        </div>
      </Modal>
      <Modal isOpen={showPrivacyModal} onClose={() => setShowPrivacyModal(false)}>
        <div className="modal-content">
          <p>
            리틸 개인정보 수집 이용 동의 주식회사 OO은 리틸 서비스를 이용하는 귀하의 개인정보를 아래와 같이 수집∙이용합니다. 자세한 내용은 리틸 개인정보 처리방침에서 확인하실 수 있습니다.
          </p>
          <ul>
            <li>수집하는 개인정보 항목 및 이용 목적</li>
            <li>개인정보 보유 및 이용기간</li>
            <li>개인정보 수집 및 이용 동의를 거부할 권리</li>
          </ul>
        </div>
      </Modal>
    </div>
  );
}

export default Signup;
