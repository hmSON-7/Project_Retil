import "./Question.css";
import MockList from "./MockList";
import Mainp from "../mainprofilpage/Mainp.jsx";
import { useState, useEffect } from "react";
import axiosInstance from "../../api/axiosInstance.js";


const Question = () => {
    const [search, setSearch] = useState("");
    const [select, setSelect] = useState("");
    const [list, setList] = useState([]);

    const token = localStorage.getItem("token");
    const user_id = localStorage.getItem("user_id");

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axiosInstance.get(
                    `/${user_id}/review-list`,
                    {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                );
                const formattedList = response.data.map(item => ({
                    id: item.review_id,
                    subjectName: item.subjectName || "Default Subject", // Assuming you might want to handle missing subject names
                    saveTime: item.date,
                    check: item.flag,
                    tilList: item.tilList // Remove til_id mapping
                }));
                setList(formattedList);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };
        fetchData();
    }, []);

    const onChangeSearch = (e) => {
        setSearch(e.target.value);
    };

    const filteredList = list.filter((item) => {
        const lowerSearch = search.toLowerCase();
        return (
            item.subjectName.toLowerCase().includes(lowerSearch) ||
            item.tilList.some(til => til.toLowerCase().includes(lowerSearch))
        );
    });

    return (
        <>
            <Mainp />
            <div className="questionTitle_div">
                <span>Today Question</span>
            </div>
            <div className="question_div">
                <div className="question_top">
                    <input
                        value={search}
                        onChange={onChangeSearch}
                        placeholder="검색어를 입력해주세요"
                    />
                </div>
                <div className="span_span">
                    <span className="span_title">타이틀</span>
                    <span className="span_create">생성 날짜</span>
                </div>
                <div className="question_list">
                    <MockList list={filteredList} />
                </div>
            </div>
        </>
    );
};

export default Question;