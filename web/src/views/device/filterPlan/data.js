import Moment from 'moment';
// 搜索框项目
export const searchItems = [
      {
            label: "滤芯编号",
            value: "deviceId",
            type: "input",
            placeholder: "",
      }
]

// 功能按钮
export const funcs = ['新增', '修改', '删除'];

// 表格项目
export const tableColumns = [
      { label: "滤芯编号", value: "deviceId" },
      {
            label: "上次滤芯更换时间", value: "previousTime", formatter(row) {
                  return Moment(row.previousTime).format('YYYY-MM-DD HH:mm:ss');
            }
      },
      { label: "上次滤芯更换备注", value: "previousRemark" },
      {
            label: "下次滤芯更换时间", value: "nextTime", formatter(row) {
                  return Moment(row.nextTime).format('YYYY-MM-DD HH:mm:ss');
            }
      },
      { label: "下次滤芯更换备注", value: "nextRemark" },
]

