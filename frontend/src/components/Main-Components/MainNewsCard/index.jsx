import React, { useState } from "react";
import "./MainNewsCard.scss";

// MUI
import Card from "@mui/joy/Card";
import Chip from "@mui/joy/Chip";
import AspectRatio from "@mui/joy/AspectRatio";
import { Divider } from "@mui/material";

// API
import NewsCardModal from "../../NewsCardModal";

const MainNewsCard = ({ newsData }) => {
  const [newsModalOpen, setNewsModalOpen] = useState(false);
  const [selectedNews, setSelectedNews] = useState(null);

  const handleCardClick = (news) => {
    // console.log(news)
    setSelectedNews(news);
    setNewsModalOpen(true);
  };

  const handleCloseModal = () => {
    setNewsModalOpen(false);
    setSelectedNews(null);
  };

  if (!newsData) return null; // add conditional check
  const err = "요약정보가 없습니다!";
  return (
    <div className="news-container" >
      {newsData.map((V, index) => {
        return (
          <Card
            key={index}
            variant="outlined"
            orientation="horizontal"
            onClick={() => handleCardClick(V)}
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
              {V.thumbnail ? (
                <img src={V.thumbnail} loading="lazy" alt="" />
              ) : null}
            </AspectRatio>
            <Divider orientation="vertical" flexItem />
            <div className="newscard-wrapper" style={{ width: "80%" }}>
              <div className="newscard-title">
                <h3 className="newscard-title-text" 
                style={{ fontFamily: 'Jua, sans-serif'}}>{V.title}</h3>
                <Divider />
              </div>
              <div className="newscard-main">
                <h4 className="newscard-main-text" style={{ fontFamily: 'Do Hyeon, sans-serif' }}>
                  {V.summary ? V.summary : err}
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
                  {V.reporter}
                </Chip>
              </div>
            </div>
          </Card>
        );
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

export default MainNewsCard;
