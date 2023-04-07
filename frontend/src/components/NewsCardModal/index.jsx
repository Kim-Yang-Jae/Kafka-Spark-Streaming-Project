import React, { useEffect, useState } from "react";
import "./NewsCardModal.scss";
import Dialog from "@mui/material/Dialog";
import { Button, Divider } from "@mui/material";
import { ToastContainer } from "react-toastify";

// API
import {scrapStatus, scrapArticle, cancelScrap} from "../../apis/news"

const NewsCardModal = ({ open, onClose, news }) => {
  
  // 로그인 중이고
  const isLogined = localStorage.getItem("Authorization");
  // 스크랩 중이면
  const [newsScrap, setNewsScrap] = useState(null);

  const handleScrap = () => {
    scrapArticle(news.newsId);
    console.log("스크랩")
    setNewsScrap(true);
  };

  const handleCancel = () =>{
    cancelScrap(news.newsId);
    console.log("제거")
    setNewsScrap(false);
    
  }
  useEffect(() => {
    async function checkScrapStatus() {
      const status = await scrapStatus(news.newsId);
      setNewsScrap(status.data.content);

    }
    checkScrapStatus();
  }, []);


  if (!news) return null; // add conditional check

  return (
    <Dialog open={open} onClose={onClose}>
      <ToastContainer />
      <div className="modal-container" style={{ "font-family": 'Jua, sans-serif'}}>
        <div className="modal-top">
          <div className="modal-image">
            <img
              src={news.thumbnail}
              alt=""
              style={{
                width: "150px",
                height: "120px",
                borderRadius: "15px",
              }}
            />
          </div>
          <div className="modal-content">
            <p className="modal-body-category">{news.category}</p>
            <h4 className="modal-header-title">{news.title}</h4>
            <div
              className="modal-content"
              style={{ textAlign: "right" }}
            >
              <p className="modal-body-reporter">{news.reporter}</p>
            </div>
            {/* <p className="modal-body-date">{news.summary}</p> */}
          </div>
        </div>
        <Divider sx={{ my: 2, borderWidth: 2 }} />
        <div
          className="modal-bottom"
          style={{
            display: "flex",
            justifyContent: "flex-end",
            marginRight: "15px",
          }}
        >
          {isLogined ? (
            <Button onClick={newsScrap ? handleCancel : handleScrap} color="primary" variant="contained" style={{ "font-family": 'Jua, sans-serif'}}>
              {newsScrap ? "기사 스크랩 취소" : "T:LENS 기사 스크랩"}
            </Button>
          ) : null}
          
          <Button
            style={{ "font-family": 'Jua, sans-serif'}}
            color="primary"
            variant="contained"
            onClick={() => window.open(news.link)}
          >
            기사 내용 확인하기
          </Button>
        </div>
      </div>
    </Dialog>
  );
};

export default NewsCardModal;
