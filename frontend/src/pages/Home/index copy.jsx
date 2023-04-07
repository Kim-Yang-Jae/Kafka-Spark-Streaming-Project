import React, { useEffect, useRef } from "react";
import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

import "./home.scss";


const Home = () => {
  const navigate = useNavigate();

  let maxScrollValue;
  const houseElem = useRef(null);
  const progressBarElem = useRef(null);

  function scrollHandler() {
    const scrollPer = window.pageYOffset / maxScrollValue;
    const zMove = scrollPer * 950 - 500;
    houseElem.current.style.transform = "translateZ(" + zMove + "vw)";
    progressBarElem.current.style.width = scrollPer * 100 + "%";
  }

  function resizeHandler() {
    maxScrollValue = document.documentElement.scrollHeight - window.innerHeight;
  }

  useEffect(() => {
    window.addEventListener("scroll", scrollHandler);
    window.addEventListener("resize", resizeHandler);
    resizeHandler();
    return () => {
      window.removeEventListener("scroll", scrollHandler);
      window.removeEventListener("resize", resizeHandler);
    };
  }, []);

  return (
    <div className="html">
      <div className="body">
        <div className="world">
          <div className="progress">
            <div ref={progressBarElem} className="progress__bar"></div>
          </div>
          <div className="stage">
            <div ref={houseElem} className="house">
              <div className="house__wall house__wall--left">
                <h1 className="made-by-tlens">
                  Made By T:LENS(C206)
                </h1>
              </div>
              <div className="house__wall house__wall--right">
                <img src="img/C206.png" alt="c206" className="c206-image" />
              </div>
              <div className="house__wall house__wall--front house__wall--front-a">
                <div className="house__contents">
                  {/* <h3 className="house__contents-title">Hello!</h3> */}
                  <img src="img/Tlens.gif" alt="" style={{ height: "28vh" }} />
                  <p className="description">
                    T:LENS의 자세한 이야기가 궁금하신가요? 스크롤을 내리셔서 확인해보세요!!
                  </p>
                  <div className="enter-button">
                    <Button
                      type="submit"
                      variant="contained"
                      color="primary"
                      sx={{ mt: 2 }}
                      onClick={() => navigate("/main")}
                    >
                      T:LENS 즉시 이용하기
                    </Button>
                  </div>
                </div>
              </div>
              <div className="house__wall house__wall--front house__wall--front-b">
                <div className="house__contents">
                  <h3 className="house__contents-title">안녕하세요! T:LENS 입니다.</h3>
                </div>
              </div>
              <div className="house__wall house__wall--front house__wall--front-c">
                <div className="house__contents">
                  <h3 className="house__contents-title">기사 작성 패턴 분석!</h3>
                </div>
              </div>
              <div className="house__wall house__wall--front house__wall--front-d">
                <div className="house__contents">
                  <h3 className="house__contents-title">가치 중립적 정보 제공!</h3>
                </div>
              </div>
              <div className="house__wall house__wall--front house__wall--front-e">
                <div className="house__contents">
                  <h3 className="house__contents-title">뉴스 트랜드 분석 및 시각화!</h3>
                </div>
              </div>
              <div className="house__wall house__wall--front house__wall--front-f">
                <div className="house__component" style={{ width: "100%" }}>
                  <Button
                    type="submit"
                    variant="contained"
                    color="primary"
                    sx={{ mt: 2 }}
                    onClick={() => navigate("/main")}
                  >
                    T:LENS 즉시 이용하기
                  </Button>
                  <Button
                    type="submit"
                    variant="contained"
                    color="primary"
                    sx={{ mt: 2 }}
                    onClick={() => navigate("/auth")}
                  >
                    T:LENS 로그인 하기
                  </Button>
                </div>
              </div>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
