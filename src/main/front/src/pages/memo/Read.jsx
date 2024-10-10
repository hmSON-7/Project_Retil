import "./Read.css";
import { useState, useEffect } from "react";
import { Editor } from "react-draft-wysiwyg";
import { EditorState, convertFromRaw } from "draft-js";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import axiosInstance from "../../api/axiosInstance.js";

const Read = () => {
  const [editorState, setEditorState] = useState(() =>
      EditorState.createEmpty()
  );
  const token = localStorage.getItem("token");
  const user_id = localStorage.getItem("user_id");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axiosInstance.get(`/til/${user_id}/4`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        const raw = response.data.content;
        console.log(raw);
        if (raw) {
          const extractedContent = JSON.parse(raw);
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
