import "./Memo.css";
import { useState, useEffect } from "react";
import { Editor } from "react-draft-wysiwyg";
import { EditorState, convertToRaw, convertFromRaw, RichUtils } from "draft-js";
import M_Category from "./M_Category";
import M_CategorySelect from "./M_CategorySelect";
import M_TitleInput from "./M_TitleInput";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";

function Memo() {
  const [editorState, setEditorState] = useState(() =>
    EditorState.createEmpty()
  );
  const [inputCount, setInputCount] = useState(0);
  const [convertedContent, setConvertedContent] = useState(null);
  const onEditorStateChange = (newEditorState) => {
    const contentState = newEditorState.getCurrentContent();
    const text = contentState.getPlainText("");
    if (text.length <= 2048) {
      setInputCount(text.length);
      setEditorState(newEditorState);
    }
  };

  const saveContent = (e) => {
    e.preventDefault(); // form 안에 있어서 콘솔창이 안뜨는 문제로 인해 임시로 함
    const contentState = editorState.getCurrentContent();
    const raw = convertToRaw(contentState); // Draft.js객체를 일반 자바스크립트 객체로 변경
    localStorage.setItem("study", JSON.stringify(raw, null, 2)); //자바스크립트 객체를 텍스트로 변환
    // 브라우저에서 작업 중인 에디터를 db에 저장하는 것이 아닌 localStorage에 저장
    console.log(raw);
  }; // 새로고침 되어도 텍스트 값이 사라지지 않음(임시 코드)

  useEffect(() => {
    const raw = localStorage.getItem("study");
    if (raw) {
      const contentState = convertFromRaw(JSON.parse(raw)); // JSON.parse 메서드로 텍스트를 자바스크립트 객체로 파싱
      const newEditorState = EditorState.createWithContent(contentState); //convertFromRaw 메서드로 contentstate로
      //EditorState.createWithContent 메서드를 이용해서 Draft.js의 EditorState로 만들고 현재 상태에 저장
      setEditorState(newEditorState);
    }
  }, []); // localStorage에서 텍스트를 가져오기 위한 코드 (임시)

  return (
    <div className="Memo">
      <form>
        <p>
          <img src="./images/ico/retil.png" />
        </p>
        <button onClick={saveContent} className="temporaryStorage">
          임시저장
        </button>
        <button className="save">저장하기</button>
        <M_Category />
        <M_CategorySelect />
        <M_TitleInput />
        <div className="editorStyle">
          <Editor
            editorState={editorState}
            onEditorStateChange={onEditorStateChange}
            placeholder="메모장"
            wrapperClassName="wrapper-class"
            editorClassName="editor-class"
            toolbarClassName="toolbar-class"
          />
          <span>{inputCount}</span>
          <span>/2048 자</span>
        </div>
      </form>
    </div>
  );
}

export default Memo;
