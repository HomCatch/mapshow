// 搜索框项目
export const searchItems = [
  {
    label: "设备号",
    value: "deviceId",
    type: "input",
    placeholder: "设备号",
  }, {
    label: "年份",
    value: "year",
    type: "date",
    _type: 'year',
    'value-format': 'yyyy',
    'format': 'yyyy',
    placeholder: "选择年份",
  }, {
    label: "月份",
    value: "month",
    type: "select",
    options: [{ label: 1, value: '01' }, { label: 2, value: '02' }, { label: 3, value: '03' }, { label: 4, value: '04' }, { label: 5, value: '05' }, { label: 6, value: '06' }, { label: 7, value: '07' }, { label: 8, value: '08' }, { label: 9, value: '09' }, { label: 10, value: '10' }, { label: 11, value: '11' }, { label: 12, value: '12' }],
    placeholder: "选择月份",
  }, {
    label: "执行状态",
    value: "replaceFinshed",
    type: "select",
    placeholder: "选择状态",
    options: [
      { label: '未执行', value: 0 },
      { label: '已执行', value: 1 }
    ]
  }
]

// 功能按钮
export const funcs = ['新增', '修改', '删除'];

// 表格项目
export const tableColumns = [
  { label: "客户", value: "customer" },
  { label: "设备号", value: "deviceId" },
  { label: "计划更换时间", value: "planReplaceTime" },
  { label: "实际更换时间", value: "realReplaceTime" },
  {
    label: "是否更換PP棉", value: "replaceFirstFilter", formatter(row) {
      let text = '-';
      if (row.replaceFirstFilter === 0) {
        text = '是'
      } else if (row.replaceFirstFilter === 1) {
        text = '否'
      }
      return text;
    }
  },
  {
    label: "是否更換椰壳炭", value: "replaceSecondFilter", formatter(row) {
      let text = '-';
      if (row.replaceSecondFilter === 0) {
        text = '是'
      } else if (row.replaceSecondFilter === 1) {
        text = '否'
      }
      return text;
    }
  },
  {
    label: "是否更換RO膜", value: "replaceThirdFilter", formatter(row) {
      let text = '-';
      if (row.replaceThirdFilter === 0) {
        text = '是'
      } else if (row.replaceThirdFilter === 1) {
        text = '否'
      }
      return text;
    }
  },
  {
    label: "是否更換后置炭", value: "replaceFouthFilter", formatter(row) {
      let text = '-';
      if (row.replaceFouthFilter === 0) {
        text = '是'
      } else if (row.replaceFouthFilter === 1) {
        text = '否'
      }
      return text;
    }
  },
  { label: "安装地址", value: "address" },
  { label: "安装人", value: "repairer" },
  { label: "安装人手机", value: "repairerPhoneNumber" },
  { label: "备注", value: "remark" },
  {
    label: "计划执行状态", value: "replaceFinshed", formatter(row) {
      let text = '-';
      if (row.replaceFinshed === 1) {
        text = '已执行'
      } else if (row.replaceFinshed === 0) {
        text = '未执行'
      }
      return text;
    }
  }

]

