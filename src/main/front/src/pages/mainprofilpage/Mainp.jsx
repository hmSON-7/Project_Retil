import React from "react";
import { Link } from "react-router-dom";
import "./Mainp.css";

function Mainp() {
  return (
    <div>
      <nav id="mainnav" className="mainnav">
        <ul className="nav__mainmenu">
          <li><Link to="#retil" className="nav__menu--focused">RETIL</Link></li>
          <li><Link to="#list">List</Link></li>
          <li><Link to="#todaymoon">오늘의 문제</Link></li>
          <li><Link to="#four">FOUR</Link></li>
        </ul>

        <Link to={'/mypage'}>
          <button className="nav_mypagebutton">마이페이지</button>
        </Link>
        <Link to={'/memo'}>
          <button className="nav_writebutton">작성하기</button>
        </Link>
      </nav>
    </div>
  );
}

export default Mainp;
