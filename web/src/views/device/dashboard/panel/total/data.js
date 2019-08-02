export function geneOptions(echartData) {
  return {
    tooltip: {
      trigger: 'item',
      formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    color: ['#67C23A', '#F56C6C'],
    legend: {
      orient: 'vertical',
      x: 'right',
      data: ['活跃设备数', '离线设备数'],
      show: true
    },
    series: [
      {
        name: '访问来源',
        type: 'pie',
        radius: ['50%', '70%'],
        avoidLabelOverlap: false,
        label: {
          normal: {
            show: false,
            position: 'center'
          },
          emphasis: {
            show: true,
            textStyle: {
              fontSize: '20'
            }
          }
        },
        labelLine: {
          normal: {
            show: false
          }
        },
        data: echartData
      }
    ]
  };
}
