import React, { useEffect, useState, useRef } from "react";
import ApexCharts from "apexcharts";

const DonutChart = ({ chartData }) => {
  const chartRef = useRef(null);

  useEffect(() => {
    if (chartData) {
      const series = chartData.map((data) => data.value);
      const labels = chartData.map((data) => data.label);

      const options = {
        series: series,
        chart: {
          width: 380,
          type: "donut",
        },
        labels: labels,
        colors: [
          "#FF4560",
          "#008FFB",
          "#FEB019",
          "#FFC100",
          "#4CAF50",
          "#2196F3",
          "#9C27B0",
          "#673AB7",
        ],
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
        plotOptions: {
          pie: {
            dataLabels: {
              offset: -30,
              style: {
                fontSize: "14px",
              },
            },
          },
        },
      };

      if (chartRef.current) {
        chartRef.current.destroy();
      }

      chartRef.current = new ApexCharts(
        document.querySelector("#chart"),
        options
      );

      chartRef.current.render();

      chartRef.current.addEventListener(
        "dataPointSelection",
        function (event, chartContext, config) {
          console.log(chartData[config.dataPointIndex]);
        }
      );
    }
  }, [chartData]);

  return <div id="chart"></div>;
};

export default DonutChart;
