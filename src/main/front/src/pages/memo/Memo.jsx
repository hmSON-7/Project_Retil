import "./Memo.css";
import { useState } from "react";
import { Editor, EditorState } from "draft-js";
import M_Category from "./M_Category";
import M_CategorySelect from "./M_CategorySelect";
import M_TitleInput from "./M_TitleInput";
import "draft-js/dist/Draft.css";
function Memo() {
  const [editorState, setEditorState] = useState(() =>
    EditorState.createEmpty()
  );
  return (
    <div className="Memo">
      <h6>
        <img src="./images/ico/retil.png" />
      </h6>
      <form>
        <button>임시저장</button>
        <button>저장하기</button>
        <M_Category />
        <M_CategorySelect />
        <M_TitleInput />
        <div className="editor">
          <Editor
            editorState={editorState}
            onChange={setEditorState}
            placeholder="메모장이다."
          />
        </div>
      </form>
    </div>
  );
}

export default Memo;
