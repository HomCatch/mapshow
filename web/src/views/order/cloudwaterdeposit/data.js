export const options1 = [
      { label: '一年套餐', value: 0 },
      { label: '一年套餐+送水', value: 1 },
      { label: '两年套餐', value: 2 },
      { label: '两年套餐+送水', value: 3 },
      { label: '三年套餐', value: 4 },
      { label: '三年套餐+送水', value: 5 },
      { label: '一年套餐（预定）', value: 6 },
      { label: '两年套餐（预定）', value: 7 },
      { label: '三年套餐（预定）', value: 8 }
]

export const options2 = [
      { label: '支付宝', value: 0 },
      { label: '微信', value: 1 }
]
export const options3 = [
      { label: '资料未完善', value: 0 },
      { label: '待发货', value: 1 },
      { label: '已发货', value: 2 },
      { label: '待安装', value: 3 },
      { label: '订单完成', value: 4 }

]
// 搜索框项目
export const searchItems = [
      {
            label: "订单编号",
            value: "orderNumber",
            type: "input",
            placeholder: "订单编号",
      },
      {
            label: "用户昵称",
            value: "userName",
            type: "input",
            placeholder: "用户昵称",
      },
      {
            label: "注册手机",
            value: "phoneNumber",
            type: "input",
            placeholder: "注册手机",
      },
      {
            label: "收货人",
            value: "receiver",
            type: "input",
            placeholder: "收货人",
      },
      {
            label: "收货地址",
            value: "receivAddress",
            type: "input",
            placeholder: "收货地址",
      },
      {
            label: "收货手机号",
            value: "receivPhoneNumber",
            type: "input",
            placeholder: "收货手机号",
      },
      {
            label: "套餐类型",
            value: "type",
            type: "select",
            placeholder: "套餐类型",
            options: options1
      },
      {
            label: "单价",
            value: "price",
            type: "input",
            placeholder: "单价",
      },
      {
            label: "支付方式",
            value: "payType",
            type: "select",
            placeholder: "支付方式",
            options: options2
      },
      {
            label: "押金",
            value: "deposit",
            type: "input",
            placeholder: "押金",
      },
      {
            label: "订单状态",
            value: "orderType",
            type: "select",
            placeholder: "订单状态",
            options: options3
      },
      {
            label: "下单时间",
            value: "orderDate",
            type: "input",
            placeholder: "下单时间",
      },
      {
            label: "第三方流水号",
            value: "thirdNumber",
            type: "input",
            placeholder: "第三方流水号",
      },
      {
            label: "设备编号",
            value: "deviceId",
            type: "input",
            placeholder: "设备编号",
      },
]

// 功能按钮
export const funcs = ['新增', '修改', '删除'];

// 表格项目
export const tableColumns = [
      { label: "订单编号", value: "orderNumber" },
      { label: "用户昵称", value: "userName" },
      { label: "注册手机", value: "phoneNumber" },
      { label: "收货人", value: "receiver" },
      { label: "收货地址", value: "receivAddress" },
      { label: "收货手机号", value: "receivPhoneNumber" },
      {
            label: "套餐类型", value: "type", formatter(row) {
                  let text = '-';
                  switch (row.type) {
                        case 0: text = '一年套餐'
                              break;
                        case 1: text = '一年套餐+送水'
                              break;
                        case 2: text = '两年套餐'
                              break;
                        case 3: text = '两年套餐+送水'
                              break;
                        case 4: text = '三年套餐'
                              break;
                        case 5: text = '三年套餐+送水'
                              break;
                        case 6: text = '一年套餐(预定）'
                              break;
                        case 7: text = '两年套餐（预定）'
                              break;
                        case 8: text = '三年套餐（预定）'
                              break;
                  }
                  return text;
            }
      },
      { label: "单价", value: "price" },
      {
            label: "支付方式", value: "payType", formatter(row) {
                  let text = '-';
                  if (row.payType === 1) {
                        text = '微信'
                  } else if (row.payType === 0) {
                        text = '支付宝'
                  }
                  return text;
            }
      },
      { label: "押金", value: "deposit" },
      {
            label: "订单状态", value: "orderType", formatter(row) {
                  let text = '-';
                  switch (row.orderType) {
                        case 0: text = '资料未完善'
                              break;
                        case 1: text = '待发货'
                              break;
                        case 2: text = '已发货'
                              break;
                        case 3: text = '待安装'
                              break;
                        case 4: text = '订单完成'
                              break;
                  }
                  return text;
            }
      },
      { label: "下单时间", value: "orderDate" },
      { label: "第三方流水号", value: "thirdNumber" },
      { label: "设备编号", value: "deviceId" },
]

