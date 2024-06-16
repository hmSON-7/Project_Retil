import "./Group.css";
import MainP from "../mainprofilpage/Mainp";
import { useState, useEffect } from "react";
import Room from "./Room.jsx";
import GroupModal from "./GroupModal.jsx";
import axiosInstance from "../../api/axiosInstance.js";

const Group = () => {
    const [search, setSearch] = useState("");
    const [group, setGroup] = useState([]);
    const [myGroup, setMyGroup] = useState([]);
    const [filter, setFilter] = useState("all"); // "all" 또는 "mine"
    const [newGroup, setNewGroup] = useState({ groupName: "", introduce: "", limit: "" });
    const [showForm, setShowForm] = useState(false);

    const token = localStorage.getItem("token");
    const user_id = localStorage.getItem("user_id");

    useEffect(() => {
        const fetchGroupData = async () => {
            try {
                const response = await axiosInstance.get(`/group/show`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setGroup(response.data);
            } catch (error) {
                console.error("Error fetching group data:", error);
            }
        };

        const fetchMyGroupData = async () => {
            try {
                const response = await axiosInstance.get(`http://localhost:8080/group/${user_id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setMyGroup(response.data);
            } catch (error) {
                console.error("Error fetching my group data:", error);
            }
        };

        fetchGroupData();
        fetchMyGroupData();
    }, [filter, search]); // filter 또는 search 값이 변경될 때마다 실행

    // 필터링된 그룹 목록 생성
    const filteredGroups = filter === "all" ? group : myGroup;
    const finalFilteredGroups = filteredGroups.filter((g) =>
        g.groupName.toLowerCase().includes(search.toLowerCase())
    );

    // 참여 중인 그룹 ID 목록 생성
    const myGroupIds = myGroup.map(g => g.id);

    const onChangeSearch = (e) => {
        setSearch(e.target.value);
    };

    const handleFilterAll = () => {
        setFilter("all");
    };

    const handleFilterMine = () => {
        setFilter("mine");
    };

    const onClickAdd = () => {
        setShowForm(true);
    };

    const handleFormChange = (e) => {
        const { name, value } = e.target;
        setNewGroup((prevGroup) => ({
            ...prevGroup,
            [name]: value,
        }));
    };

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        try {
            await axiosInstance.post(`/group/${user_id}/create`, newGroup, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setShowForm(false);
            setNewGroup({ groupName: "", introduce: "", limit: "" });
            const response = await axiosInstance.get(`/group/show`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setGroup(response.data);
        } catch (error) {
            console.error("Error creating group:", error);
        }
    };

    const handleCloseModal = () => {
        setShowForm(false);
    };

    return (
        <>
            <MainP />
            <div className="group_div">
                <div className="group_top">
                    <button onClick={handleFilterAll}>전체</button>
                    <button onClick={handleFilterMine}>내가 포함된 그룹</button>
                    <button className="group_add" onClick={onClickAdd}>+</button>
                    <input
                        value={search}
                        onChange={onChangeSearch}
                        placeholder="검색어를 입력해주세요"
                    />
                </div>
                <GroupModal
                    show={showForm}
                    onClose={handleCloseModal}
                    onChange={handleFormChange}
                    onSubmit={handleFormSubmit}
                    group={newGroup}
                />
                <div className="group_span">
                    <span className="group_name">그룹명</span>
                    <span className="group_ex">한줄소개</span>
                    <span className="group_user">방장아이디</span>
                    <span className="group_limit">현재인원</span>
                </div>
                <div className="group_list">
                    {finalFilteredGroups.map((g) => (
                        <Room key={g.id} group={g} myGroupIds={myGroupIds} />
                    ))}
                </div>
            </div>
        </>
    );
};

export default Group;
