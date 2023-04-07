import React from "react";
import { Box, Card, Typography } from "@mui/material";
import newsData from "./newsData.json";
import { Link } from "react-router-dom";
const KeywordNews = ({ keyword, region }) => {
  const relatedNews = newsData.filter((news) => {
    if (news.keyword && keyword && news.region === region) {
      // keyword와 newsData의 keyword 배열이 존재하고 region도 일치할 때
      return news.keyword.includes(keyword);
    } else {
      return false;
    }
  });

  return (
    <Box>
      {relatedNews.map((news) => {
        return (
          <div className="region-news-card">
            <Card
              key={news.id}
              variant="outlined"
              sx={{
                margin: "auto",
                marginTop: "15px",
                width: "90%",
                height: "220px", // 카드 높이를 220px로 지정
                display: "flex",
                alignItems: "stretch",
                justifyContent: "flex-start",
                gap: 2,
                boxShadow: "md",
                transition: "all 0.3s ease",
                "&:hover": {
                  boxShadow: "lg",
                  borderColor: "neutral.outlinedHoverBorder",
                },
                cursor: "pointer",
              }}
              onClick={() => window.open(news.link)}
            >
              <div
                style={{
                  width: "40%",
                  height: "100%",
                  overflow: "hidden",
                  borderRadius: "4px",
                }}
              >
                <img
                  src={news.thumbnail}
                  alt=""
                  style={{
                    width: "100%",
                    height: "100%",
                    objectFit: "cover",
                  }}
                />
              </div>
              <div
                style={{
                  width: "60%",
                  height: "100%",
                  display: "flex",
                  flexDirection: "column",
                  justifyContent: "space-between",
                }}
              >
                <div style={{ marginTop: "10px" }}>
                  <Typography
                    level="h2"
                    fontSize="xl"
                    id="card-description"
                    mb={0.5}
                    style={{ fontWeight: "600" }}
                  >
                    {news.title}
                  </Typography>
                  <Typography fontSize="md" aria-describedby="card-description">
                    {news.description}
                  </Typography>
                </div>
                <Typography
                  fontSize="sm"
                  aria-describedby="card-description"
                  mb={1}
                  style={{ fontWeight: "600" }}
                >
                  {news.date}
                </Typography>
              </div>
            </Card>
          </div>
        );
      })}
    </Box>
  );
};

export default KeywordNews;
