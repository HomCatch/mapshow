export function geneOptions(x, y) {
  return {
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
    grid: {
      top: 30,
      left: 50,
      right: 20,
    },
    xAxis: {
      type: 'category',
      data: x
    },
    yAxis: {
      type: 'value'
    },
    series: [{
      data: y,
      markLine: {
        animation: true, animationDelay: function (idx) {
          // 越往后的数据延迟越大
          return idx * 100;
        }
      },
      type: 'bar',
      itemStyle: {
        color: '#67C23A'
      }
    }]
  }
}