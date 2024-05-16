import "./L-pwSearch.css";
import React from "react";
//import { Route,Routes } from "react-router-dom";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

function PwSearch() {
  return (
    <div className="PwSearch">
      <h1>비밀번호 재설정</h1>
      <h5>비밀번호를 재설정 할 이메일을 입력해 주세요.</h5>
      <form action="">
        <div className="pwinput-box">
          <input
            type="text"
            name="id"
            // value={id}
            // onChange={(event) => {
            //   setId(event.target.value);
            // }}
            placeholder="이메일을 입력하세요."
          />
        </div>
        <Link to={"/repw"}>
          <button type="submit">비밀번호 재설정 메일 보내기 </button>
        </Link>
      </form>
    </div>
  );
}

export default PwSearch;
