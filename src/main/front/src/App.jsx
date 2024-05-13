//import logo from './logo.svg';
import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
/*import Login from "./pages/login/Login.jsx";
import Firstmain from "./pages/Firstmain.jsx";
import List from "./pages/list/List.jsx";
import Today from "./pages/Today/Today.jsx";
import Gong from "./pages/Gong.jsx";
import Mainp from "./pages/mainprofilpage/Mainp.jsx";
import Memo from "./pages/memo/Memo.jsx";
import Signup from "./pages/signup/L-signup.jsx";
import PwSearch from "./pages/login/L-pwSearch.jsx";
import Repw from "./pages/login/Repw.jsx";
import Fsignup from "./pages/signup/Fsignup.jsx";
import LsignupC from "./pages/signup/L-signupC.jsx";
import Myp from "./pages/myprofilpage/Myp.jsx";*/

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
