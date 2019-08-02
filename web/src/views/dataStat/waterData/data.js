export function waterData(x,y,name) {
  return {
    tooltip: {
      trigger: "axis",
      show:true
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

export function analyseData(x,y) {
  return {
    tooltip: {
      trigger: "axis",
      show: true
    },
    legend: {
      data: [
        "入水TDS",
        "出水TDS",
        "入水色度",
        "出水色度",
        "入水浊度",
        "出水浊度",
        "入水温度",
        "出水温度"
      ]
    },
    grid: {
      left: "3%",
      right: "4%",
      bottom: "3%",
      containLabel: true
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
        name: "入水TDS",
        type: "line",
        data: y.inTds
      },
      {
        name: "出水TDS",
        type: "line",
        data: y.outTds
      },
      {
        name: "入水色度",
        type: "line",
        data: y.inColor
      },
      {
        name: "出水色度",
        type: "line",
        data: y.outColor
      },
      {
        name: "入水浊度",
        type: "line",
        data: y.intTbdt
      },
      {
        name: "出水浊度",
        type: "line",
        data: y.outTbdt
      },
      {
        name: "入水温度",
        type: "line",
        data: y.intTemp
      },
      {
        name: "出水温度",
        type: "line",
        data: y.outTemp
      }
    ]
  };
}
