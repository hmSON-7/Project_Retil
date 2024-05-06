//import logo from './logo.svg';
import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "../src/pages/login/Login";
import Firstmain from "./pages/Firstmain";
import List from "./pages/list/List";
import Today from "./pages/Today/Today";
import Gong from "./pages/Gong";
import Mainp from "./pages/mainprofilpage/Mainp";
import Memo from "./pages/memo/Memo";
import Signup from "./pages/signup/L-signup";
import PwSearch from "./pages/login/L-pwSearch";
import Repw from "./pages/login/Repw";
import Fsignup from "./pages/signup/Fsignup";
import LsignupC from "./pages/signup/L-signupC";
import Myp from "./pages/myprofilpage/Myp";

//import Findid from './pages/Findid';
//import Findpw from './pages/Findpw';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Firstmain />} />
        <Route path="login" element={<Login />} />
        <Route path="gong" element={<Gong />} />
        <Route path="list" element={<List />} />
        <Route path="today" element={<Today />} />
        <Route path="mainp" element={<Mainp />} />
        <Route path="memo" element={<Memo />} />
        <Route path="signup" element={<Signup />} />
        <Route path="pwSearch" element={<PwSearch />} />
        <Route path="repw" element={<Repw />} />
        <Route path="fsignup" element={<Fsignup />} />
        <Route path="L-signupC" element={<LsignupC />} />
        <Route path="myp" element={<Myp />} />
        {/*<Route path="/findid" element={<Findid />} />
        <Route path="/findpw" element={<Findpw />} /> */}
      </Routes>
    </div>
  );
}

export default App;
