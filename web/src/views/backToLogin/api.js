import http from "@/request/ax";

// 设置新密码
export function activate(params) {
  return http({
    url: `/access/email/activate`,
    method: "get",
    params
  });
}
