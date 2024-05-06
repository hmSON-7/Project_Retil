import React from "react";
//import { Route,Routes } from "react-router-dom";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FaUser, FaLock } from "react-icons/fa";
import PwSearch from "./L-pwSearch";
import "./Login.css";

function Login() {
  const navigate = useNavigate();
  const [id, setId] = useState("");
  const [pw, setPw] = useState("");

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
    if (validation(id, pw)) {
      <Link to={"/Mainp"}></Link>;
    } else {
      alert("로그인에 실패하였습니다.");
      setId("");
      setPw("");
    }
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
        <Link to={"/"}>
          <button
            className={valid ? "active" : "inactive"}
            disabled={!valid}
            onClick={buttonOnClick}
            type="submit"
          >
            로그인
          </button>
        </Link>
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
