import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import Dots from "../../components/User-Components/Dots";
import "./home.scss";

import Page1 from "./page1";
import Page2 from "./page2";
import Page3 from "./page3";

import { Button } from "@mui/material";

const DIVIDER_HEIGHT = 5;

const Home = () => {
  const navigate = useNavigate();

  const outerDivRef = useRef();
  const [scrollIndex, setScrollIndex] = useState(1);

  useEffect(() => {
    const wheelHandler = (e) => {
      e.preventDefault();
      const { deltaY } = e;
      const { scrollTop } = outerDivRef.current;
      const pageHeight = window.innerHeight;

      if (deltaY > 0) {
        if (scrollTop >= 0 && scrollTop < pageHeight) {
          outerDivRef.current.scrollTo({
            top: pageHeight + DIVIDER_HEIGHT,
            left: 0,
            behavior: "smooth",
          });
          setScrollIndex(2);
        } else if (scrollTop >= pageHeight && scrollTop < pageHeight * 2) {
          outerDivRef.current.scrollTo({
            top: pageHeight * 2 + DIVIDER_HEIGHT * 2,
            left: 0,
            behavior: "smooth",
          });
          setScrollIndex(3);
        } else if (scrollTop >= pageHeight * 2 && scrollTop < pageHeight * 3) {
          outerDivRef.current.scrollTo({
            top: pageHeight * 3 + DIVIDER_HEIGHT * 3,
            left: 0,
            behavior: "smooth",
          });
          setScrollIndex(4);
        } else {
          setScrollIndex(4);
        }
      } else {
        if (scrollTop >= 0 && scrollTop < pageHeight) {
          outerDivRef.current.scrollTo({
            top: 0,
            left: 0,
            behavior: "smooth",
          });
          setScrollIndex(1);
        } else if (scrollTop >= pageHeight && scrollTop < pageHeight * 2) {
          outerDivRef.current.scrollTo({
            top: 0,
            left: 0,
            behavior: "smooth",
          });
          setScrollIndex(1);
        } else if (scrollTop >= pageHeight * 2 && scrollTop < pageHeight * 3) {
          outerDivRef.current.scrollTo({
            top: pageHeight + DIVIDER_HEIGHT,
            left: 0,
            behavior: "smooth",
          });
          setScrollIndex(2);
        } else {
          outerDivRef.current.scrollTo({
            top: pageHeight * 2 + DIVIDER_HEIGHT * 2,
            left: 0,
            behavior: "smooth",
          });
          setScrollIndex(3);
        }
      }
    };
    const outerDivRefCurrent = outerDivRef.current;
    outerDivRefCurrent.addEventListener("wheel", wheelHandler);
    return () => {
      outerDivRefCurrent.removeEventListener("wheel", wheelHandler);
    };
  }, []);
  return (
    <div ref={outerDivRef} className="outer">
      <Dots scrollIndex={scrollIndex} />
      <div className="first-page">
        <img src="img/Tlens.gif" alt="" style={{ height: "28vh" }} />
        <p className="description" style={{ "font-family": 'Jua, sans-serif', fontSize:"40px" }}>
          T:LENS의 자세한 이야기가 궁금하신가요? 스크롤을 내리면서 확인해보세요!!
        </p>
        <div className="enter-button">
          <Button
            style={{ "font-family": 'Jua, sans-serif'}}
            type="submit"
            variant="contained"
            color="primary"
            sx={{ mt: 2 }}
            onClick={() => navigate("/main")}
          >
            T:LENS 바로 이용해보기
          </Button>
        </div>
      </div>

      <div className="inner-page">
        <div className="page-background">
          <Page1 />
        </div>
      </div>
      <div className="inner-page">
        <p className="description" style={{ "font-family": 'Jua, sans-serif', fontSize:"30px" }}>
          <Page2 />
        </p>
      </div>
      <div className="inner-page">
        <Page3 />
      </div>
    </div>
  );
}


export default Home