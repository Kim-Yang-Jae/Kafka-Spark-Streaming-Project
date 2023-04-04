import React, { useEffect, useState, useRef } from "react";
import "./Main.scss";

// Charts
import MainNewsCard from "../MainNewsCard";
import BarCharts from "../../Charts-Components/BarCharts";
import Wordcloud from "../../Charts-Components/WordCloud";
import KeywordChart from "../../Charts-Components/KeywordChart";
import SearchResultChart2 from "../../Charts-Components/SearchResultChart2";

// MUI
import Divider from "@mui/material/Divider";
import Box from "@mui/material/Box";

// Api
import { defaultInstance } from "../../../apis/news";

const MainChart = (props) => {
  const [keywordNews, setKeywordNews] = useState([]);
  const [todayNewsCount, setTodayNewsCount] = useState("");
  const [allNewsCount, setAllNewsCount] = useState("");
  const [category, setCategory] = useState("");
  const [allKeywordNews, setAllKeywordNews] = useState([]);

  const pageSize = 10;
  const mainBotLeftRef = useRef(null);

  const getCategoryNews = async (category, page) => {
    if (page === 0) {
      try {
        const response = await defaultInstance.get("/category/news", {
          params: { category, pageNo: page, pageSize },
        });
        const response2 = await defaultInstance.get("/category/news", {
          params: { category, pageNo: 0, pageSize: 1000 },
        });
        setAllKeywordNews(response2.data.conten);
        setKeywordNews(response.data.content);
      } catch (error) {
        console.log(error);
      }
    } else {
      try {
        const response = await defaultInstance.get("/category/news", {
          params: { category, pageNo: page, pageSize },
        });
        const response2 = await defaultInstance.get("/category/count", {
          params: { category },
        });
        setTodayNewsCount(addCommas(response2.data.content.countRecentNews));
        setAllNewsCount(addCommas(response2.data.content.countAllNews));
        setKeywordNews((prevKeywordNews) => [
          ...prevKeywordNews,
          ...response.data.content,
        ]);
      } catch (error) {
        console.log(error);
      }
    }
  };

  const addCommas = (num) => {
    const str = num.toString();
    const len = str.length;
    if (len <= 3) {
      return str;
    } else {
      return addCommas(str.slice(0, len - 3)) + "," + str.slice(len - 3);
    }
  };

  useEffect(() => {
    setKeywordNews([]);
    setCategory("");
    if (props?.value === "1") {
      setCategory("전체");
    } else if (props?.value === "2") {
      setCategory("IT");
    } else if (props?.value === "3") {
      setCategory("경제");
    } else if (props?.value === "4") {
      setCategory("사회");
    } else if (props?.value === "5") {
      setCategory("정치");
    } else if (props?.value === "6") {
      setCategory("세계");
    } else if (props?.value === "7") {
      setCategory("생활");
    } else if (props?.value === "8") {
      setCategory("스포츠");
    } else if (props?.value === "9") {
      setCategory("연예");
    } else {
      setCategory("오피니언");
    }
  }, [props.value]);

  useEffect(() => {
    getCategoryNews(category, 0);
  }, [category]);

  const handleScroll = () => {
    const el = mainBotLeftRef.current;
    if (el.scrollTop + el.clientHeight >= el.scrollHeight) {
      getCategoryNews(category, Math.ceil(keywordNews.length / pageSize));
    }
  };

  useEffect(() => {
    const el = mainBotLeftRef.current;
    el.addEventListener("scroll", handleScroll);
    return () => {
      el.removeEventListener("scroll", handleScroll);
    };
  }, [keywordNews.length]);

  return (
    <div>
      <div className="main-wrapper">
        <Box
          sx={{
            borderTopLeftRadius: 20,
            borderBottomLeftRadius: 20,
            width: "45%",
            height: 120,
            backgroundColor: "primary.dark",
            "&:hover": {
              backgroundColor: "primary.main",
              opacity: [0.9, 0.8, 0.7],
            },
          }}
        >
          <h3 className="main-top-h3">오늘 작성된 기사</h3>
          <h2 className="main-top-h2">{todayNewsCount} 건</h2>
        </Box>
        <hr className="main-top-hr" />
        <Box
          sx={{
            borderTopRightRadius: 20,
            borderBottomRightRadius: 20,
            width: "45%",
            height: 120,
            backgroundColor: "primary.dark",
            "&:hover": {
              backgroundColor: "primary.main",
              opacity: [0.9, 0.8, 0.7],
            },
          }}
        >
          <h3 className="main-top-h3">모든 기사 데이터</h3>
          <h2 className="main-top-h2">{allNewsCount} 건</h2>
        </Box>
      </div>
      <br />
      <div className="main-mid">
        <h2>T:LENS 키워드 통계 : {category}</h2>
      </div>
      <Divider />
      <div className="main-mid-wrapper">
        <div className="main-mid-left">
          <h2>키워드 Top 10</h2>
          <BarCharts />
        </div>
        <Divider orientation="vertical" flexItem />
        <div className="main-mid-right">
          <h2>T:LENS 핫 키워드(Top 30)</h2>
          <Wordcloud />
        </div>
      </div>
      <Divider />
      <h2 className="main-bot-h2">T:LENS 키워드 뉴스 : {category}</h2>
      <Divider />
      <div className="main-bot-wrapper">
        <div className="main-bot-left" ref={mainBotLeftRef}>
          <MainNewsCard newsData={keywordNews} />
        </div>
        <Divider orientation="vertical" flexItem />
        <div className="main-bot-right">
          <div className="main-bot-right-wrapper">
            <h2 className="main-bot-right-h2">시간별 핫 키워드(Top 3)</h2>
            <br />
            <div className="main-bot-right-1">
              <KeywordChart />
            </div>
            <br />
            <h2 className="main-bot-right-h2">시간별 핫 키워드(Top 10)</h2>
            <br />
            <div className="main-bot-right-2">
              <SearchResultChart2 keyword={category} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MainChart;
