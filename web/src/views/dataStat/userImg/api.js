import http from "@/request/ax";
export function getList(data) {
  return http({
    url: `/portraitStatics/pages`,
    method: "post",
    data
  });
}