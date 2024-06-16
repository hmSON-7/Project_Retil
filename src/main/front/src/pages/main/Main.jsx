import "./Main.css";
import Mainp from "../mainprofilpage/Mainp";
import MainProfile from "./MainProfile";
import MainGraph from "./MainGraph";
import Today from "../Today/Today.jsx"
const Main = () => {
    return (
        <div className="main_content">
            <div className="main_Menubar">
                <Mainp />
            </div>
            <div className="main_top">
                <div className="main_graph">
                    <span>내 진행사항</span>
                    <MainGraph />
                </div>
                <div className="main_profile">
                    <span>내 목표</span>
                    <MainProfile />
                </div>
            </div>
            <div className = "main_today">
                <Today/>
            </div>
        </div>
    );
};

export default Main;
