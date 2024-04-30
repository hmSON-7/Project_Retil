import React, { useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import G from '../pages/G'; // G 컴포넌트 import
import "./Firstmain.css"; // CSS 파일 import

function Firstmain() {
  const marker = useRef(null);
  const sections = useRef([]);
  const menus = useRef([]);

  useEffect(() => {
    //스크롤 이벤트 처리 및 DOM요소에 대한 작업
    //querySectionAll은 css와 일치하는 문서 내의 모든 요소를 찾아 배열로 반환하는 메소드
    sections.current = document.querySelectorAll("section"); //페이지 색션에 대한
    menus.current = document.querySelectorAll(".nav__menu > li > a"); //메뉴의 링크 요소에 대한

    const handleScroll = () => { //handleScroll은 페이지 요소의 스크롤 이벤트를 처리하는 함수
      let current = "";

      sections.current.forEach(section => {
        const sectionTop = window.scrollY + section.getBoundingClientRect().top;

        if (window.scrollY >= sectionTop) {
          current = section.getAttribute("id");
        }
      });

      menus.current.forEach(menu => {
        menu.classList.remove("nav__menu--foused");
        const href = menu.getAttribute("href").substring(1);
        if (href === current) {
          menu.classList.add("nav__menu--foused");
          setMarker(menu);
        }
      });
    };

    //Dom요소의 위치와 너비를 설정하는 함수 : 마커가 나타나면 마커가 요소의 위치와 너비를 설정하기 위해 사용되는 함수
    const setMarker = link => {
      const { offsetLeft, offsetWidth } = link;
      marker.current.style.left = offsetLeft + "px";
      marker.current.style.width = offsetWidth + "px";
    };

    window.addEventListener("scroll", handleScroll);

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return (
    <div>
      <nav id="nav" className="nav">
        <ul className="nav__menu"> {/*상단 메뉴바 */}
          <li><a href="#retil" className="nav__menu--foused">RETIL</a></li>
          <li><a href="#list">List</a></li>
          <li><a href="#todaymoon">오늘의 문제</a></li>
          <li><a href="#four">FOUR</a></li>
          <div ref={marker} className="marker"></div> {/*메뉴바 언더 마커(색상: 파란색) */}
        </ul>

        <Link to={'/login'}>
          <button className="nav_loginbutton">로그인</button>
        </Link>
      </nav>
      {/* 메뉴바 페이지 색션*/}
      <section id="retil">
        retil introduce
        <G /> {/* G 컴포넌트 추가 */}
      </section>
      <section id="list">List</section>
      <section id="todaymoon">Todaymoon</section>
      <section id="four">four</section>
    </div>
  );
}

export default Firstmain;
