import ax from './ax';

// 滤芯
// 导入
export function upload(data) {
  return ax({
    url: '/filterPlans/import',
    method: 'post',
    data,
    'Content-Type': 'multipart/form-data'
  })
}

// 导出
export function _export(params) {
  return ax({
    url: '/filterPlans/export',
    method: 'post',
    data: params,
    responseType: 'blob',
    // 'Content-Type': 'multipart/form-data'
  })
}

// 下载模板
export function download_ter_template(params) {
  return ax({
    url: '/filterPlans/download',
    method: 'get',
    params,
    responseType: 'blob',
    'Content-Type': 'multipart/form-data'
  })
}