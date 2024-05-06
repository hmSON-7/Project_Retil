import "./Fsignup.css";
import { Link } from "react-router-dom";

function Fsignup() {
  return (
    <div className="fsignup">
      <h2>회원가입이 완료되었습니다.</h2>
      <Link to={"/login"}>
        <button>로그인</button>
      </Link>
    </div>
  );
}

export default Fsignup;
