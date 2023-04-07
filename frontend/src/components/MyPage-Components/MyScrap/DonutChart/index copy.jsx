import React, { useEffect, useState, useRef } from 'react';
import ApexCharts from 'apexcharts';
import {scrapCategory} from "../../../../apis/news"

const DonutChart = () => {
  const chartRef = useRef(null);  
  const [scrapItem, setScrapItem] = useState([])

  useEffect(() => {
    const fetchScrap = async () => {
      try {
        const response = await scrapCategory();
        setScrapItem(response);
      } catch (error) {
        console.error(error);
      }
    };
    fetchScrap();
  }, []);

  useEffect(() => {
    if (scrapItem) {
      const series = scrapItem.map(data => data.cnt);
      const labels = scrapItem.map(data => data.name);

      const options = {
        series: series,
        chart: {
          width: 380,
          type: 'donut',
        },
        labels: labels,
        colors: ['#FF4560', '#008FFB', '#FEB019', '#FFC100', '#4CAF50', '#2196F3', '#9C27B0', '#673AB7', '#FF6384', '#36A2EB'],
        responsive: [{
          breakpoint: 480,
          options: {
            chart: {
              width: 200
            },
            legend: {
              position: 'bottom'
            }
          }
        }],
        plotOptions: {
          pie: {
            dataLabels: {
              offset: -30,
              style: {
                fontSize: '14px'
              }
            }
          }
        },
      };

      if (chartRef.current) {
        chartRef.current.destroy();
      }

      chartRef.current = new ApexCharts(document.querySelector('#chart'), options);

      chartRef.current.render();

      chartRef.current.addEventListener('dataPointSelection', function(event, chartContext, config) {
        // console.log(scrapItem[config.dataPointIndex]);
      });
    }
  }, [scrapItem]);

  return (
    <div id="chart"></div>
  );
};

export default DonutChart;
