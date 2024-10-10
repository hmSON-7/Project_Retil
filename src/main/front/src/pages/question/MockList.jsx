import React, { useState } from "react";
import "./MockList.css"; // 스타일 파일을 임포트하세요

const MockList = ({ list }) => {
    const [hoveredId, setHoveredId] = useState(null);

    const handleMouseEnter = (id) => {
        setHoveredId(id);
    };

    const handleMouseLeave = () => {
        setHoveredId(null);
    };

    const removeDuplicates = (tilList) => {
        return [...new Set(tilList)];
    };

    const filteredList = list.filter(item => item.tilList.length > 0);

    return (
        <>
            {filteredList.map((item) => (
                <div
                    key={item.id}
                    className="mock_list"
                    onMouseEnter={() => handleMouseEnter(item.id)}
                    onMouseLeave={handleMouseLeave}
                >
                    <div className="subject_name">마우스를 올려주세요.</div>
                    <div className="memo_title">{item.saveTime}</div>
                    {hoveredId === item.id && item.tilList && (
                        <div className="title_list">
                            {removeDuplicates(item.tilList).map((til, index) => (
                                <div key={index} className="title_item">
                                    {til}
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            ))}
        </>
    );
};

export default MockList;
