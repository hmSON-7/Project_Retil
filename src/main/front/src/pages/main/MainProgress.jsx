import "./MainProgress.css";
import { useState, useEffect } from "react";

const MainProgress = () => {
  const [filled, setFilled] = useState(0);

  useEffect(() => {
    if (filled < 100) {
      setTimeout(() => setFilled((prev) => (prev += 2)), 50);
    }
  });
  return (
    <div className="profile_Progress">
      <div className="time_Progress">
        <div
          style={{
            height: "100%",
            width: `${filled}%`,
            background: "rgb(77, 173,228)",
            transition: "width 0.1s",
            zIndex: 2,
          }}
        ></div>
        <span className="progress_Percent">{filled}%</span>
      </div>
    </div>
  );
};

export default MainProgress;
