import ax from '@/request/ax';

// 设备总览
export function getTotal(){
  return ax({
    url: `/devices/pandect`,
    method: 'post',
    data: {dataModel:0}
  })
}

// 近七日TDS变化
export function getTDS(){
  return ax({
    url: `/historys/tdsRecord`,
    method: 'post',
    data: {dataModel:0}
  })
}

// 近15天用水量统计
export function getHistory(){
  return ax({
    url: `/historys/record`,
    method: 'post',
    data: {dataModel:0}
  })
}

// 近七日浊度统计
export function getTur() {
  return ax({
    url: `/historys/tbdtRecord`,
    method: 'post',
    data: {dataModel:0}
  })
}

// 近七日色度统计
export function getColor() {
  return ax({
    url: `/historys/colorRecord`,
    method: 'post',
    data: {dataModel:0}
  })
}