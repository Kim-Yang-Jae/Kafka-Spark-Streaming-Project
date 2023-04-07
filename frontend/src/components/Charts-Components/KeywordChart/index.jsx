import React from "react";
import ApexCharts from "react-apexcharts";

const KeywordChart = () => {
  const state = {
    series: [
      {
        name: "삼성",
        data: [31, 40, 28, 51, 42, 109, 100],
      },
      {
        name: "싸피",
        data: [11, 32, 45, 32, 34, 52, 41],
      },
      {
        name: "삼피",
        data: [51, 5, 45, 23, 83, 11, 21],
      },
    ],
    options: {
      chart: {
        height: 350,
        type: "area",
        toolbar: {
          show: false, // 메뉴 버튼 안보이게 설정
        },
      },
      dataLabels: {
        enabled: false,
      },
      stroke: {
        curve: "smooth",
      },
      xaxis: {
        type: "datetime",
        categories: [
          "2018-09-19T00:00:00.000Z",
          "2018-09-19T01:30:00.000Z",
          "2018-09-19T02:30:00.000Z",
          "2018-09-19T03:30:00.000Z",
          "2018-09-19T04:30:00.000Z",
          "2018-09-19T05:30:00.000Z",
          "2018-09-19T06:30:00.000Z",
        ],
      },
      tooltip: {
        x: {
          format: "dd/MM/yy HH:mm",
        },
      },
    },
  };

  return (
    <div>
      <ApexCharts options={state.options} series={state.series} type="area" />
    </div>
  );
};

export default KeywordChart;
