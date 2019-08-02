import http from "@/request/ax";
export function getList(data) {
  return http({
    url: `/waterStatics/pages`,
    method:'post',
    data
  });
}