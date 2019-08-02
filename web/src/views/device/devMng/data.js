// 搜索框项目
export const searchItems = [
  {
    label: "设备ID",
    value: "deviceId",
    type: "input",
    placeholder: ""
  },
  {
    label: "绑定账号",
    value: "bindAccount",
    type: "input",
    placeholder: ""
  }
];

// 功能按钮
export const funcs = ["新增", "修改", "删除","导出"];

// 表格项目
export const tableColumns = [
  { label: "设备ID", value: "deviceId" },
  //{ label: "设备别名", value: "deviceName" },
  {
    label: "备注",
    value: "remark",
    formatter(row) {
      let text = "";
      if (row.remark) {
        text = `<span>${row.remark}</span>`;
      } else {
        text = "<span>/</span>";
      }
      return text;
    }
  },
  {
    label: "绑定账号",
    value: "bindAccount",
    formatter(row) {
      let text = "";
      if (row.bindAccount) {
        text = `<span>${row.bindAccount}</span>`;
      } else {
        text = "<span>/</span>";
      }
      return text;
    }
  },
  {
    label: "绑定状态",
    value: "bindStatus",
    formatter(row) {
      let text = "";
      if (row.bindStatus === 1) {
        text = "<span>已绑定</span>";
      } else {
        text = "<span>未绑定</span>";
      }
      return text;
    }
  },
  {
    label: "绑定时间",
    value: "bindTime",
    formatter(row) {
      return row.updateTime || "/";
    }
  },
  {
    label: "在线状态",
    value: "status",
    formatter(row) {
      let text = "";
      if (row.status === 1) {
        text = "<span style='color:#5ac750'>在线</span>";
      } else {
        text = "<span>离线</span>";
      }
      return text;
    }
  },
  {
    label: "IP地址",
    value: "ip",
    formatter(row) {
      let text = "";
      if (row.ip) {
        text = `<span>${row.ip}</span>`;
      } else {
        text = "<span>/</span>";
      }
      return text;
    }
  },
  {
    label: "滤芯剩余寿命",
    value: "filterLifetime",
    formatter(row) {
      let text = "";
      if (row.filterLifetime) {
        text = `<span>${row.filterLifetime}%</span>`;
      } else {
        text = "<span>/</span>";
      }

      return text;
    }
  },
  {
    label: "更新时间",
    value: "updateTime",
    formatter(row) {
      return row.updateTime || "-";
    }
  },
  {
    label: "入库时间",
    value: "insertDeviceTime",
    formatter(row) {
      return row.updateTime || "-";
    }
  }
  //    { label: "经度", value: "deviceX" },
  //    { label: "纬度", value: "deviceY" },
  // { label: "所属经销商", value: "deptId" },
  //   {
  //     label: "使用状态",
  //     value: "status",
  //     formatter(row) {
  //       let text = "";
  //       if (row.status === 0) {
  //         text =
  //           '<span style="display: inline-block;padding: 3px 5px;background: #67C23A;color: #fff;border-radius: 3px;">使用中</span>';
  //       } else {
  //         text =
  //           '<span style="display: inline-block;padding: 3px 5px;background: #909399;color:#fff;border-radius: 3px;">未使用</span>';
  //       }
  //       return text;
  //     }
  //   }
];
