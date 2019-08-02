import http from "@/request/ax";

// 设置新密码
export function setNewPwd(data) {
  return http({
    url: `/access/modifyPwd`,
    method: "post",
    data
  });
}

