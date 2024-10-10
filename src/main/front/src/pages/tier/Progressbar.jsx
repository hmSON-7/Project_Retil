import React from "react";
import "./Progressbar.css";

function Progressbar({ progressPercentage }) {
    return (
        <div className="Progressbar">
            <div className="progressbar">
                <div
                    style={{
                        height: "100%",
                        width: `${progressPercentage}%`,
                        background: "gray",
                        transition: "width 0.1s",
                    }}
                ></div>
                <span className="progressPercent">{progressPercentage.toFixed(2)}%</span>
            </div>
        </div>
    );
}

export default Progressbar;
