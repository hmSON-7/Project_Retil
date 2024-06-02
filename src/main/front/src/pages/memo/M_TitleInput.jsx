import "./M_TitleInput.css";

function M_TitleInput({ title, setTitle }) {
    const handleChange = (e) => {
        setTitle(e.target.value);
    };
  return (
    <div className="titleInput">
      <input placeholder=" 제목을 입력해주세요"
             value={title}
             onChange={handleChange} />
    </div>
  );
}

export default M_TitleInput;
