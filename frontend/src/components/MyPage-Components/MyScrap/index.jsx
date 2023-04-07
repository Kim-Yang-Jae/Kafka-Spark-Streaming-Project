import { Divider } from "@mui/material";
import "./myScrap.scss";

import DonutChart from "./DonutChart";
import ArticleScrap from "./ArticleScrap";
import { getMyPageScrapCount } from "../../../apis/api/axiosinstance";
import { useEffect, useState } from "react";

const MyScrap = ({ userInfo }) => {
  const [chartData, setChartData] = useState([]);
  const getScrapCount = async () => {
    try {
      const res = await getMyPageScrapCount();
      const data = [];
      res.map((V) => {
        const save = { value: V.cnt, label: V.name };
        data.push(save);
      });
      setChartData(data);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getScrapCount();
  }, [userInfo]);

  return (
    <div className="myscrap" style={{ "font-family": "Jua, sans-serif" }}>
      <div className="basic-Info">
        <h2>안녕하세요! {userInfo.nickname} 님.</h2>
        <Divider />
      </div>
      <div className="scrap-chart">
        <h2>"{userInfo.nickname}"의 스크랩 현황</h2>
        <DonutChart chartData={chartData} />
      </div>
      <div className="scrap-article">
        <ArticleScrap userInfo={userInfo} />
      </div>
      <Divider />
    </div>
  );
};
export default MyScrap;
