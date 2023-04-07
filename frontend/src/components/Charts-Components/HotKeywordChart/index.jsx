import React from "react";
import ApexCharts from "react-apexcharts";

const HotKeywordChart = () => {
  return (
    <div>
      <ApexCharts
        type="radar"
        series={[
          {
            name: "삼성전자",
            data: [9123, 13342, 5219, 10290, 32109, 22174],
          },
        ]}
        options={{
          chart: {
            height: 350,
            type: "radar",
            toolbar: {
              show: false, // 메뉴 버튼 안보이게 설정
            },
            dropShadow: {
              enabled: true,
              blur: 1,
              left: 1,
              top: 1,
            },
          },
          stroke: {
            width: 2,
          },
          fill: {
            opacity: 0.1,
          },
          markers: {
            size: 0,
          },
          xaxis: {
            categories: ["2018", "2019", "2020", "2021", "2022", "2023"],
          },
        }}
      />
    </div>
  );
};

export default HotKeywordChart;
