// 搜索框项目
export const searchItems = [
    {
        label: "设备ID",
        value: "deviceId",
        type: "input",
        placeholder: "设备ID",
    },
    {
        label: '开始时间',
        value: 'startTime',
        type: 'datetime'
    },
    {
        label: '结束时间',
        value: 'endTime',
        type: 'datetime'
    }
]

// 功能按钮
export const funcs = ['新增', '修改', '删除'];

// 表格项目
export const tableColumns = [
    {label: "设备ID", value: "deviceId"},
    {label: "备注", value: "deviceName"},
    {label: "入水TDS", value: "tds"},
    {label: "出水TDS", value: "purifyTds"},
    {label: "入水色度", value: "color"},
    {label: "出水色度", value: "purifyColor"},
    {label: "入水浊度", value: "trt"},
    {label: "出水浊度", value: "purifyTbdt"},
    {label: "入水温度", value: "tbdt"},
    {label: "出水温度", value: "purifyTrt"},
    {label: "更新时间", value: "recordTime"},
]

