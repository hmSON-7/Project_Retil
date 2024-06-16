import "./M_CategorySelect.css";
import { useState, useEffect } from "react";
import axiosInstance from "../../api/axiosInstance.js";

const token = localStorage.getItem("token");
const user_id = localStorage.getItem("user_id");

function M_CategorySelect({ setSubject }) { // prop으로 setSubject 받기
  const [options, setOptions] = useState([]);
  const [selectedOption, setSelectedOption] = useState("");

  const handleChange = (event) => {
    setSelectedOption(event.target.value);
    setSubject(event.target.value); // 선택된 옵션 값을 부모 컴포넌트로 전달
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axiosInstance.get(`/til/${user_id}/write`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        // 서버에서 받은 데이터로 옵션 설정
        const subjectNames = response.data.map(item => item.subjectName);
        setOptions(subjectNames);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    fetchData();
  }, []);

  return (
      <div className="categorySelect">
        <select value={selectedOption} onChange={handleChange}>
          <option value="" disabled>과목을 선택해주세요</option>
          {options.map((subjectName, index) => (
              <option key={index} value={subjectName}>
                {subjectName}
              </option>
          ))}
        </select>
      </div>
  );
}

export default M_CategorySelect;
