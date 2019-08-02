export function isLineData() {
  return {
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    calculable: true,
    color: ["#6c757d","#00cf97"],
    series: [
      {
        name: "订单数",
        type: "pie",
        radius: [40, 60],
        center:["50%","18%"],
        itemStyle: {
          normal: {
            label: {
              show: false
            },
            labelLine: {
              show: false
            }
          }
        },
        data: [
          { value: 1804, name: "待支付" },
          { value: 7380, name: "已支付" }
        ]
      }
    ]
  };
}

export function filterData() {
  return {
    tooltip: {
      trigger: "axis"
    },
    calculable: true,
    xAxis: [
      {
        type: "category",
        boundaryGap: false,
        data: [
          "2019.3.1",
          "2019.3.2",
          "2019.3.3",
          "2019.3.4",
          "2019.3.5",
          "2019.3.6",
          "2019.3.7"
        ]
      }
    ],
    yAxis: [
      {
        type: "value"
      }
    ],
    series: [
      {
        name: "下单数",
        type: "line",
        stack: "总量",
        data: [120, 132, 101, 134, 90, 230, 210]
      }
    ]
  };
}
