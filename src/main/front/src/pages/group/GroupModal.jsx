// GroupModal.jsx
import React from 'react';
import "./GroupModal.css"
const GroupModal = ({ show, onClose, onChange, onSubmit, group }) => {
    if (!show) return null;

    return (
        <div className="group_modal" onClick={onClose}>
            <div className="groupModal-content" onClick={(e) => e.stopPropagation()}>
                <span className="group_close" onClick={onClose}>&times;</span>
                <form onSubmit={onSubmit}>
                    <input
                        type="text"
                        name="groupName"
                        value={group.groupName}
                        onChange={onChange}
                        placeholder="그룹명"
                        required
                    />
                    <input
                        type="text"
                        name="introduce"
                        value={group.introduce}
                        onChange={onChange}
                        placeholder="한줄소개"
                        required
                    />
                    <input
                        type="number"
                        name="limit"
                        value={group.limit}
                        onChange={onChange}
                        placeholder="인원 제한"
                        required
                    />
                    <button type="submit">저장</button>
                </form>
            </div>
        </div>
    );
};

export default GroupModal;
