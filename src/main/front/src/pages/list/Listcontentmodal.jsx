import React, { useState, useEffect } from 'react';
import axiosInstance from "../../api/axiosInstance.js";
import './Listcontentmodal.css';
import { Editor } from "react-draft-wysiwyg";
import { EditorState, convertFromRaw } from "draft-js";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";

function Listcontentmodal({ isOpen, onClose, item }) {
  const [content, setContent] = useState(""); // content 상태 설정
  const [editorState, setEditorState] = useState(() =>
    EditorState.createEmpty()
  );
  const token = localStorage.getItem("token"); // localStorage에서 token 가져오기
  const user_id = localStorage.getItem("user_id"); // localStorage에서 user_id 가져오기

  useEffect(() => {
    if (isOpen && item) {
      // isOpen이 true이고 item이 존재할 때 content를 가져오는 함수 호출
      fetchContent(user_id, item.til_id); // user_id와 til_id를 인자로 넘겨줌
    }
  }, [isOpen, item]); // isOpen 또는 item이 변경될 때마다 useEffect 실행

  const fetchContent = async (user_id, til_id) => {
    try {
      // Axios를 사용하여 content 가져오기
      const response = await axiosInstance.get(`/til/${user_id}/${til_id}`, {
        headers: {
          Authorization: `Bearer ${token}`, // 헤더에 Authorization 설정
        },
      });

      console.log("Fetched content:", response.data); // 응답 데이터 확인
      setContent(parseContent(response.data.content)); // 가져온 내용을 파싱하여 상태로 설정

      // 에디터에 contentState 설정
      if (response.data.content) {
        const extractedContent = JSON.parse(response.data.content);
        const contentState = convertFromRaw({
          blocks: extractedContent.map(block => ({
            key: block.key,
            text: block.text,
            type: block.type,
            depth: 0,
            inlineStyleRanges: [],
            entityRanges: [],
            data: {},
          })),
          entityMap: {},
        });
        const newEditorState = EditorState.createWithContent(contentState);
        setEditorState(newEditorState);
      }
    } catch (error) {
      console.error("Error fetching TIL content", error); // 에러 발생 시 로그 출력
    }
  };

  // content를 파싱하여 적절히 가공하는 함수
  const parseContent = (rawContent) => {
    try {
      const parsedContent = JSON.parse(rawContent); // JSON 형식의 content 파싱
      console.log("Parsed content:", parsedContent); // 파싱된 내용 확인
      return parsedContent.map(item => item.text).join("\n"); // 각 객체의 text 필드를 개행 문자와 함께 연결하여 반환
    } catch (error) {
      console.error("Error parsing content", error); // 파싱 에러 발생 시 로그 출력
      return "Error parsing content"; // 에러 메시지 반환
    }
  };

  // isOpen이 false이거나 item이 없을 경우 null 반환하여 모달 창을 렌더링하지 않음
  if (!isOpen || !item) return null;

  // saveTime을 원하는 형식으로 변환
  const formattedSaveTime = item.saveTime.split('T')[0];

  // 모달 창 UI 렌더링
  return (
    <div className="modal-overlay">
      <div className="listcontentmodal-content">
        <div className="listcontentmodal-title">
          <h2> 제목 : {item.title}</h2> {/* item의 title 표시 */}
        </div>
        <div className="listcontentmodal-text">
          <Editor
            editorState={editorState}
            placeholder="메모장"
            wrapperClassName="wrapper"
            editorClassName="editor-class"
            toolbarHidden={true}
            readOnly={true}
          />
        </div>
        <p className="listcontentmodal-savetime">{formattedSaveTime}</p> {/* 포맷된 saveTime 표시 */}
        <button className="listcontentmodal-closebutton" onClick={onClose}>Close</button> {/* 모달 닫기 버튼 */}
      </div>
    </div>
  );
}

export default Listcontentmodal;
