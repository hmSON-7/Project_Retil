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
  const [visibleCategories, setVisibleCategories] = useState(3); // 처음에는 3개의 카테고리만 보이도록 설정
  const [showMore, setShowMore] = useState(false); // 더보기/접기 버튼 상태
  const [headerHeight, setHeaderHeight] = useState(70); // 초기 div 높이 설정

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
      const response = await axiosInstance.get(
          `/til/${user_id}/subject/${subjectName}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
      );

      const processedTilList = response.data.map(processTilItem);
      setTilList(processedTilList);
      setFilteredTilList(processedTilList);
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

      const processedTilList = response.data.map(processTilItem);
      setTilList(processedTilList);
      setFilteredTilList(processedTilList);
    } catch (error) {
      console.error("Error fetching all TIL list", error);
    }
  };

  // TIL 아이템을 처리하여 가장 큰 날짜 값을 찾는 함수
  const processTilItem = (til) => {
    const dayValues = {
      aday: til.aday ? 1 : 0,
      threeDays: til.threeDays ? 3 : 0,
      aweek: til.aweek ? 7 : 0,
      fifteenDays: til.fifteenDays ? 15 : 0,
      amonth: til.amonth ? 30 : 0,
      twoMonth: til.twoMonth ? 60 : 0,
      sixMonth: til.sixMonth ? 180 : 0,
    };

    const maxDayValue = Math.max(
        dayValues.aday,
        dayValues.threeDays,
        dayValues.aweek,
        dayValues.fifteenDays,
        dayValues.amonth,
        dayValues.twoMonth,
        dayValues.sixMonth
    );
    return { ...til, maxDayValue };
  };
  // 모달 열기
  const openModal = () => {
    setIsModalOpen(true);
  };

  // 모달 닫기
  const closeModal = () => {
    setIsModalOpen(false);
  };

  // 더보기 버튼 클릭 시
  const handleShowMore = () => {
    setHeaderHeight(140); // 높이를 70px에서 140px로 변경
    setVisibleCategories(categories.length); // 모든 카테고리를 보여줌
    setShowMore(true); // 더보기 버튼 클릭 상태 설정
  };

  // 접기 버튼 클릭 시
  const handleCollapse = () => {
    setHeaderHeight(70); // 높이를 다시 70px로 변경
    setVisibleCategories(3); // 처음 3개의 카테고리만 보여줌
    setShowMore(false); // 더보기 버튼 접기 상태 설정
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

  const handleBookmarkToggle = async (til) => {
    try {
      const updatedTil = { ...til, bookmark: !til.bookmark };
      await axiosInstance.patch(
          `/til/${user_id}/bookmark`,
          { tilTitle: til.title },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
      );

      const updatedList = tilList.map((item) =>
          item.til_id === til.til_id ? updatedTil : item
      );
      const sortedList = updatedList.sort((a, b) => b.bookmark - a.bookmark);

      setTilList(sortedList);
      setFilteredTilList(sortedList);
    } catch (error) {
      console.error("Error updating bookmark", error);
    }
  };

  return (
      <div className="List-talltotal">
        <div className="listmoongu">
          <span>카테고리를 설정하세요</span>
        </div>
        <div className="listsecondmoongu">
          <span>그리고 작성하기 버튼을 클릭하세요. 마음껏 작성하세요!</span>
        </div>

        <div className="List-container">
          <div
              className="List-header"
              style={{
                height: `${headerHeight}px`,
                transition: "height 0.3s ease",
              }}
          >
            <button onClick={openModal} className="Tab-addbutton">
              +
            </button>
            {categories.length > 3 && (
                <button
                    className="ShowMoreButton"
                    onClick={showMore ? handleCollapse : handleShowMore}
                >
                  {showMore ? "접기" : "더보기"}
                </button>
            )}
            <ListModal
                isOpen={isModalOpen}
                closeModal={closeModal}
                onSave={handleSaveCategory}
                categories={categories}
                selectedColor={selectedColor}
                setSelectedColor={setSelectedColor}
            />

            <button className="Tab-Totalbutton" onClick={handleShowAll}>
              전체보기
            </button>
            {filteredCategories
                .slice(0, visibleCategories)
                .map((category, index) => (
                    <button
                        className="AddSubjectbutton"
                        key={index}
                        onClick={() => handleCategoryClick(category.subjectName)}
                        style={{
                          border: `2px solid ${category.color || "transparent"}`,
                          marginBottom: "10px",
                          "--border-color": category.color || "transparent",
                        }}
                    >
                      {category.subjectName}
                    </button>
                ))}
          </div>

          <div className="list-searchmocklist">
            <input
                type="text"
                placeholder="제목을 검색하세요"
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
                <div key={index} className="list_checkbox">
                  <input
                      type="checkbox"
                      checked={til.bookmark}
                      onChange={() => handleBookmarkToggle(til)}
                      id={`list_check_${index}`}
                  />
                  <label htmlFor={`list_check_${index}`} />
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
                    <Listprogress til={til.maxDayValue} /> {/* maxDayValue를 사용 */}
                  </div>

                  {index !== filteredTilList.length - 1 && (
                      <hr className="category-separator" />
                  )}
                </div>
            ))}
          </div>
          <Listcontentmodal
              isOpen={isItemModalOpen}
              onClose={closeItemModal}
              item={selectedItem}
          />
        </div>
      </div>
  );
}

export default List;
