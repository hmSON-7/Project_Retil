import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import LogoutModal from "./LogoutModal";
import "./Mainp.css";
import mypage from "../../assets/mypage.png"
import logoutButton from "../../assets/logoutbutton.png"
import galogo from "../../assets/galogo.png"
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
          <div className="mlogo">
            <Link to="/main" className="mlogo-link">
              <img
                  className="mlogo-image"
                  src={galogo}
                  alt="main로고"
              />
            </Link>
          </div>

          <ul className="nav__mainmenu">
            <li>
              <Link
                  to="/Headlist"
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
                Group
              </Link>
            </li>
          </ul>
          {/**작성하기 버튼 */}
          <Link to={"/memo"}>
            <button className="nav_writebutton">작성하기</button>
          </Link>
          {/**마이페이지 아이콘 */}
          <Link to={"/Mypage"}>
            <button className="nav_mypagebutton">
              <img src={mypage} alt="my Page" />
            </button>
          </Link>
          {/**로그아웃 버튼 */}
          <button className="logout-icon">
            <img
                src={logoutButton}
                alt="Logout"
                onClick={openLogoutModal}
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
