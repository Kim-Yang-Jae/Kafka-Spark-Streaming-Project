import "./sub-ReportList.scss";
import React, { useEffect, useState } from "react";
import AspectRatio from "@mui/joy/AspectRatio";
import Link from "@mui/joy/Link";
import Card from "@mui/joy/Card";
import Typography from "@mui/joy/Typography";
import { useNavigate } from "react-router-dom";

// API
import { getSubscribe } from "../../../../apis/news";

const SubReportList = ({ userInfo }) => {
  const navigate = useNavigate();
  const [selectedJournalist, setSelectedJournalist] = useState(null);

  const [subReporter, setsubReporter] = useState([]);

  useEffect(() => {
    const fetchReporterList = async () => {
      try {
        const response = await getSubscribe();
        setsubReporter(response.lst);
      } catch (error) {
        console.error(error);
      }
    };
    fetchReporterList();
  }, []);

  return (
    <div>
      <h4>내가 구독한 기자 : (총 {subReporter.length} 명)</h4>
      <div style={{ maxHeight: "430px", overflowY: "scroll" }}>
        {" "}
        {/* 카드 부분 스크롤 적용 */}
        {subReporter.map((reporter, index) => {
          const handlenavigate = (reporter) => {
            navigate(`/reporter/${reporter.name}`, {
              state: {
                data: reporter,
                thumbnail: reporter.press,
                reporter: reporter, // 추가
              },
            });
          };
          return (
            <div key={index}>
              <Card
                onClick={() => {
                  handlenavigate(reporter);
                }}
                className="reporter-card"
                variant="outlined"
                orientation="horizontal"
                sx={{
                  marginTop: "15px",
                  width: "90%",
                  gap: 2,
                  marginInline: "3%",
                  "&:hover": {
                    boxShadow: "md",
                    borderColor: "neutral.outlinedHoverBorder",
                  },
                }}
                style={{ overflow: "hidden" }} // 카드 부분 고정 적용
              >
                {reporter.thumbnail ? (
                  <img
                    src={reporter.thumbnail}
                    loading="lazy"
                    alt=""
                    style={{ borderRadius: "100%", width: "80px" }}
                  />
                ) : (
                  <img
                    src="/images/thumbnail.png"
                    alt="company image"
                    style={{ borderRadius: "100%", height: "80px" }}
                  />
                )}
                <div>
                  <div>
                    <img
                      src={reporter.pressThumbnail}
                      alt="company image"
                      style={{ width: "80px", height: "25px" }}
                    />
                  </div>
                  <Typography
                    level="h5"
                    fontWeight="bold"
                    fontSize="lg"
                    id="card-description"
                    mb={0.5}
                  >
                    "{reporter.name}"
                  </Typography>
                </div>
              </Card>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default SubReportList;
