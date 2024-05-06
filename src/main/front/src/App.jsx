import {useState} from 'react'
import './App.css'
import {Route, Routes} from "react-router-dom";
import Home from "./home.jsx";

function App() {
    const [count, setCount] = useState(0)

    return (
        <div>
            <h1>"첫 화면입니다!"</h1>

        <Routes>
            <Route path="/api/home" element={<Home />} />
        </Routes>

        </div>
    )
}

export default App;
