import http from "@/request/ax";
export function getList(data) {
  return http({
    url: `/deviceStatics/pages`,
    method: "post",
    data
  });
}