import http from "@/request/ax";
export function getList(data) {
  return http({
    url: `/userStatics/pages`,
    method: "post",
    data
  });
}