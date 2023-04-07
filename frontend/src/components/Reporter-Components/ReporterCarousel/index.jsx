import React, { useEffect, useState, useRef } from "react";
import { TiChevronLeftOutline, TiChevronRightOutline } from "react-icons/ti";
import "./ReporterCarousel.scss";
import ReporterCard from "../ReporterCard";
import ReporterCard2 from "../ReporterCard2";
import { getPressData, getReporterData } from "../../../apis/api/axiosinstance";
import Footer from "../../../layout/Footer";

// MUI
import Divider from "@mui/material/Divider";

const MAX_VISIBILITY = 10;

const Carousel = ({ children, active, handlePrev, handleNext }) => {
  const count = React.Children.count(children);

  return (
    <div className="carousel">
      {active > 0 && (
        <button className="nav left" onClick={handlePrev}>
          <TiChevronLeftOutline />
        </button>
      )}
      {React.Children.map(children, (child, i) => (
        <div
          className="card-container"
          style={{
            "--active": i === active ? 1 : 0,
            "--offset": (active - i) / 3,
            "--direction": Math.sign(active - i),
            "--abs-offset": Math.abs(active - i) / 3,
            pointerEvents: active === i ? "auto" : "none",
            opacity: Math.abs(active - i) >= MAX_VISIBILITY ? "0" : "1",
            display: Math.abs(active - i) > MAX_VISIBILITY ? "none" : "block",
          }}
        >
          {child}
        </div>
      ))}
      {active < count - 1 && (
        <button className="nav right" onClick={handleNext}>
          <TiChevronRightOutline />
        </button>
      )}
    </div>
  );
};

const ReporterCarousel = () => {
  const [active, setActive] = useState(4);
  const [pressData, setPressData] = useState([]);
  const [reporterData, setReporterData] = useState([]);
  const [pressName, setPressName] = useState("");
  const [reporterCount, setReporterCount] = useState("");
  const mainBotLeftRef = useRef(null);

  const pageSize = 12;
  const page = 0;

  const handlePrev = () => setActive((i) => i - 1);
  const handleNext = () => setActive((i) => i + 1);

  const pressNames = [
    "경향신문",
    "서울신문",
    "한겨레",
    "국민일보",
    "세계일보",
    "한국일보",
    "동아일보",
    "조선일보",
    "문화일보",
    "중앙일보",
    "뉴스1",
    "채널A",
    "MBC",
    "TV조선",
    "뉴시스",
    "한국경제TV",
    "MBN",
    "YTN",
    "연합뉴스",
    "JTBC",
    "SBS",
    "연합뉴스TV",
    "KBS",
    "SBS Biz",
  ];

  const getPress = async () => {
    const data = await getPressData();
    const sliceData = data.slice(0, 24);
    setPressData(sliceData);
  };

  useEffect(() => {
    setPressName(pressNames[active]);
    getPress();
  }, [active]);

  const getReporter = async (pressName, page) => {
    try {
      const res = await getReporterData(pressName, page, pageSize);
      const res2 = await getReporterData(pressName, 0, 99999);
      setReporterCount(res2.length);
      if (page === 0) {
        setReporterData(res);
      } else {
        setReporterData((prevData) => [...prevData, ...res]);
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getReporter(pressName, page);
    const el = mainBotLeftRef.current;
    el.scrollTop = 0;
  }, [pressName, page]);

  const handleScroll = () => {
    const el = mainBotLeftRef.current;
    if (el.scrollTop + el.clientHeight + 1 >= el.scrollHeight) {
      getReporter(pressName, Math.ceil(reporterData.length / pageSize));
    }
  };

  useEffect(() => {
    const el = mainBotLeftRef.current;
    el.addEventListener("scroll", handleScroll);
    return () => {
      el.removeEventListener("scroll", handleScroll);
    };
  }, [reporterData.length]);

  return (
    <div style={{ height: 240 }}>
      <div className="reportercarousel-main">
        <Carousel
          active={active}
          handlePrev={handlePrev}
          handleNext={handleNext}
        >
          {pressData?.map((V, i) => (
            <ReporterCard key={i} V={V} />
          ))}
        </Carousel>
      </div>
      <br />
      <Divider sx={{ margin: "0 8% 0 8%" }} />
      <h2 style={{ textAlign: "left", marginLeft: "8%" }}>
        {reporterData[1]?.press} : {reporterCount}명
      </h2>
      <div className="reportercarousel-main-news" ref={mainBotLeftRef}>
        {reporterData ? (
          reporterData.map((V, index) => {
            return (
              <ReporterCard2 key={index} V={V} pressData={pressData[active]} />
            );
          })
        ) : (
          <h2>등록된 기자가 없습니다</h2>
        )}
      </div>
      <br />
      <div style={{ marginLeft: "-2%", marginRight: "-2%" }}>
        <Footer />
      </div>
    </div>
  );
};

export default ReporterCarousel;
