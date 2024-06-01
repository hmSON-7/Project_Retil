import axios from "axios";
import "./MockList.css";
import { useState, useEffect } from "react";

const token = localStorage.getItem("token");
const user_id = localStorage.getItem("user_id");

// 누르면 리스트의 에디터로 넘어가지는 거
// 리스트 오늘의 문제 진행 현황 시작하면 될듯
const MockList = () => {
  const [list, setList] = useState([]);
  const subjectName = "folder1"
  useEffect(() => {
    const fetchData = async () => {
      try {
        const encodedSubjectName = encodeURIComponent(subjectName);
        const response = await axios.get(
          `http://localhost:8080/til/${user_id}/subject/${encodedSubjectName}`, // 이부분은 카테고릴 이름을 props로 받아서 넣으면 될듯
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setList(response.data);
      } catch (error) {
        console.error("Error data:", error);
      }
    };
    fetchData();
  }, []);
  return (
    <div className="list-container">
      {list.length === 0 ? (
        <p>Loading...</p>
      ) : (
        <ul>
          {list.map((item, index) => (
            <li key={index} className="list-item">
              <div className="list-item-content">
                <h3>{item.title}</h3>
                <p>Subject: {item.subjectName}</p>
                <p>Save Time: {new Date(item.saveTime).toLocaleString()}</p>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default MockList;
