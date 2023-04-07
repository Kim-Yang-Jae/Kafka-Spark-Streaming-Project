import React, { useEffect } from "react";
import * as am4core from "@amcharts/amcharts4/core";
import * as am4plugins_forceDirected from "@amcharts/amcharts4/plugins/forceDirected";
import am4themes_animated from "@amcharts/amcharts4/themes/animated";

const SearchResultChart2 = ({ keyword }) => {
  const data = [
    {
      name: keyword,
      children: [
        {
          name: "코스닥",
          children: [
            {
              name: "NH투자증권",
              children: [
                {
                  name: "NH투자증권",
                  value: 1,
                },
                {
                  name: "Rose",
                  value: 1,
                },
                {
                  name: "Jasmine",
                  value: 1,
                },
              ],
            },
            {
              name: "Rose",
              value: 1,
            },
            {
              name: "Jasmine",
              value: 1,
            },
          ],
        },
        {
          name: "Black Tea",
          children: [
            {
              name: "Chamomile",
              value: 1,
            },
            {
              name: "Rose",
              value: 1,
            },
            {
              name: "Jasmine",
              value: 10,
            },
          ],
        },
        {
          name: "Black Tea",
          value: 1,
        },
        {
          name: "Black Tea",
          value: 1,
        },
        {
          name: "Black Tea",
          value: 1,
        },
        {
          name: "Floral",
          children: [
            {
              name: "Chamomile",
              value: 1,
            },
            {
              name: "Rose",
              value: 1,
            },
            {
              name: "Jasmine",
              value: 1,
            },
          ],
        },
      ],
    },
  ];

  useEffect(() => {
    am4core.useTheme(am4themes_animated);

    const chart = am4core.create(
      "chartdiv",
      am4plugins_forceDirected.ForceDirectedTree
    );
    chart.data = data;

    const networkSeries = chart.series.push(
      new am4plugins_forceDirected.ForceDirectedSeries()
    );

    networkSeries.dataFields.value = "value";
    networkSeries.dataFields.name = "name";
    networkSeries.dataFields.children = "children";
    networkSeries.nodes.template.tooltipText = "{name}:{value}";
    networkSeries.nodes.template.fillOpacity = 1;
    networkSeries.nodes.template.label.fontSize = 15;
    networkSeries.nodes.template.label.fontWeight = "bold";
    networkSeries.nodes.template.label.fill = am4core.color("black");

    networkSeries.nodes.template.label.text = "{name}";
    networkSeries.fontSize = 10;

    networkSeries.links.template.strokeWidth = 1;

    const hoverState = networkSeries.links.template.states.create("hover");
    hoverState.properties.strokeWidth = 3;
    hoverState.properties.strokeOpacity = 1;

    networkSeries.nodes.template.events.on("over", (event) => {
      event.target.dataItem.childLinks.each(function (link) {
        link.isHover = true;
      });
      if (event.target.dataItem.parentLink) {
        event.target.dataItem.parentLink.isHover = true;
      }
    });

    networkSeries.nodes.template.events.on("out", (event) => {
      event.target.dataItem.childLinks.each(function (link) {
        link.isHover = false;
      });
      if (event.target.dataItem.parentLink) {
        event.target.dataItem.parentLink.isHover = false;
      }
    });

    return () => {
      chart.dispose();
    };
  }, [keyword]);

  return (
    <div
      id="chartdiv"
      style={{ width: "100%", height: "355px", maxWidth: "100%" }}
    ></div>
  );
};

export default SearchResultChart2;
