import "./Read.css";
import { useState, useEffect } from "react";
import { Editor } from "react-draft-wysiwyg";
import { EditorState, convertFromRaw } from "draft-js";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import axios from "axios";

const Read = () => {
  const [editorState, setEditorState] = useState(() =>
      EditorState.createEmpty()
  );
  const token = localStorage.getItem("token");
  const user_id = localStorage.getItem("user_id");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/til/${user_id}/2`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        const raw = response.data.content;
        console.log(raw);
        if (raw) {
          const contentState = convertFromRaw(JSON.parse(raw)); // JSON.parse 메서드로 텍스트를 자바스크립트 객체로 파싱
          const newEditorState = EditorState.createWithContent(contentState); //convertFromRaw 메서드로 contentstate로
          //EditorState.createWithContent 메서드를 이용해서 Draft.js의 EditorState로 만들고 현재 상태에 저장
          setEditorState(newEditorState);
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, [token, user_id]);

  return (
      <div className="Read">
        <Editor
            editorState={editorState}
            placeholder="메모장"
            wrapperClassName="wrapper"
            editorClassName="editor-class"
            toolbarHidden={true}
            readOnly={true}
        />
      </div>
  );
};

export default Read;
