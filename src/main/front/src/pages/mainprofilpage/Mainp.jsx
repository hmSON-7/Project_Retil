import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import LogoutModal from "./LogoutModal";

import "./Mainp.css";

function Mainp() {
  const [activeLink, setActiveLink] = useState("RETIL");
  const [isLogoutModalOpen, setIsLogoutModalOpen] = useState(false);
  const nav = useNavigate();

  const onClickHandle = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user_id");
    nav("/");
  };

  const handleLinkClick = (linkName) => {
    setActiveLink(linkName);
  };

  const openLogoutModal = () => {
    setIsLogoutModalOpen(true);
  };

  const closeLogoutModal = () => {
    setIsLogoutModalOpen(false);
  };

  const handleLogoutConfirm = () => {
    onClickHandle();
    closeLogoutModal();
  };

  return (
    <div>
      <nav id="mainnav" className="mainnav">
        <ul className="nav__mainmenu">
          <div className="mlogo">
            <Link to="/main" className="mlogo-link">
              <img
                className="mlogo-image"
                src="images/ico/galogo.png"
                alt="로고"
              />
            </Link>
          </div>
          <li>
            <Link
              to="/main"
              className={activeLink === "RETIL" ? "nav__menu--focused" : ""}
              onClick={() => handleLinkClick("RETIL")}
            >
              RETIL
            </Link>
          </li>
          <li>
            <Link
              to="/list"
              className={activeLink === "List" ? "nav__menu--focused" : ""}
              onClick={() => handleLinkClick("List")}
            >
              List
            </Link>
          </li>
          <li>
            <Link
              to="/question"
              className={
                activeLink === "오늘의 문제" ? "nav__menu--focused" : ""
              }
              onClick={() => handleLinkClick("오늘의 문제")}
            >
              오늘의 문제
            </Link>
          </li>
          <li>
            <Link
              to="/tier"
              className={activeLink === "티어" ? "nav__menu--focused" : ""}
              onClick={() => handleLinkClick("티어")}
            >
              티어
            </Link>
          </li>
          <li>
            <Link
              to="/group"
              className={activeLink === "group" ? "nav__menu--focused" : ""}
              onClick={() => handleLinkClick("group")}
            >
              group
            </Link>
          </li>
        </ul>

        <Link to={"/Mypage"}>
          <button className="nav_mypagebutton">
            <img src="src/assets/menubarIcon.png" alt="my Page" />
          </button>
        </Link>
        <Link to={"/memo"}>
          <button className="nav_writebutton">작성하기</button>
        </Link>
        <button className="logoutbutton" onClick={openLogoutModal}>
          로그아웃
          <img
            src="src/assets/logoutbutton.png"
            alt="Logout"
            className="logout-icon"
          />
        </button>
      </nav>

      <LogoutModal
        isOpen={isLogoutModalOpen}
        onRequestClose={closeLogoutModal}
        onConfirm={handleLogoutConfirm}
      />
    </div>
  );
}

export default Mainp;
