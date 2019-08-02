// 搜索框项目
export const searchItems = [
      {
            label: "订单号",
            value: "orderNo",
            type: "input",
            placeholder: "订单号",
      },
      {
            label: "第三方系统交易号",
            value: "tradeNo",
            type: "input",
            placeholder: "第三方系统交易号",
      },
      {
            label: "设备mac地址",
            value: "devMac",
            type: "input",
            placeholder: "设备mac地址",
      },
      {
            label: "",
            value: "devId",
            type: "input",
            placeholder: "",
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
      {
            label: "水通道号 1：开水，2：热水，3,：常温水，4：冷水)",
            value: "waterTemp",
            type: "input",
            placeholder: "水通道号 1：开水，2：热水，3,：常温水，4：冷水)",
      },
      {
            label: "水量",
            value: "waterAmount",
            type: "input",
            placeholder: "水量",
      },
      {
            label: "微信openId",
            value: "openId",
            type: "input",
            placeholder: "微信openId",
      },
      {
            label: "微信支付回调时间",
            value: "payNotifyTime",
            type: "input",
            placeholder: "微信支付回调时间",
      },
      {
            label: "硬件出水完成，上报时间",
            value: "hardwareNotifyTime",
            type: "input",
            placeholder: "硬件出水完成，上报时间",
      },
      {
            label: "支付状态 1. 待支付 2.已付款 3.已退款",
            value: "payStatus",
            type: "input",
            placeholder: "支付状态 1. 待支付 2.已付款 3.已退款",
      },
      {
            label: "0.订单已取消 2.已下发出水指令，等待应答 (待出水) 1.已应答，售卖成功 （已出水）3.售水失败",
            value: "sellStatus",
            type: "input",
            placeholder: "0.订单已取消 2.已下发出水指令，等待应答 (待出水) 1.已应答，售卖成功 （已出水）3.售水失败",
      },
      {
            label: "商户退款单号",
            value: "refundNo",
            type: "input",
            placeholder: "商户退款单号",
      },
      {
            label: "优惠金额",
            value: "couponPrice",
            type: "input",
            placeholder: "优惠金额",
      },
      {
            label: "微信支付金额",
            value: "moneyAmount",
            type: "input",
            placeholder: "微信支付金额",
      },
      {
            label: "微信退款单号",
            value: "refundId",
            type: "input",
            placeholder: "微信退款单号",
      },
      {
            label: "售水类型",
            value: "sellType",
            type: "input",
            placeholder: "售水类型",
      },
      {
            label: "脉冲通道",
            value: "passageNo",
            type: "input",
            placeholder: "脉冲通道",
      },
      {
            label: "",
            value: "couponUsageId",
            type: "input",
            placeholder: "",
      },
      {
            label: "脉冲数",
            value: "pulse",
            type: "input",
            placeholder: "脉冲数",
      },
]

// 功能按钮
export const funcs = ['新增', '修改', '删除'];

// 表格项目
export const tableColumns = [
      { label: "订单号", value: "orderNo" },
      { label: "第三方系统交易号", value: "tradeNo" },
      { label: "设备mac地址", value: "devMac" },
      { label: "", value: "devId" },
      { label: "订单创建时间", value: "createTime" },
      { label: "水通道号 1：开水，2：热水，3,：常温水，4：冷水)", value: "waterTemp" },
      { label: "水量", value: "waterAmount" },
      { label: "微信openId", value: "openId" },
      { label: "微信支付回调时间", value: "payNotifyTime" },
      { label: "硬件出水完成，上报时间", value: "hardwareNotifyTime" },
      { label: "支付状态 1. 待支付 2.已付款 3.已退款", value: "payStatus" },
      { label: "0.订单已取消 2.已下发出水指令，等待应答 (待出水) 1.已应答，售卖成功 （已出水）3.售水失败", value: "sellStatus" },
      { label: "商户退款单号", value: "refundNo" },
      { label: "优惠金额", value: "couponPrice" },
      { label: "微信支付金额", value: "moneyAmount" },
      { label: "微信退款单号", value: "refundId" },
      { label: "售水类型", value: "sellType" },
      { label: "脉冲通道", value: "passageNo" },
      { label: "", value: "couponUsageId" },
      { label: "脉冲数", value: "pulse" },
]