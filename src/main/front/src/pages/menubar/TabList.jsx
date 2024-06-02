import React from 'react';
import "./TabList.css";

function TabList({ tabs }) {

  return (
    <div className="categorylistvbox">
      <div className="categoryname">
        <h2>카테고리별 리스트</h2>
      </div>
      <div className="categorylist">
        <ul>
          {tabs.map((tab, index) => (
            <li key={index}>
              <span>Name: {tab.name}</span>
              <span>Color: {tab.color}</span>
            </li>
        ))}
        </ul>
      </div>
    </div>
  );
}

export default TabList;// TabList를 명시적으로 exports
