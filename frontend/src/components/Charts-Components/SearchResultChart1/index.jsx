import React from "react";
import ReactApexChart from "react-apexcharts";

const SearchResultChart1 = ({ splitKeyword }) => {
  const state = {
    series: [
      {
        data: splitKeyword.valueList,
      },
    ],
    options: {
      chart: {
        type: "bar",
        height: 350,
        toolbar: {
          show: false, // 메뉴 버튼 안보이게 설정
        },
      },
      plotOptions: {
        bar: {
          borderRadius: 4,
          horizontal: true,
        },
      },
      dataLabels: {
        enabled: false,
      },
      xaxis: {
        categories: splitKeyword.textList,
      },
    },
  };

  return (
    <div style={{ margin: "0 2% 0 0" }}>
      <ReactApexChart
        options={state.options}
        series={state.series}
        type="bar"
        height={300}
      />
    </div>
  );
};

export default SearchResultChart1;
