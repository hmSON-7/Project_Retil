import { useState, useEffect } from "react";
import "./Progressbar.css";
function Progressbar() {
  const [filled, setFilled] = useState(0);

  useEffect(() => {
    if (filled < 100) {
      // 스프링부트에서 시간 값을 가져오면 여기를 수정해야할듯
      setTimeout(() => setFilled((prev) => (prev += 2)), 50);
    }
  });

  return (
    <div className="Progressbar">
      <div className="progressbar">
        <div
          style={{
            height: "100%",
            width: `${filled}%`,
            background: "#a66cff",
            transition: "width 0.1s",
          }}
        ></div>
        <span className="progressPercent">{filled}%</span>
      </div>
    </div>
  );
}

export default Progressbar;
