import ax from '@/request/ax'

// 列表
export function getList(data) {
    return ax({
        url: `/cloudWaterDeposits/pages`,
        method: 'post',
        data
    })
}

// 新增
export function add(data) {
    return ax({
        url: `/cloudWaterDeposits/`,
        method: 'post',
        data
    })
}

// 修改
export function edit({ id, data }) {
    return ax({
        url: `/cloudWaterDeposits/${id}`,
        method: 'put',
        data
    })
}

// 删除
export function del({ id }) {
    return ax({
        url: `/cloudWaterDeposits/${id}`,
        method: 'delete'
    })
}

// 详情
export function getDetail({ id }) {
    return ax({
        url: `/cloudWaterDeposits/${id}`,
        method: 'get'
    })
}
// 导出
export function _export() {
    return ax({
        url: `/cloudWaterDeposits/export/`,
        method: 'get',
        responseType: 'blob',
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

