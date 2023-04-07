import React, { useState, useEffect, useRef } from "react";
import { useParams, useLocation } from "react-router-dom";
import MainNewsCard from "../../Main-Components/MainNewsCard";
import "./CompanyDetail.scss";
import { getKeywordNews } from "../../../apis/api/axiosinstance";

// Charts
import CompanyStock from "../../Charts-Components/CompanyStock";
import SearchResultChart2 from "../../Charts-Components/SearchResultChart2";
import CompanyKeyword from "../../Charts-Components/CompanyKeyword";
import WordCloud from "../../Charts-Components/WordCloud";
import HotKeywordChart from "../../Charts-Components/HotKeywordChart";
import dummy from "./dummy.json";

// MUI
import Divider from "@mui/material/Divider";
import { Button } from "@mui/material";

const CompanyDetail = () => {
  const { name } = useParams();
  const { state } = useLocation();
  const [keyName, setKeyName] = useState("");
  const [loading, setLoading] = useState(false);
  const [newsData, setNewsData] = useState([]);
  const [day, setDay] = useState(750);
  const pageSize = 10;
  const mainBotLeftRef = useRef(null);

  const getNews = async () => {
    try {
      const res = await getKeywordNews(name, 0, 10);
      setNewsData(res);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    if (name === "현대자동차") {
      setKeyName("현대차");
    } else if (name === "GS칼텍스") {
      setKeyName("GS");
    } else if (name === "POSCO홀딩스") {
      setKeyName("포스코케미칼");
    } else if (name === "LG전자") {
      setKeyName("LG");
    } else if (name === "기아자동차") {
      setKeyName("기아");
    } else if (name === "한국전력공사") {
      setKeyName("한국전력");
    } else {
      setKeyName(name);
    }
    setTimeout(() => {
      setLoading(true);
    }, 1);
    getNews();
  }, [name]);

  const handleScroll = async () => {
    const el = mainBotLeftRef.current;
    if (el.scrollTop + el.clientHeight + 1 >= el.scrollHeight) {
      const res3 = await getKeywordNews(
        name,
        Math.ceil(newsData.length / pageSize),
        pageSize
      );
      setNewsData((prevData) => [...prevData, ...res3]);
    }
  };

  useEffect(() => {
    const el = mainBotLeftRef.current;
    el.addEventListener("scroll", handleScroll);
    return () => {
      el.removeEventListener("scroll", handleScroll);
    };
  }, [newsData.length]);

  return (
    <div className="companydetail-wrapper">
      <h1>T:LENS 기업 분석 : {name}</h1>
      <div className="companydetail-container">
        <div className="companydetail-top">
          <div className="companydetail-top-left">
            <div className="companydetail-top-left-1">
              <div className="companydetail-top-left-2">
                <img
                  className="companydetail-top-left-img"
                  src={`/img/${state.index}.jpg`}
                  alt=""
                />
              </div>
              <Divider orientation="vertical" flexItem />
              <div className="companydetail-top-left-3">
                <h1>{name}</h1>
                <h3>{state.ename}</h3>
                <Divider sx={{ marginLeft: "-5%" }} />
                <div className="companydetail-top-left-4">
                  <div style={{ width: "20%" }}>
                    <h4>업종 : </h4>
                    <h4>설립일 : </h4>
                    <h4>대표 : </h4>
                  </div>
                  <div style={{ width: "80%" }}>
                    <h4>{state.category}</h4>
                    <h4>{state.birth}</h4>
                    <h4>{state.ceo}</h4>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <Divider orientation="vertical" />
          <div className="companydetail-top-right">
            <h2 style={{ marginLeft: "10px" }}>{name} 주가 그래프</h2>
            <div style={{ display: "flex", marginLeft: "5%" }}>
              <Button
                onClick={() => {
                  setDay(61);
                }}
              >
                3개월
              </Button>
              <Button
                onClick={() => {
                  setDay(750);
                }}
              >
                1년
              </Button>
            </div>
            {loading && <CompanyStock keyName={keyName} day={day} />}
          </div>
        </div>
        {/* <div className="companydetail-mid">
          <div className="companydetail-mid-left">
            <h2 style={{ marginLeft: "4%" }}>키워드 관계도 : {name}</h2>
            <div className="companydetail-mid-left-1">
              <SearchResultChart2 keyword={name} />
            </div>
          </div>
          <Divider />
          <div className="companydetail-mid-right">
            <h2 style={{ marginLeft: "4%" }}>키워드 통계 : {name} </h2>
            <div className="companydetail-mid-right-1">
              <CompanyKeyword />
            </div>
          </div>
        </div> */}
      </div>
      <div className="companydetail-bot-wrapper">
        <h2>T:LENS 키워드 분석 : {name}</h2>
        <Divider />
        <div className="companydetail-bot" style={{ display: "flex" }}>
          <div className="companydetail-bot-news" ref={mainBotLeftRef}>
            <MainNewsCard newsData={newsData} />
          </div>
          <Divider orientation="vertical" flexItem />
          <div className="companydetail-bot-right">
            <br />
            <h3 className="companydetail-bot-right-h3">
              {name} 년도 별 기사 데이터 건수
            </h3>
            <div className="companydetail-bot-right2">
              <HotKeywordChart />
            </div>
            <h3 className="companydetail-bot-right2-h3">
              {name} 키워드 (Top30)
            </h3>
            <div className="companydetail-bot-right3">
              <WordCloud dummy2={dummy} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CompanyDetail;
