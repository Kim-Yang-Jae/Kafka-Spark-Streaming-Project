/* eslint-disable react-hooks/exhaustive-deps */
import React from "react";
import { useEffect, useState, useRef } from "react";
import { useParams } from "react-router-dom";
import {
  getKeywordNews,
  getAllKeywordNews,
} from "../../apis/api/axiosinstance";
import MainNewsCard from "../../components/Main-Components/MainNewsCard";
import "./SearchResult.scss";
import { ToastContainer } from "react-toastify";
// Charts
import SearchResultChart1 from "../../components/Charts-Components/SearchResultChart1";
import SearchResultChart2 from "../../components/Charts-Components/SearchResultChart2";

// MUI
import { Divider, Button } from "@mui/material";

// API
import { keywordRegister, keywordStatus, deleteKeyword } from "../../apis/news";

const SearchResult = () => {
  const { keyword } = useParams();

  // 로그인 중이고
  const isLoggedIn = localStorage.getItem("Authorization");
  // 등록 중이면
  const [subkeyword, setSubKeyword] = useState(null);

  const handleSub = () => {
    keywordRegister(keyword);
    console.log("등록");
    setSubKeyword(true);
  };

  const handleCancel = () => {
    console.log(keyword);
    deleteKeyword(keyword);
    console.log("제거");
    setSubKeyword(false);
  };

  useEffect(() => {
    async function checkSubscriptionStatus() {
      const data = await keywordStatus(keyword);
      setSubKeyword(data);
    }
    checkSubscriptionStatus();
  }, [keyword]);

  const pageSize = 10;
  const mainBotLeftRef = useRef(null);
  const [newsData, setNewsData] = useState([]);
  const [splitKeyword, setSplitKeyword] = useState({
    textList: [],
    valueList: [],
  });

  const getNews = async (keyword, page) => {
    const res = await getAllKeywordNews(keyword);
    const combinedTitle = res
      .map((news) => {
        return news.title;
      })
      .join(" ");
    const cleanStr = combinedTitle.replace(/[^\p{L}\p{N}\s]/gu, "");
    const count = countWords(cleanStr);
    setSplitKeyword(count);
    if (page === 0) {
      try {
        const res1 = await getKeywordNews(keyword, page);
        setNewsData(res1);
      } catch (error) {
        console.log(error);
      }
    } else {
      const res2 = await getKeywordNews(keyword, page);
      setNewsData((prevData) => [...prevData, ...res2]);
    }
  };

  useEffect(() => {
    getNews(keyword, 0);
  }, [keyword]);

  const handleScroll = () => {
    const el = mainBotLeftRef.current;
    if (el.scrollTop + el.clientHeight + 1 >= el.scrollHeight) {
      getNews(keyword, Math.ceil(newsData.length / pageSize));
    }
  };

  useEffect(() => {
    const el = mainBotLeftRef.current;
    el.addEventListener("scroll", handleScroll);
    return () => {
      el.removeEventListener("scroll", handleScroll);
    };
  }, [newsData.length]);

  const countWords = (text) => {
    const words = text.split(" ");
    const wordCounts = {};
    words.forEach((word) => {
      if (wordCounts[word]) {
        wordCounts[word]++;
      } else {
        wordCounts[word] = 1;
      }
    });
    const result = Object.entries(wordCounts)
      .map(([text, value]) => ({ text, value }))
      .sort((a, b) => b.value - a.value)
      .slice(0, 10);

    const textList = result.map(({ text }) => text);
    const valueList = result.map(({ value }) => value);

    return { textList, valueList };
  };

  return (
    <div className="searchresult-wrapper" style={{ "font-family": 'Jua, sans-serif'}}>
      <ToastContainer />
      <div className="searchresult-container">
        <div className="searchresult-text">
          <h2>"{keyword}" 뉴스 기사</h2>
          {isLoggedIn && (
            <Button
              onClick={subkeyword ? handleCancel : handleSub}
              variant="contained"
            >
              {subkeyword ? "키워드 등록취소" : "키워드 등록하기"}
            </Button>
          )}
        </div>
        <div className="searchresult-text2">
          <h2>T:LENS 검색어 분석 : {keyword}</h2>
        </div>
      </div>
      <Divider />
      {newsData?.length > 0 ? (
        <div className="searchresult-main">
          <div className="searchresult-news" ref={mainBotLeftRef}>
            <MainNewsCard newsData={newsData} />
          </div>
          <Divider orientation="vertical" flexItem />
          <div className="searchresult-chart">
            <h4>"{keyword}" 연관 검색어 추천</h4>
            <div style={{ border: "1px solid #D8D8D8" }}>
              <SearchResultChart1 splitKeyword={splitKeyword} />
            </div>
            <h4>"{keyword}" 연관 키워드</h4>
            <div style={{ border: "1px solid #D8D8D8" }}>
              <SearchResultChart2 keyword={keyword} />
            </div>
          </div>
        </div>
      ) : (
        <h2 ref={mainBotLeftRef}>검색 결과가 없습니다!</h2>
      )}
    </div>
  );
};

export default SearchResult;
