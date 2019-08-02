import http from "@/request/ax";

// 手机注册
export function register(data) {
  return http({
    url: `/access/register`,
    method: "post",
    data
  });
}

// 邮箱注册
export function registerEmail(data) {
  return http({
    url: `/access/registerMail`,
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

export function sendEmail(params) {
  return http({
    url: `/access/sendActivateEmail`,
    method: "get",
    params
  });
}
