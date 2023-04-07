import React, { useEffect, useState } from "react";
import axios from "axios";
import * as am4core from "@amcharts/amcharts4/core";
import * as am4charts from "@amcharts/amcharts4/charts";
import am4themes_animated from "@amcharts/amcharts4/themes/animated";

am4core.useTheme(am4themes_animated);

function StockChart({ keyName, day }) {
  const [data, setData] = useState([]);

  useEffect(() => {
    const url =
      "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";
    const params = {
      serviceKey:
        "ERifINcaqI/MKEa0+o15ZnvcLbu2m2py9mvF3UhczTq4Mf+ZR9O4sSPclr6/g8IhwGmDRxLQX8YExauSPSIomA==",
      numOfRows: day,
      resultType: "json",
      itmsNm: keyName,
    };

    const fetchData = async () => {
      try {
        const response = await axios.get(url, { params });
        setData(response.data.response.body.items.item);
      } catch (error) {
        console.log(error);
      }
    };
    if (keyName) fetchData();
  }, [keyName, day]);

  const createChart = (chartData) => {
    const chart = am4core.create("chartdiv", am4charts.XYChart);
    chart.paddingRight = 20;
    chart.data = chartData;

    const dateAxis = chart.xAxes.push(new am4charts.DateAxis());
    dateAxis.renderer.grid.template.location = 0;

    const valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
    valueAxis.tooltip.disabled = true;
    valueAxis.renderer.minWidth = 35;

    const series = chart.series.push(new am4charts.LineSeries());
    series.dataFields.dateX = "date";
    series.dataFields.valueY = "price";
    series.tooltipText = "{valueY.value}";
    series.tooltip.pointerOrientation = "vertical";
    series.strokeWidth = 2;

    chart.cursor = new am4charts.XYCursor();
    chart.cursor.xAxis = dateAxis;
    chart.cursor.snapToSeries = series;
    chart.cursor.lineX.strokeWidth = 2;
    chart.cursor.lineX.strokeDasharray = "";
    chart.cursor.lineX.stroke = am4core.color("#d88800");

    return chart;
  };

  useEffect(() => {
    if (data.length === 0) return;

    const chart = createChart(
      data.map((item) => ({
        date: `${item["basDt"].substring(0, 4)}-${item["basDt"].substring(
          4,
          6
        )}-${item["basDt"].substring(6, 8)}`,
        price: item["clpr"],
        price2: item["hipr"],
        price3: item["lopr"],
      }))
    );

    const dateAxis = chart.xAxes.push(new am4charts.DateAxis());
    dateAxis.renderer.grid.template.location = 0;

    const valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
    valueAxis.tooltip.disabled = true;
    valueAxis.renderer.minWidth = 35;

    const series = chart.series.push(new am4charts.LineSeries());
    series.dataFields.dateX = "date";
    series.dataFields.valueY = "price";
    series.tooltipText = "{valueY.value}";
    series.tooltip.pointerOrientation = "vertical";
    series.strokeWidth = 2;

    const series2 = chart.series.push(new am4charts.LineSeries());
    series2.dataFields.dateX = "date";
    series2.dataFields.valueY = "price2";
    series2.tooltipText = "{valueY.value}";
    series2.tooltip.pointerOrientation = "vertical";
    series2.strokeWidth = 2;
    series2.stroke = am4core.color("#ff0000");

    const series3 = chart.series.push(new am4charts.LineSeries());
    series3.dataFields.dateX = "date";
    series3.dataFields.valueY = "price3";
    series3.tooltipText = "{valueY.value}";
    series3.tooltip.pointerOrientation = "vertical";
    series3.strokeWidth = 2;
    series3.stroke = am4core.color("#0000ff");

    chart.cursor = new am4charts.XYCursor();
    chart.cursor.xAxis = dateAxis;
    chart.cursor.snapToSeries = series;
    chart.cursor.lineX.strokeWidth = 2;
    chart.cursor.lineX.strokeDasharray = "";
    chart.cursor.lineX.stroke = am4core.color("#d88800");

    return () => {
      chart.dispose();
    };
  }, [data]);

  return <div id="chartdiv" style={{ width: "100%", height: "250px" }} />;
}

export default StockChart;
