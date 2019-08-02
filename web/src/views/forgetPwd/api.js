import http from "@/request/ax";

// 手机找回密码
export function forgetPwd(data) {
  return http({
    url: `/access/forgetPwd`,
    method: "post",
    data
  });
}

// 邮箱找回密码
export function forgetEmailPwd(data) {
  return http({
    url: `/access/forgetEmailPwd`,
    method: "post",
    data
  });
}

// 图形验证码
export function captcha(params) {
  return http({
    url: `/access/captcha`,
    method: "get",
    params
  });
}
// 手机验证码
export function sendSms(params) {
  return http({
    url: `/access/sendSms`,
    method: "get",
    params
  });
}
