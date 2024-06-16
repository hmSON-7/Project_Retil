import React, { useEffect, useRef } from "react";
import { Link } from "react-router-dom";
import G from "../pages/G"; // G 컴포넌트 import
import "./Firstmain.css"; // CSS 파일 import

function Firstmain() {
  const marker = useRef(null);
  const sections = useRef([]);
  const menus = useRef([]);

  useEffect(() => {
    sections.current = document.querySelectorAll("section");
    menus.current = document.querySelectorAll(".nav__menu > li > a");

    const handleScroll = () => {
      let current = "";

      sections.current.forEach((section) => {
        const sectionTop = window.scrollY + section.getBoundingClientRect().top;

        if (window.scrollY >= sectionTop - 60) { // Adjusted for header height
          current = section.getAttribute("id");
        }
      });

      menus.current.forEach((menu) => {
        menu.classList.remove("nav__menu--focused");
        const href = menu.getAttribute("href").substring(1);
        if (href === current) {
          menu.classList.add("nav__menu--focused");
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

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return (
    <div>
      <nav id="nav" className="nav">
      <div className="Flogo">
        <img className="Flogo-image" src="/images/ico/galogo.png" alt="로고" />
      </div>
        <ul className="nav__menu">
          <li>
            <a href="#retil" className="nav__menu--focused">
              RETIL
            </a>
          </li>
          <li>
            <a href="#list">List</a>
          </li>
          <li>
            <a href="#todaymoon">오늘의 문제</a>
          </li>
          <li>
            <a href="#four">티어</a>
          </li>
          <div ref={marker} className="marker"></div>
        </ul>
        <Link to="/login">
          <button className="nav_loginbutton">로그인</button>
        </Link>
      </nav>
      <section id="retil">
        <span>retil introduce</span>
        <div className="main_gragh">
          <G />
        </div>
      </section>
      <section id="list">List</section>
      <section id="todaymoon">Todaymoon</section>
      <section id="four">티어</section>
    </div>
  );
}

export default Firstmain;
