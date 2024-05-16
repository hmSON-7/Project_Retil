import "./M_CategorySelect.css";
import { useState } from "react";

function M_CategorySelect() {
  const [selectedOption, setSelectedOption] = useState("option1");

  const handleChange = (event) => {
    setSelectedOption(event.target.value);
  };

  return (
    <div className="categorySelect">
      <select value={selectedOption} onChange={handleChange}>
        <option value="option1">Option 1</option>
        <option value="option2">Option 2</option>
        <option value="option3">Option 3</option>
      </select>
    </div>
  );
}

export default M_CategorySelect;
