export function geneOptions(x, y1, y2) {
  return {
    grid: {
      top: 30,
      left: 50,
      right: 20,
    },
    tooltip: {
      trigger: "axis",
      axisPointer: {
        lineStyle: {
          color: "#ddd"
        }
      },
      backgroundColor: "rgba(255,255,255,1)",
      padding: [5, 10],
      textStyle: {
        color: "#7588E4"
      },
      extraCssText: "box-shadow: 0 0 5px rgba(0,0,0,0.3)"
    },
    xAxis: {
      type: "category",
      data: x,
      boundaryGap: false,
      splitLine: {
        show: true,
        interval: "auto",
        lineStyle: {
          color: ["#D4DFF5"]
        }
      },
      axisTick: {
        show: false
      },
      axisLine: {
        lineStyle: {
          color: "#609ee9"
        }
      },
      axisLabel: {
        margin: 10,
        textStyle: {
          fontSize: 14
        },
      }
    },
    yAxis: {
      type: "value",
      splitLine: {
        lineStyle: {
          color: ["#D4DFF5"]
        }
      },
      axisTick: {
        show: false
      },
      axisLine: {
        lineStyle: {
          color: "#609ee9"
        }
      },
      axisLabel: {
        margin: 10,
        textStyle: {
          fontSize: 14
        }
      }
    },
    series: [
      {
        name: "出水",
        type: "line",
        showSymbol: false,
        symbol: "circle",
        symbolSize: 6,
        markLine: {
          animation: true,
          animationDuration: function (idx) {
            // 越往后的数据延迟越大
            return idx * 3000;
          },
          animationDurationUpdate: function (idx) {
            // 越往后的数据延迟越大
            return idx * 3000;
          }
        },
        data: y1,
        itemStyle: {
          normal: {
            color: "#67C23A"
          }
        },
        lineStyle: {
          normal: {
            width: 2
          }
        }
      },
      {
        name: "入水",
        type: "line",
        markLine: { animation: true },
        showSymbol: false,
        symbol: "circle",
        symbolSize: 6,
        data: y2,
        itemStyle: {
          normal: {
            color: "#F56C6C"
          }
        },
        lineStyle: {
          normal: {
            width: 2
          }
        }
      }
    ]
  }
}