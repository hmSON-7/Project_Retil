import React, { useState } from 'react';
import Pagination from './Pagination';
import './Board.css'; // Board 컴포넌트의 CSS 파일 import
import Searchgroup  from "./Searchgroup";

const Group = () => {
  const totalItems = 50; // 전체 게시물 수
  const itemsPerPage = 10; // 페이지당 표시할 게시물 수

  const [currentPage, setCurrentPage] = useState(1);

  const posts = Array.from({ length: totalItems }, (_, index) => `Group 게시물 #${index + 1}`);

  const currentPosts = posts.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);

  return (
    <div className='gruopbox'>
      <div className='groupname'><h1>Group</h1></div>
      
      <div className='groupsearchbox'><Searchgroup /> {/* 검색창 추가 */}</div>
      <div className='grouppostbox'>
        {currentPosts.map((post, index) => (
          <div key={index} className="grouppost">{post}</div>
        ))}
      </div>
      <Pagination
        totalItems={totalItems}
        itemsPerPage={itemsPerPage}
        setCurrentPage={setCurrentPage}
        currentPage={currentPage}
      />
    </div>
  );
};

const GroupTwo = () => {
  const totalItems = 8; // 전체 게시물 수
  const itemsPerPage = 10; // 페이지당 표시할 게시물 수

  const [currentPage, setCurrentPage] = useState(1);

  const posts = Array.from({ length: totalItems }, (_, index) => `Group 게시물 #${index + 1}`);

  const currentPosts = posts.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);

  return (
    <div className='gruopbox'>
      <div className='groupname'><h1>Grouptwo</h1></div>
      
      <div className='groupsearchbox'><Searchgroup /> {/* 검색창 추가 */}</div>
      <div className='grouppostbox'>
        {currentPosts.map((post, index) => (
          <div key={index} className="grouppost">{post}</div>
        ))}
      </div>
      <Pagination
        totalItems={totalItems}
        itemsPerPage={itemsPerPage}
        setCurrentPage={setCurrentPage}
        currentPage={currentPage}
      />
    </div>
  );
};

const Board = () => {
  const [currentTab, setCurrentTab] = useState('Group'); // 초기 탭은 'Group'으로 설정

  // 클릭한 탭에 따라 currentTab 상태를 변경하는 함수
  const handleTabChange = (tabName) => {
    setCurrentTab(tabName);
  };

  return (
    <div>
      <div className='tabbox'>
     
        {/* Group 탭 */}
        <div
          className={currentTab === 'Group' ? 'tab active' : 'tab'}
          onClick={() => handleTabChange('Group')}
        >
          Group
        </div>
        {/* GroupTwo 탭 */}
        <div
          className={currentTab === 'GroupTwo' ? 'tab active' : 'tab'}
          onClick={() => handleTabChange('GroupTwo')}
        >
          GroupTwo
        </div>
      </div>
      

      {/* 현재 선택된 탭에 따라 해당 컴포넌트를 렌더링 */}
      {currentTab === 'Group' ? <Group /> : <GroupTwo />}
    </div>
  );
};

export default Board;
