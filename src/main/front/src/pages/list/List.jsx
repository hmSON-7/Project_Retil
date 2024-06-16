import React, { useState, useEffect } from "react";
import axiosInstance from "../../api/axiosInstance.js";
import "./List.css";
import ListModal from "./ListPodal";

function List() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState("");
  const [categories, setCategories] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedColor, setSelectedColor] = useState("");
  const [filteredCategories, setFilteredCategories] = useState([]);

  const user_id = localStorage.getItem("user_id");
  const token = localStorage.getItem("token");

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      const response = await axiosInstance.get(`/til/${user_id}/write`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log("Response Data:", response.data);

      // 데이터를 맵핑하여 상태 설정
      const mappedCategories = response.data.map((item, index) => ({
        id: index,  // 인덱스를 ID로 사용
        subjectName: item.subjectName,
        color: item.color,
      }));

      console.log("Mapped Categories:", mappedCategories);

      setCategories(mappedCategories);
      setFilteredCategories(mappedCategories);
    } catch (error) {
      console.error("Error fetching categories", error);
    }
  };

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleSaveCategory = (categoryName, color) => {
    const newCategory = {
      id: categories.length,  // 새로운 ID 설정
      subjectName: categoryName,
      color: color,
    };

    setCategories([...categories, newCategory]);
    setFilteredCategories([...categories, newCategory]);
    closeModal();
  };
  const handleCategoryClick = (subjectName) => {
    setSelectedCategory(subjectName);
    const filtered = subjectName ? categories.filter(category => category.subjectName === subjectName) : categories;
    setFilteredCategories(filtered);
  };

  const handleShowAll = () => {
    setSelectedCategory("");
    setFilteredCategories(categories);
    setSearchTerm("");
  };

  const handleSearch = () => {
    const filtered = categories.filter(category => {
      const lowerCaseSubjectName = (category.subjectName || "").toLowerCase();
      return lowerCaseSubjectName.includes(searchTerm.toLowerCase());
    });
    setFilteredCategories(filtered);
  };

  return (
      <div className="List-container">
        <div className="Forst-name">
          <h1> 리스트 </h1>
        </div>
        <div className="List-header">
          <button className="AddSubjectbutton" onClick={handleShowAll}>
            전체보기
          </button>
          {categories.map((category, index) => (
              <button
                  className="AddSubjectbutton"
                  key={index}
                  onClick={() => handleCategoryClick(category.subjectName)}
                  style={{
                    borderColor: category.color || 'transparent',
                    backgroundColor: category.color || 'transparent',
                    color: category.color ? '#fff' : '#000' // 색상이 있는 경우 글자색을 흰색으로 설정
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
              placeholder="검색"
              className="ListSerachinput"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
          />
          <button className="List-okSerchbutton" onClick={handleSearch}>확인</button>
        </div>
        <div className="List-moklocklist">
          <span className="Listfavorite-icon">즐겨찾기</span>
          <span className="Listitem-title">제목</span>
          <span className="Listitem-date">작성날짜</span>
        </div>
        <div className="List-middle">
          {filteredCategories.map((category, index) => (
              <React.Fragment key={index}>
                <div
                    className="categorcontenet-box"
                    style={{
                      border: `2px solid ${category.color || 'transparent'}`,
                      borderRadius: "5px",
                      marginBottom: "10px"
                    }}
                >
                  <input
                      type="checkbox"
                      checked={category.bookmark || false}
                      readOnly
                  />
                  <input
                      type="text"
                      value={category.subjectName || ""}
                      readOnly
                  />
                  <input
                      type="text"
                      value={category.title || ""}
                      readOnly
                  />
                  <input
                      type="text"
                      value={category.saveTime || ""}
                      readOnly
                  />
                </div>
                {index !== filteredCategories.length - 1 && (
                    <hr className="category-separator" />
                )}
              </React.Fragment>
          ))}
        </div>
      </div>
  );
}

export default List;
