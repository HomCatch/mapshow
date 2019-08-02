export function userData(male, female) {
  return {
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    calculable: true,
    color: ["#fa6987", "#727cf5"],
    series: [
      {
        name: "用户性别",
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
        data: [{ value: female, name: "女性" }, { value: male, name: "男性" }]
      }
    ]
  };
}

export function ageData(ageList) {
  return {
    tooltip: {
      trigger: "axis"
    },
    calculable: true,
    color: ["#00c0ce"],
    xAxis: [
      {
        type: "category",
        data: ["<=19", "20~29", "30~39", "40~49", ">=50"]
      }
    ],
    yAxis: [
      {
        type: "value"
      }
    ],
    series: [
      {
        name: "年龄分布",
        type: "bar",
        data: ageList,
        markPoint: {
          data: [
            { type: "max", name: "最大值" },
            { type: "min", name: "最小值" }
          ]
        },
        markLine: {
          data: [{ type: "average", name: "平均值" }]
        }
      }
    ]
  };
}
export function endData(endObj) {
  return {
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    calculable: true,
    series: [
      {
        name: "用户终端分布",
        type: "pie",
        radius: [60, 100],

        // for funnel
        x: "60%",
        width: "35%",
        funnelAlign: "left",
        max: 1048,

        data: [
          { value: endObj.huawei, name: "Huawei" },
          { value: endObj.xiaomi, name: "Xiaomi" },
          { value: endObj.apple, name: "Apple" },
          { value: endObj.samSong, name: "SumSung" },
          { value: endObj.other, name: "Other" }
        ]
      }
    ]
  };
}

export function endTable(endObj) {
  return [
    {
      brand: "Apple",
      userNum: endObj.apple
    },
    {
      brand: "Huawei",
      userNum: endObj.huawei
    },
    {
      brand: "Xiaomi",
      userNum: endObj.xiaomi
    },
    {
      brand: "SamSung",
      userNum: endObj.samSong
    }
  ];
}

export function areaTable(areaObj) {
  return [
    {
      province: "广东",
      userNum: areaObj.guangDong
    },
    {
      province: "江苏",
      userNum: areaObj.jiangSu
    },
    {
      province: "山东",
      userNum: areaObj.shanDong
    },
    {
      province: "浙江",
      userNum: areaObj.zheJiang
    },
    {
      province: "辽宁",
      userNum: areaObj.liaoNing
    },
    {
      province: "四川",
      userNum: areaObj.siChuan
    },
    {
      province: "湖北",
      userNum: areaObj.heBei
    },
    {
      province: "湖南",
      userNum: areaObj.heNan
    },
    {
      province: "河北",
      userNum: areaObj.heBei
    },
    {
      province: "河南",
      userNum: areaObj.heNan
    },
    {
      province: "福建",
      userNum: areaObj.fuJian
    },
    {
      province: "贵州",
      userNum: areaObj.guiZhou
    },
    {
      province: "云南",
      userNum: areaObj.yunNan
    },
    {
      province: "广西",
      userNum: areaObj.guangXi
    }
  ];
}

export function areaDatas(areaObj) {
  return {
    tooltip: {
      trigger: "item"
    },
    dataRange: {
      orient: "horizontal",
      min: 0,
      max: 55000,
      text: ["高", "低"], // 文本，默认为数值文本
      splitNumber: 0
    },
    series: [
      {
        name: "2011全国GDP分布",
        type: "map",
        mapType: "china",
        mapLocation: {
          x: "left"
        },
        selectedMode: "multiple",
        itemStyle: {
          normal: { label: { show: true } },
          emphasis: { label: { show: true } }
        },
        data: [
          { name: "广西", value: areaObj.guangXi },
          { name: "贵州", value: areaObj.guiZhou },
          { name: "云南", value: areaObj.yunNan },
          { name: "福建", value: areaObj.fuJian },
          { name: "湖北", value: areaObj.huBei },
          { name: "湖南", value: areaObj.huNan },
          { name: "四川", value: areaObj.siChuan },
          { name: "辽宁", value: areaObj.liaoNing },
          { name: "河北", value: areaObj.heBei },
          { name: "河南", value: areaObj.heNan },
          { name: "浙江", value: areaObj.zheJiang },
          { name: "山东", value: areaObj.shanDong },
          { name: "江苏", value: areaObj.jiangSu },
          { name: "广东", value: areaObj.guangDong }
        ]
      }
    ],
    animation: false
  };
}
