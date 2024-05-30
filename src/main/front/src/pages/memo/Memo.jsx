import "./Memo.css";
import { useState, useEffect } from "react";
import { Editor } from "react-draft-wysiwyg";
import { EditorState, convertToRaw, convertFromRaw } from "draft-js";
import M_Category from "./M_Category";
import M_CategorySelect from "./M_CategorySelect";
import M_TitleInput from "./M_TitleInput";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import axios from "axios";

function Memo() {
  const [editorState, setEditorState] = useState(() => EditorState.createEmpty());
  const user_id = localStorage.getItem("user_id");
  const token = localStorage.getItem("token");
  const [inputCount, setInputCount] = useState(0);
  const [statusMessage, setStatusMessage] = useState("");
  const [studyTime, setStudyTime] = useState(0);
  let timer;

  const onEditorStateChange = (newEditorState) => {
    const contentState = newEditorState.getCurrentContent();
    const text = contentState.getPlainText("");
    if (text.length <= 2048) {
      setInputCount(text.length);
      setEditorState(newEditorState);
    }
  };

  const subjectName = "연습";
  const title = "안녕";

  const saveContent = () => {
    const contentState = editorState.getCurrentContent();
    const raw = convertToRaw(contentState);
    localStorage.setItem("study", JSON.stringify(raw, null, 2));
  };

  const formatTime = (seconds) => {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    const remainingSeconds = seconds % 60;
    return `${hours}시간 ${minutes}분 ${remainingSeconds}초`;
  };

  const startTimer = () => {
    timer = setInterval(() => {
      setStudyTime((prevTime) => prevTime + 1);
    }, 1000);
  };

  const save2Content = async (e) => {
    e.preventDefault();
    if (!user_id || !token) {
      setStatusMessage("User not logged in or token missing.");
      return;
    }

    try {
      const contentState = editorState.getCurrentContent();
      const rawContentState = convertToRaw(contentState);
      const content = JSON.stringify(rawContentState, null, 2);

      const response = await axios.post(
          `http://localhost:8080/til/${user_id}/write`,
          {
            subjectName: "folder1", // 여기서 실제 데이터로 변경할 것
            title: title, // 여기서 실제 데이터로 변경할 것
            content: content,
            time: studyTime * 1000
          },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
      );

      if (response.status === 200) {
        setStatusMessage("Successfully saved");
        setStudyTime(0); // 저장 후 타이머 초기화
      } else {
        setStatusMessage("Failed to save");
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        setStatusMessage("Token expired or invalid. Please log in again.");
      } else {
        setStatusMessage("There was an error saving the content!");
      }
      console.error("Error saving content:", error);
    }
  };

  useEffect(() => {
    const raw = localStorage.getItem("study");
    if (raw) {
      const contentState = convertFromRaw(JSON.parse(raw));
      const newEditorState = EditorState.createWithContent(contentState);
      setEditorState(newEditorState);
    }
    startTimer();
    return () => clearInterval(timer);
  }, []);

  return (
      <div className="Memo">
        <div className="Memo_form">
          <p>
            <img src="./images/ico/retil.png" alt="Memo" />
          </p>
          <button onClick={saveContent} className="temporaryStorage">
            임시저장
          </button>
          <button onClick={save2Content}>저장하기</button>
          <div className="memo_top">
            <M_Category />
            <M_CategorySelect />
            <M_TitleInput />
          </div>
          <div className="editorStyle">
            <Editor
                editorState={editorState}
                onEditorStateChange={onEditorStateChange}
                placeholder="메모장"
                wrapperClassName="wrapper-class"
                editorClassName="editor-class"
                toolbarClassName="toolbar-class"
            />
            <div className="limit">
              <span>{inputCount}</span>
              <span>/2048 자</span>
            </div>
          </div>
          {statusMessage && <div className="statusMessage">{statusMessage}</div>}
          <span>공부한 시간: {formatTime(studyTime)}</span>
        </div>
      </div>
  );
}

export default Memo;
