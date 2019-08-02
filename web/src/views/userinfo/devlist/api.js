import ax from '@/request/ax'

// 列表
export function getList(data) {
  return ax({
    url: `/userInfo/findDevInfo`,
    method: 'post',
    data
  })
}
