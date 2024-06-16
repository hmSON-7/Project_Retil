import React, { useState } from "react";
import axiosInstance from "../../api/axiosInstance.js";
import "./ListPodal.css";

function ListPodal({ isOpen, closeModal, onSave, categories }) {
    const token = localStorage.getItem("token");
    const [CategoryName, setCategoryName] = useState('');
    const [color, setColor] = useState('#000000'); // 초기값은 검은색으로 설정
    const [errorMessage, setErrorMessage] = useState(''); // 에러메세지 호출

    const handleInputChange = (e) => {
        setCategoryName(e.target.value);
    };

    const handleColorChange = (e) => {
        setColor(e.target.value);
    };

    const handleSave = async () => { // async 추가
        if (CategoryName.trim() === '') {
            setErrorMessage('카테고리명을 입력하세요.');
            return;
        }

        if (categories.some(category => category.name === CategoryName)) {
            setErrorMessage('이미 존재하는 카테고리명입니다.');
            return;
        }

        const user_id = localStorage.getItem("user_id");

        if (!user_id) {
            setErrorMessage('User ID is missing.');
            return;
        }

        try {
            // 서버에 POST 요청
            const response = await axiosInstance.post(`/til/${user_id}/subject`, {
                subjectName: CategoryName,
                color: color,
            },{
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            console.log(response.data);

            // onSave 함수를 호출하여 상위 컴포넌트에 저장된 데이터를 전달합니다.
            onSave(CategoryName, color);
            closeModal();
        } catch (error) {
            console.error("There was an error saving the category!", error);
            setErrorMessage('카테고리를 저장하는 중에 오류가 발생했습니다.');
        }
    };

    if (!isOpen) return null;

    return (
        <div className="ListModal-container">
            <div className="Modal-content" onClick={(e) => e.stopPropagation()}>
                <span className="Modalclose" onClick={closeModal}>&times;</span>
                <p>카테고리 생성</p>
                <input
                    type="text"
                    value={CategoryName}
                    onChange={handleInputChange}
                    placeholder="카테고리명을 입력하세요"
                />
                <input
                    type="color"
                    value={color}
                    onChange={handleColorChange}
                />
                {errorMessage && <p className="ErrorMessage">{errorMessage}</p>}
                <button className="Listsavebutton" onClick={handleSave}>저장</button>
                <button className="Listclosebutton" onClick={closeModal}>닫기</button>
            </div>
        </div>
    );
}

export default ListPodal;
