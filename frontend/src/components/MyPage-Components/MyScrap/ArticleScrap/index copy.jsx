import { useState, useEffect } from "react";
import AspectRatio from "@mui/joy/AspectRatio";
import Card from "@mui/joy/Card";
import Chip from "@mui/joy/Chip";
import NewsCardModal from "../../../NewsCardModal"
import { Divider, Button } from "@material-ui/core";
import "./articleScrap.scss"


// API
import { scrapList, cancelScrap } from "../../../../apis/news"

const ArticleScrap = ({ userInfo }) => {

  const [scrapNews, setScrapNews] = useState([])
  
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

  useEffect(() => {
    const fetchScrapList = async () => {
      try {
        const response = await scrapList();
        setScrapNews(response.lst);
      } catch (error) {
        console.error(error);
      }
    };
    fetchScrapList();
  }, []);

  const handleDelete = async (id) => {
    try {
      await cancelScrap(id);
      setScrapNews(prevScrapNews => prevScrapNews.filter(news => news.newsId !== id));
      // 삭제 성공 시 화면 갱신 등 추가 작업 수행
    } catch (error) {
      console.log(error);
    }
  };
  if (!scrapNews) return null; // add conditional check
  const err = "요약정보가 없습니다!";

  return (
    <div >
      <h2>"{userInfo.nickname}"의 스크랩 기사 : (총 {scrapNews.length} 건)</h2>
      {scrapNews.map((news) => {
        return (
          <div className="scrap-card">
            <Card
              key={news.newsId}
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
                  <h3 className="newscard-title-text">{news.title}</h3>
                  <Divider />
                </div>
                <div className="newscard-main">
                  <h4 className="newscard-main-text">
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
            <Button
              sx={{
                fontSize: "15px",
                bgcolor: "red", // 빨간색으로 변경
                color: "white",
                height: "60px",
                width: "20px",
              }}
              onClick={() => handleDelete(news.newsId)}>
              X
            </Button>
          </div>
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

export default ArticleScrap;
