import React, { useState, useEffect, useRef } from "react";
import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import "./style.scss";

const Page3 = () => {
  const navigate = useNavigate();
  const Authorization = localStorage.getItem("Authorization");
  const isLoggedIn = Authorization !== null;

  const [isVisible, setIsVisible] = useState(false);
  const targetRef = useRef(null);

  useEffect(() => {
    const observer = new IntersectionObserver(
      ([entry]) => {
        setIsVisible(entry.isIntersecting);
      },
      {
        root: null,
        rootMargin: "0px",
        threshold: 0.5,
      }
    );

    if (targetRef.current) {
      observer.observe(targetRef.current);
    }

    return () => {
      observer.disconnect();
    };
  }, []);

  return (
    <div className="page3-container">
      <div>
        <p
          className="description"
          style={{
            fontFamily: "Jua, sans-serif",
            fontSize: "40px",
            opacity: isVisible ? 1 : 0,
            transition: "opacity 2s",
          }}
        >
          트렌즈의 서비스에 관심이 생기셨나요? 지금 당장 이용해보세요
        </p>
      </div>
      <div>
        <p
          className="description"
          style={{
            fontFamily: "Jua, sans-serif",
            fontSize: "40px",
            opacity: isVisible ? 1 : 0,
            transition: "opacity 2s 1s", // 1초 뒤에 페이드 인
          }}
        >
          당신을 위한 가장 <span style={{ color: "#0066cc" }}>담백한</span>{" "}
          뉴스 트렌드 사이트
        </p>
      </div>
      <div ref={targetRef}>
        <img
          src="img/logo.png"
          className="description"
          style={{
            fontFamily: "Jua, sans-serif",
            height: "100px",
            opacity: isVisible ? 1 : 0,
            transition: "opacity 2s 2s", // 2초 뒤에 페이드 인
          }}
          alt=""
        />
      </div>
      <div className="button-set">
        <Button
          style={{ fontFamily: "Jua, sans-serif" }}
          type="submit"
          variant="contained"
          color="primary"
          sx={{ mt: 2 }}
          onClick={() => navigate("/main")}
        >
          T:LENS 바로 이용해보기
        </Button>
        {isLoggedIn ? (
          <div></div>
        ) : (
          <Button
            style={{ fontFamily: "Jua, sans-serif", marginLeft: "1rem" }}
            type="submit"
            variant="contained"
            color="primary"
            sx={{ mt: 2 }}
            onClick={() => navigate("/auth")}
          >
            로그인 / 회원가입 하기
          </Button>
        )}
      </div>
    </div>
  );
};

export default Page3;
