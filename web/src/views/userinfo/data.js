// 搜索框项目
export const searchItems = [
  {
    label: "用户名",
    value: "nickName",
    type: "input",
    placeholder: "用户名"
  },
  {
    label: "手机号",
    value: "phoneNumber",
    type: "input",
    placeholder: "手机号"
  }
];

// 功能按钮
export const funcs = ["新增", "修改", "删除"];

// 表格项目
export const tableColumns = [
  { label: "个人昵称", value: "nickName" },
  {
    label: "性别",
    value: "sex",
    formatter(row) {
      let text = "";
      if (row.sex === 1) {
        text = "<span>男</span>";
      } else if (row.sex === 0) {
        text = "<span>女</span>";
      } else {
        text = "<span>/</span>";
      }
      return text;
    }
  },
  {
    label: "出生",
    value: "birth",
    formatter(row) {
      let text = "";
      if (row.birth) {
        text = `<span>${row.birth}</span>`;
      } else {
        text = "<span>/</span>";
      }
      return text;
    }
  },
  {
    label: "电话号码",
    value: "phoneNumber",
    formatter(row) {
      let text = "";
      if (row.phoneNumber) {
        text = `<span>${row.phoneNumber}</span>`;
      } else {
        text = "<span>/</span>";
      }
      return text;
    }
  },
  { label: "注册时间", value: "registeredTime" },
  { label: "修改时间", value: "modifiedTime" },
  { label: "上次登录时间", value: "lastLoginTime" }
];
