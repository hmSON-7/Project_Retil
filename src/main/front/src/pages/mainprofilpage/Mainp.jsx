import React from "react";
import { Link,useNavigate } from "react-router-dom";
import "./Mainp.css";

function Mainp() {
  const nav = useNavigate()
  const onClickHandle = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user_id");
    nav("/");
  }
  return (
    <div>
      <nav id="mainnav" className="mainnav">
        <ul className="nav__mainmenu">
          <li>
            <Link to="#retil" className="nav__menu--focused">
              RETIL
            </Link>
          </li>
          <li>
            <Link to="/list">List</Link>
          </li>
          <li>
            <Link to="#todaymoon">오늘의 문제</Link>
          </li>
          <li>
            <Link to="/tier">티어</Link>
          </li>
          <li>
            <Link to="/group">group</Link>
          </li>
        </ul>

        <Link to={"/Mypage"}>
          <button className="nav_mypagebutton">마이페이지</button>
        </Link>
        <Link to={"/memo"}>
          <button className="nav_writebutton">작성하기</button>
        </Link>
        <button onClick={onClickHandle}></button>
      </nav>
    </div>
  );
}

export default Mainp;
