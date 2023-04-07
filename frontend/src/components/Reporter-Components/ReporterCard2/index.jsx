import * as React from "react";
import { useNavigate } from "react-router-dom";

// MUI
import Link from "@mui/joy/Link";
import Card from "@mui/joy/Card";
import Typography from "@mui/joy/Typography";
import Divider from "@mui/material/Divider";

const ReporterCard2 = ({ V, pressData }) => {
  const navigate = useNavigate();

  const handlenavigate = (value) => {
    navigate(`/reporter/${value.name}`, {
      state: {
        data: value,
        thumbnail: pressData.thumbnail,
      },
    });
  };

  return (
    <div style={{ margin: "1%", display: "flex" }}>
      <Card
        onClick={() => {
          handlenavigate(V);
        }}
        variant="outlined"
        orientation="horizontal"
        sx={{
          width: "100%",
          display: "flex",
          width: 340,
          gap: 2,
          "&:hover": {
            boxShadow: "md",
            borderColor: "neutral.outlinedHoverBorder",
          },
        }}
      >
        <div>
          <img
            style={{ width: "100px", height: "110px", borderRadius: "20px" }}
            src={V.thumbnail ? V.thumbnail : `/images/thumbnail.png`}
            loading="lazy"
            alt=""
          />
        </div>
        <Divider orientation="vertical" flexItem />
        <div>
          <img style={{ width: "30%" }} src={pressData.thumbnail} alt="" />
          <Typography level="h2" fontSize="lg" id="card-description" mb={0.5}>
            {V[2]}
          </Typography>
          <Typography fontSize="sm" aria-describedby="card-description" mb={1}>
            <Link overlay underline="none" sx={{ color: "text.tertiary" }} style={{ "font-family": 'Jua, sans-serif'}}>
              {V.press} : {V.name}
            </Link>
          </Typography>
          <Typography fontSize="sm" aria-describedby="card-description" mb={1} style={{ fontFamily: 'Do Hyeon, sans-serif' , color: "#0066cc"}}>
            클릭시 정보 확인
          </Typography>
        </div>
      </Card>
    </div>
  );
};

export default ReporterCard2;
