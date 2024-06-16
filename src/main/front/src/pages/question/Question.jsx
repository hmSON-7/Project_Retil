import "./Question.css";
import {useState} from "react";
import MockList from "./MockList";

const token = localStorage.getItem("token");
const user_id = localStorage.getItem("user_id");

const mockData = [
    {
        id: 1,
        name: "국어",
        color: "red"
    },
    {
        id: 2,
        name: "C 언어",
        color: "pink"
    },
    {
        id: 3,
        name: "java 언어",
        color: "pink"
    }
];

const mockList = [
    {
        id: 1,
        bookmark: false,
        subjectName: "국어",
        title: "누가 내 머리 위에 똥 쌌어?",
        color: ""
    },
    {
        id: 2,
        bookmark: true,
        subjectName: "C 언어",
        title: "포인터 그것이 알고 싶다",
        color: ""
    }
];

const Question = () => {
    const [category, setCategory] = useState(mockData);
    const [search, setSearch] = useState("");
    const [select, setSelect] = useState("");
    const [list, setList] = useState(mockList);

    const allItems = {
        id: "all",
        name: "전체보기",
        color: "rgb(77, 173,228)"
    };

    const onChangeSearch = (e) => {
        setSearch(e.target.value);
    };

    const handleBookmarkToggle = (id) => {
        setList((prevList) =>
            prevList.map((item) =>
                item.id === id ? {...item, bookmark: !item.bookmark} : item
            )
        );
    };

    const filteredList = list.filter((item) => {
        return (
            item.title.toLowerCase().includes(search.toLowerCase()) &&
            (select === "" || item.subjectName === select)
        );
    });

    return (
        <>
            <div className="question_div">
                <div className="question_top">
                    <button
                        key={allItems.id}
                        style={{backgroundColor: allItems.color}}
                        onClick={() => setSelect("")}
                    >
                        {allItems.name}
                    </button>
                    {category.map((item) => (
                        <button
                            key={item.id}
                            style={{backgroundColor: item.color}}
                            onClick={() => setSelect(item.name)}
                        >
                            {item.name}
                        </button>
                    ))}
                    <input
                        value={search}
                        onChange={onChangeSearch}
                        placeholder="검색어를 입력해주세요"
                    />
                </div>
                <div className="span_span">
                    <span>즐겨찾기</span>
                    <span>카테고리</span>
                    <span>제목</span>
                    <span className="span_day">작성날짜</span>
                </div>
                <div className="question_list">
                    <MockList list={filteredList} onToggleBookmark={handleBookmarkToggle}/>
                </div>
            </div>
        </>
    );
};

export default Question;
