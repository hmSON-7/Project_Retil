import React from "react";
import axiosInstance from "../../api/axiosInstance.js";

const FavoriteIcon = ({ id, bookmarked, onToggleBookmark }) => {
  const toggleBookmark = async () => {
    try {
      // 서버로 즐겨찾기 상태 업데이트 요청
      await axiosInstance.put(`/til/${user_id}/`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      // 즐겨찾기 상태를 토글하는 콜백 함수 호출
      onToggleBookmark(id);
    } catch (error) {
      console.error("Error toggling bookmark", error);
    }
  };

  return (
    <div className="favorite-icon" onClick={toggleBookmark}>
      {bookmarked ? "★" : "☆"}
    </div>
  );
};

export default FavoriteIcon;
