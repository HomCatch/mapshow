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

// 搜索框项目
export const searchItems = [
      {
            label: "订单编号",
            value: "orderNumber",
            type: "input",
            placeholder: "订单编号",
      },
      {
            label: "设备编号",
            value: "deviceNumber",
            type: "input",
            placeholder: "设备编号",
      },
      {
            label: "用户昵称",
            value: "userName",
            type: "input",
            placeholder: "用户昵称",
      },
      {
            label: "注册号码",
            value: "phoneNumber",
            type: "input",
            placeholder: "注册号码",
      },
      {
            label: "真实姓名",
            value: "realName",
            type: "input",
            placeholder: "真实姓名",
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
            label: "共计水量",
            value: "totalAmount",
            type: "input",
            placeholder: "共计水量",
      },
      {
            label: "扣减水量",
            value: "minusAmount",
            type: "input",
            placeholder: "扣减水量",
      },
      {
            label: "账单金额",
            value: "billPrice",
            type: "input",
            placeholder: "账单金额",
      },
      {
            label: "支付方式",
            value: "payType",
            type: "select",
            placeholder: "支付方式",
            options: options2
      },
      {
            label: "支付金额",
            value: "payPrice",
            type: "input",
            placeholder: "支付金额",
      },
      {
            label: "第三方流水号",
            value: "thirdNumber",
            type: "input",
            placeholder: "第三方流水号",
      },
      {
            label: "账单时间",
            value: "billDate",
            type: "input",
            placeholder: "账单时间",
      },
      {
            label: "支付时间",
            value: "payDate",
            type: "input",
            placeholder: "支付时间",
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
      },
]

// 功能按钮
export const funcs = ['新增', '修改', '删除'];

// 表格项目
export const tableColumns = [
      { label: "订单编号", value: "orderNumber" },
      { label: "设备编号", value: "deviceNumber" },
      { label: "用户昵称", value: "userName" },
      { label: "注册号码", value: "phoneNumber" },
      { label: "真实姓名", value: "realName" },
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
      { label: "共计水量", value: "totalAmount" },
      { label: "扣减水量", value: "minusAmount" },
      { label: "账单金额", value: "billPrice" },
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
      { label: "支付金额", value: "payPrice" },
      { label: "第三方流水号", value: "thirdNumber" },
      { label: "账单时间", value: "billDate" },
      { label: "支付时间", value: "payDate" },
      { label: "创建时间", value: "createTime" },
]

