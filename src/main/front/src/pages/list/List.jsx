import React, { useState } from 'react';
import './List.css'; // CSS 파일 import

function List() {
  const [activeTab, setActiveTab] = useState('tabmenu01');

  const handleTabClick = (tabId) => {
    setActiveTab(tabId);
  };

  return (
    <div className="tabcontent">
      <input type="radio" name="tab" checked={activeTab === 'tabmenu01'} id="tabmenu01" onChange={() => handleTabClick('tabmenu01')} />
      <label htmlFor="tabmenu01">tab01</label>
      <input type="radio" name="tab" checked={activeTab === 'tabmenu02'} id="tabmenu02" onChange={() => handleTabClick('tabmenu02')} />
      <label htmlFor="tabmenu02">tab02</label>
      <input type="radio" name="tab" checked={activeTab === 'tabmenu03'} id="tabmenu03" onChange={() => handleTabClick('tabmenu03')} />
      <label htmlFor="tabmenu03">tab03</label>

      <div className={`content-box content01 ${activeTab === 'tabmenu01' ? 'active' : ''}`}>content01</div>
      <div className={`content-box content02 ${activeTab === 'tabmenu02' ? 'active' : ''}`}>content02</div>
      <div className={`content-box content03 ${activeTab === 'tabmenu03' ? 'active' : ''}`}>content03</div>
    </div>
  );
}

export default List;
