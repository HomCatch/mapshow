export function userData(x,y,name) {
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
