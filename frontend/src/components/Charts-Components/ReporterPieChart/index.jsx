import React from "react";
import ApexCharts from "react-apexcharts";

const ReporterPieChart = ({ categoryCount }) => {
  const state = {
    series: categoryCount.count ? categoryCount.count : [1, 1],
    options: {
      chart: {
        width: 380,
        type: "pie",
      },
      labels: categoryCount.text
        ? categoryCount.text
        : ["기사없음", "기사없음"],
      responsive: [
        {
          breakpoint: 480,
          options: {
            chart: {
              width: 200,
            },
            legend: {
              position: "bottom",
            },
          },
        },
      ],
    },
  };
  return (
    <div>
      <ApexCharts options={state.options} series={state.series} type="pie" />
    </div>
  );
};

export default ReporterPieChart;
