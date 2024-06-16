import React from "react";
import "./MockList.css"; // 스타일 파일을 임포트하세요

const MockList = ({ list, onToggleBookmark }) => {
    return (
        <>
            {list.map((item) => (
                <div key={item.id} className="mock_list">
                    <input
                        type="checkbox"
                        checked={item.bookmark}
                        onChange={() => onToggleBookmark(item.id)}
                        id={`mock_check_${item.id}`}
                    />
                    <label htmlFor={`mock_check_${item.id}`}></label>
                    <div className="subject_name">{item.subjectName}</div>
                    <div className="memo_title">{item.title}</div>
                </div>
            ))}
        </>
    );
};

export default MockList;
