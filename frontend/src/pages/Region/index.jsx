import React, { useEffect, useState, useRef } from "react";
import { Box, Divider } from "@mui/material";
import KoreaMap from "../../components/Region-Components/KoreaMap";
import MainNews from "../../components/Main-Components/MainNewsCard";

import { getRegion, getRegionNews } from "../../apis/api/axiosinstance";

import "./Region.scss";

const Region = () => {
  const [selectedRegion, setSelectedRegion] = useState(null);
  const [regionData, setRegionData] = useState([]);
  const [newsData, setNewsData] = useState([]);
  const mainBotLeftRef = useRef(null);
  const page = 0;
  const pageSize = 10;

  const handleRegionSelect = (name) => {
    setSelectedRegion(name);
  };

  const getRegionData = async () => {
    const res = await getRegion();
    setRegionData(res);
  };

  useEffect(() => {
    getRegionData();
  }, []);

  const fetchRegionNewsData = async (selectedRegion, page, pageSize) => {
    let regionName = selectedRegion;

    if (selectedRegion === "경상북도") {
      regionName = "대구광역시";
    } else if (selectedRegion === "전라남도") {
      regionName = "광주광역시";
    } else if (
      selectedRegion === "세종특별시" ||
      selectedRegion === "충청남도"
    ) {
      regionName = "대전광역시";
    } else if (
      selectedRegion === "인천광역시" ||
      selectedRegion === "서울특별시"
    ) {
      regionName = "경기도";
    } else if (
      selectedRegion === "경상남도" ||
      selectedRegion === "울산광역시"
    ) {
      regionName = "부산광역시";
    }

    const res = await getRegionNews(regionName, page, pageSize);
    if (page === 0) {
      setNewsData(res);
    } else {
      setNewsData((prevData) => [...prevData, ...res]);
    }
  };

  useEffect(() => {
    if (selectedRegion !== null) {
      fetchRegionNewsData(selectedRegion, page, pageSize);
    }
    const el = mainBotLeftRef.current;
    el.scrollTop = 0;
  }, [selectedRegion]);

  const handleScroll = () => {
    const el = mainBotLeftRef.current;
    if (el.scrollTop + el.clientHeight + 1 >= el.scrollHeight) {
      fetchRegionNewsData(
        selectedRegion,
        Math.ceil(newsData.length / pageSize),
        pageSize
      );
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
    <div className="region-wrapper" style={{ fontFamily: "Jua, sans-serif" }}>
      <Box
        sx={{
          display: "flex",
          width: "100%",
          borderRadius: "20px",
          height: "130px",
          marginBottom: "20px",
          backgroundColor: "primary.dark",
          alignItems: "center",
          justifyContent: "center",
          "&:hover": {
            backgroundColor: "primary.main",
            opacity: [0.9, 0.8, 0.7],
          },
        }}
      >
        <div>
          <h2>T:LENS 지역별 키워드 분석</h2>
          <h2 style={{ color: "white" }}>
            지역별 트렌드 정보 및 관련기사를 확인하실 수 있습니다.
          </h2>
        </div>
      </Box>

      <div
        style={{
          width: "100%",
          display: "flex",
          justifyContent: "space-between",
        }}
      >
        <div style={{ width: "50%" }}>
          <KoreaMap
            onRegionSelect={handleRegionSelect}
            regionData={regionData}
          />
        </div>
        <Divider orientation="vertical" flexItem />
        <div
          className="wordcloud"
          style={{
            width: "50%",
            overflowY: "auto",
            height: "100vh",
            padding: "3% 0 3% 0",
          }}
          ref={mainBotLeftRef}
        >
          {newsData.length > 0 ? (
            <>
              {selectedRegion && (
                <h2 style={{ marginTop: -20 }}>
                  "{selectedRegion}" T:LENS 뉴스
                </h2>
              )}
              <MainNews newsData={newsData} />
            </>
          ) : (
            <h1 className="wordcloud-message">지역을 선택해주세요</h1>
          )}
        </div>
      </div>
    </div>
  );
};

export default Region;
