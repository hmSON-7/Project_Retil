import "./Room.css";
import axiosInstance from "../../api/axiosInstance.js";

const Room = ({ group, myGroupIds }) => {
    const token = localStorage.getItem("token");
    const user_id = localStorage.getItem("user_id");

    const onClick_button = async () => {
        if (!myGroupIds.includes(group.id)) {
            try {
                await axiosInstance.post(`/group/join`, {
                    user_id: user_id,
                    groupName: group.groupName
                }, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                // 그룹 참여 후 페이지를 새로고침하거나 상태를 업데이트
                window.location.reload();
            } catch (error) {
                console.error("Error joining group:", error);
            }
        }
    };

    return (
        <div className="group_item">
            <span className="item_name">{group.groupName}</span>
            <span className="item_ex">{group.groupIntroduce}</span>
            <span className="item_user">{group.groupOwner.nickname}</span>
            <span className="item_limit">{group.memberCurrent} / {group.memberLimit}</span>
            <button
                onClick={onClick_button}
                disabled={myGroupIds.includes(group.id)}
            >
                {myGroupIds.includes(group.id) ? "참여 중" : "참여하기"}
            </button>
        </div>
    )
}

export default Room;
