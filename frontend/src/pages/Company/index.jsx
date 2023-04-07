import * as React from "react";
import "./Company.scss";
import CompanyCard from "../../components/Company-Components/CompanyCard";
import company from "./Company.json";

// MUI
import Box from "@mui/material/Box";

const Company = () => {
  return (
    <div className="company-main">
      <Box
        sx={{
          backgroundColor: "primary.dark",
          "&:hover": {
            backgroundColor: "primary.main",
            opacity: [0.9, 0.8, 0.7],
          },
        }}
      >
        <div>
          <h2>T:LENS 기업별 키워드 분석</h2>
          <h3>기업별 트렌드 정보 및 관련기사를 화인하실 수 있습니다.</h3>
        </div>
      </Box>
      <Box>
        {company.map((V, index) => {
          return (
            <CompanyCard
              key={index}
              name={V.name}
              ename={V.ename}
              category={V.category}
              ceo={V.ceo}
              birth={V.birth}
              index={index + 1}
            />
          );
        })}
      </Box>
    </div>
  );
};

export default Company;
