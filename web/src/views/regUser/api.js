import http from "@/request/ax";

export function sendEmail(params) {
  return http({
    url: `/access/sendActivateEmail`,
    method: "get",
    params
  });
}

