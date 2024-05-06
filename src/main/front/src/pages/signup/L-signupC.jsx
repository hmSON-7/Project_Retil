import React from 'react';
import { Link } from 'react-router-dom';
import './L-signupC.css';
import bookImage from '/images/ico/book.jpg';

function LsignupC() {
  return (
    <div className="completebox">
      <nav className="nav">
        <ul className="nav__menu">
          <li><a href="#retil" className="nav__menu--focused">RETIL</a></li>
          <li><a href="#list">List</a></li>
          <li><a href="#todaymoon">오늘의 문제</a></li>
          <li><a href="#four">FOUR</a></li>
        </ul>
        <div className="marker"></div>
        <Link to="/login">
          <button className="nav_loginbutton">로그인</button>
        </Link>
      </nav>
      <img className="book" src={bookImage} alt="Book" />
      <div className="c_m_box">
        <div className="cmessage">회원가입이 완료되었습니다.</div>
      </div>
      <div className="button"></div>
      <Link to="/login">
      <button className="buttontext">로그인</button>
      </Link>
    </div>
  );
}

export default LsignupC;
