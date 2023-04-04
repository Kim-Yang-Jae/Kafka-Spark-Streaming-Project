import * as React from "react";
import "./MainNewsCard.scss";

// MUI
import Card from "@mui/joy/Card";
import Link from "@mui/joy/Link";
import Chip from "@mui/joy/Chip";
import AspectRatio from "@mui/joy/AspectRatio";
import { Divider } from "@mui/material";

const MainNewsCard = ({ newsData }) => {
  if (!newsData) return null; // add conditional check

  return (
    <div className="news-container">
      {newsData.map((V, index) => {
        return (
          <Card
            key={index}
            variant="outlined"
            orientation="horizontal"
            sx={{
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
                <h3 className="newscard-title-text">{V.title}</h3>
                <Divider />
              </div>
              <div className="newscard-main">
                <Link
                  overlay
                  underline="none"
                  href={V.link}
                  target="_blank"
                  sx={{
                    color: "text.tertiary",
                  }}
                ></Link>
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
    </div>
  );
};

export default MainNewsCard;
