import React from "react";
//import { Route,Routes } from "react-router-dom";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FaUser, FaLock } from "react-icons/fa";
import PwSearch from "./L-pwSearch";
import "./Login.css";
import axios from "axios";
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
    axios
      .post("http://localhost:8080/users/login", { email: id, password: pw })
      .then((response) => {
        const token = response.data; // 서버에서 받은 토큰
        console.log(id);
        console.log(pw);
        // 토큰을 로컬 스토리지에 저장
        localStorage.setItem("token", token);
        // 환영 메시지 설정
        setMessage("로그인 성공!");

        // 마이페이지로 이동
        navigate("/main");
      })
      .catch((error) => {
        const errorMsg = error.response ? error.response.data : "로그인 실패";
        setMessage(errorMsg);
        console.error(error);
      });
  };
  return (
    <div className="login">
      <Link to={"/"}>
        {" "}
        <h1>
          <img src="./images/ico/retil.png" />
        </h1>
      </Link>

      <form action="">
        <div className="input-box">
          <input
            type="text"
            name="id"
            value={id}
            onChange={(event) => {
              setId(event.target.value);
            }}
            placeholder="이메일을 입력하세요"
          />
          <FaUser className="icon" />
        </div>
        <div className="input-box">
          <input
            type="password"
            name="password"
            value={pw}
            onChange={(event) => {
              setPw(event.target.value);
            }}
            placeholder="비밀번호를 입력하세요"
          />
          <FaLock className="icon" />
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
          onClick={buttonOnClick}
          type="submit"
        >
          로그인
        </button>

        <div className="forgot-link">
          <div className="L-pwSearch">
            <Link to={"/pwSearch"}>비밀번호 찾기</Link>
          </div>
        </div>
      </form>
    </div>
  );
}

export default Login;
