import React, { useState } from 'react';
import './Tab.css';
import Listmodal from './Listmodal';

const Tab = () => {
  // 현재 선택된 탭의 인덱스를 관리하는 상태
  const [currentTab, clickTab] = useState(0);
  
  // 탭 메뉴 배열을 관리하는 상태
  const [menuArr, setMenuArr] = useState([
    { name: 'Tab1', content: 'Tab menu ONE' },
    { name: 'Tab2', content: 'Tab menu TWO' },
    { name: 'Tab3', content: 'Tab menu THREE' },
  ]);
  
  // 모달 창의 열림 상태를 관리하는 상태
  const [isModalOpen, setIsModalOpen] = useState(false);

  // 탭 선택 핸들러: 클릭한 탭의 인덱스를 currentTab 상태로 설정
  const selectMenuHandler = (index) => {
    clickTab(index);
  };

  // 모달 창 열기 핸들러: isModalOpen 상태를 true로 설정
  const openModalHandler = () => {
    setIsModalOpen(true);
  };

  // 모달 창 닫기 핸들러: isModalOpen 상태를 false로 설정
  const handleModalClose = () => {
    setIsModalOpen(false);
  };

  // 새로운 탭 추가 핸들러: 새로운 탭을 menuArr 상태에 추가
  const handleAddTab = (newTabName) => {
    const newTabContent = `Tab menu ${menuArr.length + 1}`;
    setMenuArr([...menuArr, { name: newTabName, content: newTabContent }]);
    setIsModalOpen(false); // 모달 창을 닫음
  };

  return (
    <div className='tabul'>
      <ul className="tab-menu">
        {/* menuArr 배열을 map 함수로 순회하여 탭 메뉴를 렌더링 */}
        {menuArr.map((el, index) => (
          <li
            key={index}
            className={index === currentTab ? 'submenu focused' : 'submenu'}
            onClick={() => selectMenuHandler(index)}
          >
            {el.name}
          </li>
        ))}
        {/* "추가하기" 버튼을 클릭하면 모달 창이 열림 */}
        <li className="submenubutton" onClick={openModalHandler}>
          추가하기
        </li>
      </ul>
      <div className="desc">
        <p>{menuArr[currentTab].content}</p>
      </div>
      {/* 모달 컴포넌트 렌더링: isOpen, onClose, onAddTab 속성을 전달 */}
      <Listmodal isOpen={isModalOpen} onClose={handleModalClose} onAddTab={handleAddTab} />
    </div>
  );
};

export default Tab;
