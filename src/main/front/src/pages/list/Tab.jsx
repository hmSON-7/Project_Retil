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
  const [showMoreTabs, setShowMoreTabs] = useState(false);

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
   // "더 보기" 버튼 클릭 핸들러: 더 많은 탭을 보이도록 설정하거나 다시 숨김
  const handleShowMoreTabs = () => {
    setShowMoreTabs(!showMoreTabs);
  };


  return (
    <div className='tabul'>
      {/* 탭 목록을 표시합니다. */}
      <ul className="tab-menu">
        {/* 보여질 탭은 3개까지만, showMoreTabs가 true인 경우 모두 표시합니다. */}
        {menuArr.slice(0, showMoreTabs ? menuArr.length : 3).map((el, index) => (
          <div
            key={index}
            className={index === currentTab ? 'submenu focused' : 'submenu'}
            onClick={() => selectMenuHandler(index)}
          >
            {el.name}
          </div>
        ))}
        {/* 탭이 3개 이상인 경우 "더 보기" 버튼을 표시합니다. */}
        {menuArr.length > 3 && (
          <div className="submenubutton" onClick={handleShowMoreTabs}>
            {showMoreTabs ? '접기' : '더 보기'}
          </div>
        )}
        {/* "추가하기" 버튼을 표시합니다. */}
        <div className="submenubutton" onClick={openModalHandler}>
          추가하기
        </div>
      </ul>
      {/* 현재 선택된 탭의 내용을 표시합니다. */}
      <div className="desc">
        <p>{menuArr[currentTab].content}</p>
      </div>
      {/* 모달 컴포넌트를 렌더링합니다. isOpen, onClose, onAddTab 속성을 전달합니다. */}
      <Listmodal isOpen={isModalOpen} onClose={handleModalClose} onAddTab={handleAddTab} />
    </div>
  );
};

export default Tab;
