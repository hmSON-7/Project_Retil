import React from 'react';
import './Pagination.css'; // CSS 파일 import


const Pagination = ({ totalItems, itemsPerPage, setCurrentPage, currentPage }) => {
  const pageNumbers = [];
  for (let i = 1; i <= Math.ceil(totalItems / itemsPerPage); i++) {
    pageNumbers.push(i);
  }

  //next버튼
  const handleNextPage = () => {
    setCurrentPage(currentPage + 1);
  };
  //pre버튼
  const handlePrevPage = () => {
    setCurrentPage(currentPage - 1);
  };

  return (
    <ul className="page-numbers">
      <li className="li-page-numbers">
        <button
          className="button-page-numbers"
          onClick={handlePrevPage}
          disabled={currentPage === 1}
        >
          <img src="src/assets/prebuttongroup.png" alt="Previous Page"  />
        </button>
      </li>
      {pageNumbers.map((number) => (
        <li key={number} className="li-page-numbers">
          <button
            className={currentPage === number ? "button-page-numbers active" : "button-page-numbers"}
            onClick={() => setCurrentPage(number)}
            disabled={currentPage === number}
          >
            {number}
          </button>
        </li>
      ))}
      <li className="li-page-numbers">
        <button
          className="button-page-numbers"
          onClick={handleNextPage}
          disabled={currentPage === Math.ceil(totalItems / itemsPerPage)}
        >
          <img src="src/assets/nextbuttongroup.png" alt="Next Page"  />
        </button>
      </li>
    </ul>
  );
};

export default Pagination;
