import "./Repw.css";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
function Repw() {
  const [password, setPassword] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [rePasswordMatch, setPasswordMatch] = useState(false);
  const [confirmPassword, setConfirmPassword] = useState("");

  useEffect(() => {
    const isPasswordMatch = password === confirmPassword;

    setPasswordMatch(isPasswordMatch);
  });

  //비밀번호 유효성 검사
  const handlePasswordChange = (e) => {
    const value = e.target.value;
    setPassword(value);
    const passwordRegex =
      /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,20})/;
    const isValidPassword = passwordRegex.test(value);

    if (!isValidPassword && value.trim() !== "") {
      setPasswordError(
        "영문자, 숫자, 특수문자를 포함하여 8~20자로 입력하세요."
      );
    } else {
      setPasswordError("");
    }
  };

  const handleConfirmPasswordChange = (e) => {
    const value = e.target.value;
    setConfirmPassword(value);
  };

  return (
    <div className="rePassword">
      <h1>비밀번호 재설정</h1>
      <h5>새롭게 설정할 비밀번호를 입력해 주세요.</h5>
      <form action="">
        <div className="reinput-box">
          <h7>새로운 비밀번호</h7>
          <input
            type="password"
            id="password"
            placeholder="영문자, 숫자, 특수문자 포함 최소 8~20자"
            value={password}
            onChange={handlePasswordChange}
          />
        </div>
        {passwordError && <div className="reinput-error">{passwordError}</div>}
        <div className="reinput-box">
          <h7>비밀번호 확인</h7>
          <input
            type="password"
            id="rePassword"
            value={confirmPassword}
            onChange={handleConfirmPasswordChange}
            placeholder="비밀번호를 확인해 주세요."
          />
        </div>
        {confirmPassword && (
          <div
            className={
              rePasswordMatch ? "repassword-match" : "repassword-mismatch"
            }
          >
            {rePasswordMatch
              ? "비밀번호가 일치합니다."
              : "비밀번호가 일치하지 않습니다."}
          </div>
        )}
        <Link to={"/login"}>
          <button type="submit">저장하기</button>
        </Link>
      </form>
    </div>
  );
}

export default Repw;
