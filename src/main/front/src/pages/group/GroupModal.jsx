// GroupModal.jsx
import React from 'react';
import "./GroupModal.css"

const GroupModal = ({ show, onClose, onChange, onSubmit, group }) => {
    if (!show) return null;

    const handleLimitChange = (e) => {
        const { name, value } = e.target;
        const intValue = parseInt(value, 10);
        if (name === "limit") {
            if (intValue >= 1 && intValue <= 5) {
                onChange(e);
            }
        } else {
            onChange(e);
        }
    };

    return (
        <div className="group_modal" onClick={onClose}>
            <div className="groupModal-content" onClick={(e) => e.stopPropagation()}>
                <span className="group_close" onClick={onClose}>&times;</span>
                <div className='groupnamein'><span>그룹 만들기</span></div>

                <form onSubmit={onSubmit}>
                    <div className="groupName_modal">
                        <span>그룹명</span>
                        <input
                            type="text"
                            name="groupName" // 여기에 name 속성을 추가합니다.
                            value={group.groupName}
                            onChange={onChange}
                            required
                        />
                    </div>
                    <div className="introduce_modal">
                        <span> 자신의 그룹을 나타내는 문구를 작성해보세요</span>
                        <input
                            type="text"
                            name="introduce"
                            value={group.introduce}
                            onChange={onChange}
                            required
                        />
                    </div>

                    <div className="limit_modal">
                        <span>인원수</span>
                        <input
                            type="number"
                            name="limit"
                            value={group.limit}
                            onChange={handleLimitChange}
                            placeholder="인원 제한"
                            required
                            min="1"
                            max="5"
                        />
                    </div>
                    <button className='groupsave' type="submit">적용</button>
                </form>
            </div>
        </div>
    );
};

export default GroupModal;
