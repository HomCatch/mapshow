import ax from '@/request/ax';

export function getProvinces() {
  return ax({
    url: `/area/provinces`,
    method: 'get'
  })
}

export function getCities(params) {
  return ax({
    url: `/area/cities`,
    method: 'get',
    params
  })
}


// 获取某省所有设备
export async function getDevList(params) {
  return ax({
    url: '/devices/deviceMap',
    method: 'get',
    // data: qs.stringify({ ...data })
    params
  })
}
