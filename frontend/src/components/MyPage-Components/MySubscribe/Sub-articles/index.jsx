import React, { useState, useEffect } from "react";

//MUI
import { Divider } from "@mui/material";
import AspectRatio from "@mui/joy/AspectRatio";
import Link from "@mui/joy/Link";
import Card from "@mui/joy/Card";
import Chip from "@mui/joy/Chip";
import Typography from "@mui/joy/Typography";
import { Button } from "@mui/material";
import data from "./articles.json";
import "./articleScrap.scss";
import NewsCardModal from "../../../NewsCardModal";

// API
import { getSubscribeNews } from "../../../../apis/news";

const ArticleScrap = ({ name }) => {
  const [scrapData, setScrapData] = useState([]);
  const [newsModalOpen, setNewsModalOpen] = useState(false);
  const [selectedNews, setSelectedNews] = useState(null);

  const handleCardClick = (news) => {
    setSelectedNews(news);
    setNewsModalOpen(true);
  };

  const handleCloseModal = () => {
    setNewsModalOpen(false);
    setSelectedNews(null);
  };

  useEffect(() => {
    const fetchScrapNews = async () => {
      try {
        const response = await getSubscribeNews();
        setScrapData(response.lst);
      } catch (error) {
        console.error(error);
      }
    };
    fetchScrapNews();
  }, []);

  if (!scrapData) return null;

  const err = "요약정보가 없습니다!";

  return (
    <div className="news-container">
      {scrapData.map((news, index) => {
        if (name === news.reporter) {
          return (
            <Card
              key={index}
              variant="outlined"
              orientation="horizontal"
              onClick={() => handleCardClick(news)}
              sx={{
                cursor: "pointer",
                margin: "auto",
                marginTop: "15px",
                width: "90%",
                gap: 2,
                "&:hover": {
                  boxShadow: "md",
                  borderColor: "neutral.outlinedHoverBorder",
                },
              }}
            >
              <AspectRatio ratio="1" sx={{ width: "15%" }}>
                {news.thumbnail ? (
                  <img src={news.thumbnail} loading="lazy" alt="" />
                ) : null}
              </AspectRatio>
              <Divider orientation="vertical" flexItem />
              <div className="newscard-wrapper" style={{ width: "80%" }}>
                <div className="newscard-title">
                  <h3 className="newscard-title-text" style={{ "font-family": 'Jua, sans-serif'}}>{news.title}</h3>
                  <Divider />
                </div>
                <div className="newscard-main">
                  <h4 className="newscard-main-text" style={{ fontFamily: 'Do Hyeon, sans-serif' }}>
                    {news.summary ? news.summary : err}
                  </h4>
                </div>
                <div className="newscard-reporter">
                  <Chip
                    variant="outlined"
                    color="primary"
                    size="sm"
                    sx={{
                      pointerEvents: "none",
                    }}
                  >
                    {news.reporter}
                  </Chip>
                </div>
              </div>
            </Card>
          );
        }
      })}
      {selectedNews && (
        <NewsCardModal
          onClose={handleCloseModal}
          news={selectedNews}
          open={newsModalOpen}
        />
      )}
    </div>
  );
};

export default ArticleScrap;
