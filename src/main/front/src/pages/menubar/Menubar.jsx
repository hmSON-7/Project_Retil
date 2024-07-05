import React, { useState } from 'react';
import './Menubar.css';
import TabList from "../menubar/TabList";

function Menubar() {
  const [tabs, setTabs] = useState([]);
  const [activeTab, setActiveTab] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [newTabName, setNewTabName] = useState('');
  const [selectedColor, setSelectedColor] = useState('#007bff');

  const addTab = () => {
    if (newTabName.trim() === '') return;
    const newTab = { name: newTabName.trim(), color: selectedColor, list: [] }; // 각 탭에 대한 목록 추가
    setTabs([...tabs, newTab]);
    setActiveTab(newTab);
    setNewTabName('');
    setShowModal(false);
  };

  const openModal = () => {
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
  };

  const handleTabClick = (tab) => {
    setActiveTab(tab);
  };

  const handleAddToList = (item) => {
    const updatedTabs = tabs.map(tab => {
      if (tab === activeTab) {
        return { ...tab, list: [...tab.list, item] }; // 활성 탭에만 항목을 추가
      }
      return tab;
    });
    setTabs(updatedTabs);
  };

  return (
    <div className="a">
      <div className="footermenubar">
        <div className="tab-menu">
          {tabs.map((tab, index) => (
            <button
              key={index}
              className={activeTab === tab ? 'active' : ''}
              style={{ borderColor: tab.color, backgroundColor: tab.color, color: '#fff' }}
              onClick={() => handleTabClick(tab)}
            >
              {tab.name}
            </button>
          ))}
          
          <button onClick={openModal}>추가하기</button>
        </div>
      </div>

      <ListModal
        showModal={showModal}
        closeModal={closeModal}
        newTabName={newTabName}
        setNewTabName={setNewTabName}
        addTab={addTab}
        selectedColor={selectedColor}
        setSelectedColor={setSelectedColor}
      />

      <div className="tab-content-container">
        {tabs.map((tab, index) => (
          <div key={index} className="tab-content" 
          style={{ display: activeTab === tab ? 'block' : 'none', 
          borderColor: tab.color, borderWidth: '5px' }}>
            {tab.list.map((item, itemIndex) => (
              <div key={itemIndex}>{item}</div> // 각 탭의 목록을 표시
            ))}
          </div>
        ))}
      </div>

      <TabList tabs={tabs} />
    </div>
  );
}

export default Menubar;
