import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import emailIcon from '/images/ico/mail.png';
import passwordIcon from '/images/ico/password.png';
import "./Login.css";
import axiosInstance from "../../api/axiosInstance.js";

function Login() {
    const navigate = useNavigate();
    const [id, setId] = useState("");
    const [pw, setPw] = useState("");
    const [message, setMessage] = useState("");

    const validation = (idText, pwText) => {
        if (!idText.includes("@")) {
            return false;
        }
        if (pwText.length < 8) {
            return false;
        }
        return true;
    };

    const valid = validation(id, pw);

    const buttonOnClick = () => {
        axiosInstance
            .post(`/users/login`, { email: id, password: pw })
            .then((response) => {
                const { token, id } = response.data; // 서버에서 받은 토큰
                // 토큰을 로컬 스토리지에 저장
                localStorage.setItem("token", token);
                localStorage.setItem("user_id", id);
                navigate("/main");
            })
            .catch((error) => {
                const errorMsg = error.response ? error.response.data : "로그인 실패";
                setMessage(errorMsg);
                alert("로그인 실패: " + errorMsg); // alert로 오류 메시지 표시
                setId(""); // 로그인 실패 시 이메일 입력란을 비웁니다.
                setPw(""); // 로그인 실패 시 비밀번호 입력란을 비웁니다.
                console.error(error);
            });
    };

    const handleFormSubmit = (event) => {
        event.preventDefault(); // 폼의 기본 동작을 막음 (새로고침 방지)
        if (valid) {
            buttonOnClick(); // 로그인 버튼 클릭 함수 호출
        }
    };

    return (
        <div className="login">
            <Link to={"/"}>
                <h1>
                    <img src="./images/ico/retil.png" alt="Retil Logo" />
                </h1>
            </Link>

            <form onSubmit={handleFormSubmit}>
                <div className="input-box">
                    <img className="email_icon" src={emailIcon} alt="사용자 아이콘" />
                    <input
                        type="text"
                        name="id"
                        value={id}
                        onChange={(event) => {
                            setId(event.target.value);
                        }}
                        placeholder="이메일을 입력하세요"
                    />
                </div>

                <div className="input-box">
                    <img className="email_icon" src={passwordIcon} alt="사용자 아이콘" />
                    <input
                        type="password"
                        name="password"
                        value={pw}
                        onChange={(event) => {
                            setPw(event.target.value);
                        }}
                        placeholder="비밀번호를 입력하세요"
                    />
                </div>

                <div className="remember-signup">
                    <label>
                        <input type="checkbox" />
                        로그인 유지
                    </label>
                    <div className="L-signup">
                        <Link to={"/signup"}>회원가입</Link>
                    </div>
                </div>

                <button
                    className={valid ? "active" : "inactive"}
                    disabled={!valid}
                    type="submit"
                >
                    로그인
                </button>
            </form>

            <div className="forgot-link">
                <div className="L-pwSearch">
                    <Link to={"/pwSearch"}>비밀번호 찾기</Link>
                </div>
            </div>

            {message && <p className="message">{message}</p>}
        </div>
    );
}

export default Login;
