//import logo from './logo.svg';
import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import Firstmain from "./pages/Firstmain";
import List from "./pages/List";


function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Firstmain />} />
        
      </Routes>
    </div>
  );
}

export default App;
