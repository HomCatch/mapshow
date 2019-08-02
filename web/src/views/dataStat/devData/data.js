export function isLineData(online,offline) {
  return {
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    calculable: true,
    color: ["#6c757d", "#00cf97"],
    series: [
      {
        name: "设备状态",
        type: "pie",
        radius: [40, 60],
        center: ["50%", "18%"],
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
          { value: offline, name: "离线设备" },
          { value: online, name: "在线设备" }
        ]
      }
    ]
  };
}

export function filterData(x,y,name) {
  return {
    tooltip: {
      trigger: "axis"
    },
    calculable: true,
    xAxis: [
      {
        type: "category",
        boundaryGap: false,
        data: x
      }
    ],
    yAxis: [
      {
        type: "value"
      }
    ],
    series: [
      {
        name: name,
        type: "line",
        stack: "总量",
        data: y
      }
    ]
  };
}
