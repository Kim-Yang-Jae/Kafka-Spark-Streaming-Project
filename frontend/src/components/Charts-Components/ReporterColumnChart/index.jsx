import React from "react";
import ReactApexChart from "react-apexcharts";

const ReporterColumnChart = ({ ageCnt }) => {
  const colors = [
    "#0d6efd",
    "#6610f2",
    "#6f42c1",
    "#d63384",
    "#dc3545",
    "#fd7e14",
    "#ffc107",
    "#198754",
  ];

  const state = {
    series: [
      {
        data: ageCnt,
      },
    ],
    options: {
      chart: {
        height: 250,
        type: "bar",
        events: {
          click: function (chart, w, e) {
            // console.log(chart, w, e)
          },
        },
        toolbar: {
          show: false,
        },
      },
      colors: colors,
      plotOptions: {
        bar: {
          columnWidth: "25%",
          distributed: true,
        },
      },
      dataLabels: {
        enabled: false,
      },
      legend: {
        show: false,
      },
      xaxis: {
        categories: [
          ["0~10"],
          ["10~20"],
          ["20~30"],
          ["30~40"],
          ["40~50"],
          ["50~60"],
          ["60~70"],
          ["70~80"],
          ["80~90"],
        ],
        labels: {
          style: {
            colors: colors,
            fontSize: "10px",
          },
        },
      },
    },
  };
  return (
    <div>
      <ReactApexChart
        options={state.options}
        series={state.series}
        type="bar"
        height={300}
      />
    </div>
  );
};

export default ReporterColumnChart;
