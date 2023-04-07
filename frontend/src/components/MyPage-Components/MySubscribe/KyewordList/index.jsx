import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import { subKeyword, deleteKeyword } from "../../../../apis/news";
import './keywordList.scss';

function KeywordList() {
  const [selectedKeyword, setSelectedKeyword] = useState('');
  const [keywordData, setKeywordData] = useState([]);

  useEffect(() => {
    const fetchScrapList = async () => {
      try {
        const response = await subKeyword();
        setKeywordData(response);
      } catch (error) {
        console.error(error);
      }
    };
    fetchScrapList();
  }, []);

  function handleKeywordClick(word) {
    setSelectedKeyword(word);
  }

  async function handleDeleteClick(word) {
    try {
      await deleteKeyword(word);
      setKeywordData(keywordData.filter((keyword) => keyword.name !== word));
    } catch (error) {
      console.error(error);
    }
  }

  return (
    <div className="keyword-list-container">
      <ToastContainer />
      <div className="keyword-list">
        {keywordData && keywordData.length > 0 && (
          keywordData.map((keyword) => (
            <div
              key={keyword.id}
              className="keyword-item"
              onMouseEnter={(e) => e.currentTarget.lastChild.style.display = "inline-block"}
              onMouseLeave={(e) => e.currentTarget.lastChild.style.display = "none"}
            >
              <div className="keyword-name" onClick={() => handleKeywordClick(keyword.name)}>
                {keyword.name}
              </div>
              <div
                className="delete-button"
                onClick={(e) => handleDeleteClick(keyword.name)}
              >
                X
              </div>
            </div>
          ))
        )}
      </div>
      {selectedKeyword && (
        <Link to={`/search/${selectedKeyword}`} className="keyword-search">
          {selectedKeyword}에 대한 키워드 검색 >>
        </Link>
      )}
    </div>
  );
}

export default KeywordList;
