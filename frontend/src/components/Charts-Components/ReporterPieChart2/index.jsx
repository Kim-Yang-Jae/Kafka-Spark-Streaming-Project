import React from "react";
import ReactApexChart from "react-apexcharts";

const ReporterPieChart2 = ({ genderCheck }) => {
  const colors = ["#0d6efd", "#d63384"];
  const state = {
    series: genderCheck,
    options: {
      chart: {
        width: 380,
        type: "donut",
        offsetY: 50,
      },
      colors: colors,
      plotOptions: {
        pie: {
          startAngle: -90,
          endAngle: 270,
        },
      },
      dataLabels: {
        enabled: false,
      },
      fill: {
        type: "gradient",
      },
      legend: {
        position: "bottom",
        offsetX: -15,
        offsetY: 100,
        floating: true,
      },
      labels: ["남성", "여성"],
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
      <ReactApexChart
        options={state.options}
        series={state.series}
        type="donut"
      />
    </div>
  );
};

export default ReporterPieChart2;
