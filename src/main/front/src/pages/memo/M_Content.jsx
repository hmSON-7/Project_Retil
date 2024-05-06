import { useState } from "react";
import { Editor, EditorState } from "draft-js";
import "draft-js/dist/Draft.css";
function M_Content() {
  const [editorState, setEditorState] = useState(() =>
    EditorState.createEmpty()
  );
  return (
    <Editor
      editorState={editorState}
      onChange={setEditorState}
      placeholder="메모장이다."
    />
  );
}

export default M_Content;
