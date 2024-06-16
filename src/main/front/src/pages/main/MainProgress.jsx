import "./MainProgress.css";

const MainProgress = ({ percent }) => {
    const formattedPercent = percent.toFixed(2);
    return (
        <div className="profile_Progress">
            <div className="time_Progress">
                <div
                    style={{
                        height: "100%",
                        width: `${percent}%`,
                        background: "rgb(77, 173,228)",
                        transition: "width 0.1s",
                        zIndex: 2,
                    }}
                ></div>
            </div>
            <span className="progress_Percent">{formattedPercent}%</span>
        </div>
    );
};

export default MainProgress;
