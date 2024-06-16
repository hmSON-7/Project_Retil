import React, { useState, useEffect } from "react";
import axiosInstance from "../../api/axiosInstance.js";
import "./List.css";
import ListModal from "./ListPodal";
import Listcontentmodal from "./Listcontentmodal";
import Listprogress from "./Listprogress";

function List() {
  // 상태 정의
  const [isModalOpen, setIsModalOpen] = useState(false); // 모달 열림/닫힘 상태
  const [selectedCategory, setSelectedCategory] = useState(""); // 선택된 카테고리
  const [categories, setCategories] = useState([]); // 카테고리 목록
  const [searchTerm, setSearchTerm] = useState(""); // 검색어
  const [selectedColor, setSelectedColor] = useState(""); // 선택된 색상
  const [filteredCategories, setFilteredCategories] = useState([]); // 필터링된 카테고리 목록
  const [tilList, setTilList] = useState([]); // TIL 리스트
  const [filteredTilList, setFilteredTilList] = useState([]); // 필터링된 TIL 리스트
  const [isItemModalOpen, setIsItemModalOpen] = useState(false); // 아이템 모달 열림/닫힘 상태
  const [selectedItem, setSelectedItem] = useState(null); // 선택된 아이템

  // 로컬 스토리지에서 사용자 ID와 토큰 가져오기
  const user_id = localStorage.getItem("user_id");
  const token = localStorage.getItem("token");

  // 컴포넌트가 마운트될 때 카테고리와 TIL 리스트 가져오기
  useEffect(() => {
    fetchCategories();
    fetchAllTilList();
  }, []);

  // 카테고리 목록 가져오기
  const fetchCategories = async () => {
    try {
      const response = await axiosInstance.get(`/til/${user_id}/write`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      // 카테고리 데이터 매핑
      const mappedCategories = response.data.map((item, index) => ({
        id: index,
        subjectName: item.subjectName,
        color: item.color,
      }));

      setCategories(mappedCategories);
      setFilteredCategories(mappedCategories);
    } catch (error) {
      console.error("Error fetching categories", error);
    }
  };

  // 특정 카테고리의 TIL 리스트 가져오기
  const fetchTilList = async (subjectName) => {
    try {
      const response = await axiosInstance.get(`/til/${user_id}/subject/${subjectName}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      setTilList(response.data);
      setFilteredTilList(response.data);
    } catch (error) {
      console.error("Error fetching TIL list", error);
    }
  };

  // 전체 TIL 리스트 가져오기
  const fetchAllTilList = async () => {
    try {
      const response = await axiosInstance.get(`/til/${user_id}/`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      setTilList(response.data);
      setFilteredTilList(response.data);
    } catch (error) {
      console.error("Error fetching all TIL list", error);
    }
  };

  // 모달 열기
  const openModal = () => {
    setIsModalOpen(true);
  };

  // 모달 닫기
  const closeModal = () => {
    setIsModalOpen(false);
  };

  // 새로운 카테고리 저장
  const handleSaveCategory = (categoryName, color) => {
    const newCategory = {
      id: categories.length,
      subjectName: categoryName,
      color: color,
    };

    setCategories([...categories, newCategory]);
    setFilteredCategories([...categories, newCategory]);
    closeModal();
  };

  // 카테고리 클릭 핸들러
  const handleCategoryClick = (subjectName) => {
    setSelectedCategory(subjectName);
    fetchTilList(subjectName);
  };

  // 전체보기 버튼 클릭 핸들러
  const handleShowAll = () => {
    setSelectedCategory("");
    setFilteredCategories(categories);
    setSearchTerm("");
    fetchAllTilList();
  };

  // 검색 핸들러
  const handleSearch = () => {
    const filtered = tilList.filter((til) => {
      const lowerCaseTitle = (til.title || "").toLowerCase();
      return lowerCaseTitle.includes(searchTerm.toLowerCase());
    });
    setFilteredTilList(filtered);
  };

  // 아이템 모달 열기
  const openItemModal = (item) => {
    setSelectedItem({ ...item, user_id }); // user_id 추가
    setIsItemModalOpen(true);
  };

  // 아이템 모달 닫기
  const closeItemModal = () => {
    setSelectedItem(null);
    setIsItemModalOpen(false);
  };

  return (
    <div className="List-container">
      <div className="Forst-name">
        <h1>리스트</h1>
      </div>
      <div className="List-header">
        <button className="Tab-Totalbutton" onClick={handleShowAll}>
          전체보기
        </button>
        {categories.map((category, index) => (
          <button
            className="AddSubjectbutton"
            key={index}
            onClick={() => handleCategoryClick(category.subjectName)}
            style={{
              border: `2px solid ${category.color || "transparent"}`,
              borderRadius: "5px",
              marginBottom: "10px",
            }}
          >
            {category.subjectName}
          </button>
        ))}

        <button onClick={openModal} className="Tab-addbutton">
          +
        </button>
        <ListModal
          isOpen={isModalOpen}
          closeModal={closeModal}
          onSave={handleSaveCategory}
          categories={categories}
          selectedColor={selectedColor}
          setSelectedColor={setSelectedColor}
        />
        <input
          type="text"
          placeholder=" 제목을 검색하세요"
          className="ListSerachinput"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button className="List-okSerchbutton" onClick={handleSearch}>
          확인
        </button>
      </div>
      <div className="List-moklocklist">
        <span className="Listfavorite-icon">즐겨찾기</span>
        <span className="Listitem-title">제목</span>
        <span className="Listitem-date">작성날짜</span>
      </div>

      <div className="List-middle">
        {filteredTilList.map((til, index) => (
          // 필터링된 TIL 리스트 렌더링
          <React.Fragment key={index}>
            <div>
              <input
                className="listcheckbox"
                type="checkbox"
                checked={til.bookmark || false}
                readOnly
              />
            </div>
            <div
              className="categorcontenet-box"
              style={{
                border: `2px solid ${til.color || "transparent"}`,
                borderRadius: "5px",
                marginBottom: "10px",
              }}
              onClick={() => openItemModal(til)} // 아이템 클릭 시 모달 열기
            >
              <input
                className="listtitle-box"
                type="text"
                value={til.title || ""}
                readOnly
              />
              <input
                className="listsavetime-box"
                type="text"
                value={(til.saveTime || "").split("T")[0]} // 날짜 부분만 추출하여 표시
                readOnly
              />
            </div>

            <div className="addprogress">
            <Listprogress til={7} /> {/* 3일일 경우 */}
            </div>

            {index !== filteredTilList.length - 1 && (
              <hr className="category-separator" />
            )}
          </React.Fragment>
        ))}
      </div>
      <Listcontentmodal
        isOpen={isItemModalOpen}
        onClose={closeItemModal}
        item={selectedItem}
      />
    </div>
  );
}

export default List;
