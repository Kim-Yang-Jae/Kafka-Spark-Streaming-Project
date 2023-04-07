import React from "react";
import ApexCharts from "react-apexcharts";

const CompanyKeyword = () => {
  const data = [
    {
      x: "New Delhi",
      y: 218,
    },
    {
      x: "Kolkata",
      y: 149,
    },
    {
      x: "Mumbai",
      y: 184,
    },
    {
      x: "Ahmedabad",
      y: 55,
    },
    {
      x: "Bangaluru",
      y: 84,
    },
    {
      x: "Pune",
      y: 31,
    },
    {
      x: "Chennai",
      y: 70,
    },
    {
      x: "Jaipur",
      y: 30,
    },
    {
      x: "Surat",
      y: 44,
    },
    {
      x: "Hyderabad",
      y: 68,
    },
    {
      x: "Lucknow",
      y: 28,
    },
    {
      x: "Indore",
      y: 19,
    },
    {
      x: "Kanpur",
      y: 29,
    },
    {
      x: "Lucknow",
      y: 28,
    },
    {
      x: "Indore",
      y: 19,
    },
    {
      x: "Kanpur",
      y: 29,
    },
  ];
  const state = {
    series: [
      {
        data: data,
      },
    ],
    options: {
      legend: {
        show: false,
      },
      chart: {
        height: 1000,
        type: "treemap",
        toolbar: {
          show: false, // 메뉴 버튼 안보이게 설정
        },
      },
      colors: [
        "#3B93A5",
        "#F7B844",
        "#ADD8C7",
        "#EC3C65",
        "#CDD7B6",
        "#C1F666",
        "#D43F97",
        "#1E5D8C",
        "#421243",
        "#7F94B0",
        "#EF6537",
        "#C0ADDB",
      ],
      plotOptions: {
        treemap: {
          distributed: true,
          enableShades: false,
        },
      },
    },
  };
  return (
    <div>
      <ApexCharts
        options={state.options}
        series={state.series}
        type="treemap"
      />
    </div>
  );
};

export default CompanyKeyword;
