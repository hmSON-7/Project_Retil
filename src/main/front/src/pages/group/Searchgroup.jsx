import React, { useState } from 'react';

const Searchgroup = (props) => {
  const [searchText, setSearchText] = useState(''); // 검색어 상태 관리

  // 검색어 입력 시 상태 업데이트
  const onChangeText = (e) => {
    setSearchText(e.target.value);
  };
  
  // 검색 버튼 클릭 시 검색 수행
  const onSearchClick = () => {
    props.search(searchText);
  };

  // 엔터 키 눌렀을 때 검색 수행
  const onKeyDownEnter = (e) => {
    if (e.key === 'Enter') {
      onSearchClick();
    }
  };

  return (
    <div>
      {/* 검색 입력창 */}
      <input
        type='text'
        placeholder='검색어를 입력하세요...'
        value={searchText}
        onChange={(e) => onChangeText(e)}
        onKeyDown={(e) => onKeyDownEnter(e)}
      />
      {/* 검색 버튼 */}
      <button onClick={() => onSearchClick()}>
        검색
      </button>
    </div>
  );
};

export default Searchgroup;
