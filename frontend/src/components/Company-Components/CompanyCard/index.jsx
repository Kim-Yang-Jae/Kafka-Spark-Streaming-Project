import * as React from "react";
import { useNavigate } from "react-router-dom";
import "./CompanyCard.scss";

// MUI
import Card from "@mui/joy/Card";
import CardOverflow from "@mui/joy/CardOverflow";
import Divider from "@mui/joy/Divider";
import Typography from "@mui/joy/Typography";
import Link from "@mui/joy/Link";

const CompanyCard = ({ name, ename, category, ceo, birth, index }) => {
  const navigate = useNavigate();
  const handleCompanyDetail = (name) => {
    navigate(`/company/${name}`, {
      state: {
        category: category,
        ceo: ceo,
        birth: birth,
        index: index,
        ename: ename,
      },
    });
  };
  return (
    <div>
      <Card className="company-card" variant="outlined">
        <CardOverflow>
          <img
            style={{ marginTop: "10%" }}
            src={`/img/${index}.jpg`}
            alt=""
            width="150"
            height="80"
          />
        </CardOverflow>
        <Typography level="h2" sx={{ fontSize: "md" }} style={{ fontFamily: 'Do Hyeon, sans-serif' }}>
          <Link
            sx={{ color: "black" }}
            onClick={() => {
              handleCompanyDetail(name, category, ceo, birth, ename);
            }}
            overlay
            underline="none"
          >
            {name}
          </Link>
        </Typography>
        <Typography level="body2" sx={{ mt: 0.5, mb: 2 }} style={{ fontFamily: 'Do Hyeon, sans-serif' }}>
          <Link
            sx={{ color: "black" }}
            onClick={() => {
              handleCompanyDetail(name, category, ceo, birth, ename);
            }}
          >
            {category}
          </Link>
        </Typography>
        <Divider inset="context" />
        <CardOverflow className="company-card-overflow" variant="soft">
          <Typography level="body3" style={{ fontFamily: 'Do Hyeon, sans-serif' }}>대표 : {ceo}</Typography>
        </CardOverflow>
      </Card>
    </div>
  );
};

export default CompanyCard;
