import React, { useEffect, useRef } from "react";
import { Link } from "react-router-dom";
import G from "../Firstmain/G"; // G 컴포넌트 import
import "./Firstmain.css";
import Todayquestion from "./Todayquestion";
import FirstTier from './FirstTier'; // FirstTier 컴포넌트 import
import ListIntroduce from './ListIntroduce'; // ListIntroduce 컴포넌트 import
import "./FirstTier.css"; // FirstTier 컴포넌트의 CSS 파일 import
import "./ListIntroduce.css"; // ListIntroduce 컴포넌트의 CSS 파일 import
import galogo from "../../assets/galogo.png";

function Firstmain() {
  const marker = useRef(null);
  const sections = useRef([]);
  const menus = useRef([]);
  const targetRef1 = useRef(null); // IntersectionObserver 타겟 요소 1
  const targetRef2 = useRef(null); // IntersectionObserver 타겟 요소 2

  useEffect(() => {
    sections.current = document.querySelectorAll("section");
    menus.current = document.querySelectorAll(".fnav__menu > li > a");

    const handleScroll = () => {
      let current = "";

      sections.current.forEach((section) => {
        const sectionTop = window.scrollY + section.getBoundingClientRect().top;

        if (window.scrollY >= sectionTop - 60) { // Adjusted for header height
          current = section.getAttribute("id");
        }
      });

      menus.current.forEach((menu) => {
        menu.classList.remove("fnav__menu--focused");
        const href = menu.getAttribute("href").substring(1);
        if (href === current) {
          menu.classList.add("fnav__menu--focused");
          setMarker(menu);
        }
      });
    };

    const setMarker = (link) => {
      const { offsetLeft, offsetWidth } = link;
      marker.current.style.left = `${offsetLeft}px`;
      marker.current.style.width = `${offsetWidth}px`;
    };

    window.addEventListener("scroll", handleScroll);

    // IntersectionObserver 설정
    const observer1 = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          entry.target.classList.add("active");
        } else {
          entry.target.classList.remove("active");
        }
      });
    });

    const observer2 = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          setTimeout(() => {
            entry.target.classList.add("active");
          }, 1500); // 2초 후에 클래스 추가
        } else {
          entry.target.classList.remove("active");
        }
      });
    });

    if (targetRef1.current) {
      observer1.observe(targetRef1.current);
    }

    if (targetRef2.current) {
      observer2.observe(targetRef2.current);
    }

    return () => {
      window.removeEventListener("scroll", handleScroll);

      // IntersectionObserver 해제
      if (targetRef1.current) {
        observer1.unobserve(targetRef1.current);
      }

      if (targetRef2.current) {
        observer2.unobserve(targetRef2.current);
      }
    };
  }, []);

  return (
      <div>
        {/* 전체 메뉴바 박스 */}
        <nav id="nav" className="fnav">
          <div className="Flogo">
            <img className="Flogo-image" src={galogo} alt="로고" />
          </div>
          {/* 색션 */}
          <ul className="fnav__menu">
            <li>
              <a href="#retil" className="fnav__menu--focused">
                Introduce
              </a>
            </li>
            <li>
              <a href="#list">List</a>
            </li>
            <li>
              <a href="#todaymoon">TodayQuestion</a>
            </li>
            <li>
              <a href="#four">Tier</a>
            </li>
          </ul>
          {/* 로그인 로고 */}
          <Link to="/login">
            <button className="floginbutton">
              <img src="src/assets/loginIcon.png" alt="로그인" />
            </button>
          </Link>
          <div ref={marker} className="fmarker"></div>
        </nav>
        {/* 여기부터 색션 시작 */}
        {/* introduce */}
        <section id="retil">
          <span className="flist-retiltitle">RE:TIL</span>
          <span className="flist-retilintroduce">당신의 미래, 저희 retil이 도와드리겠습니다</span>
          <div className="main_gragh">
            <G />
          </div>
        </section>
        {/* list */}
        <section id="list">
          <div className="list-content">
            <div className="list-left">
            </div>
          </div>
          <ListIntroduce /> {/* ListIntroduce 컴포넌트 추가 */}
        </section>
        {/* todayQuestion */}
        <Todayquestion />
        {/* tier */}
        <section id="four">
          <div className="tier-content">
            <FirstTier />
          </div>
        </section>
      </div>
  );
}

export default Firstmain;
