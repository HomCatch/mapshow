import ax from '@/request/ax'

// 列表
export function getList(data) {
    return ax({
        url: `/userInfo/pages`,
        method: 'post',
        data
    })
}

// 新增
export function add(data) {
    return ax({
        url: `/userInfo/`,
        method: 'post',
        data
    })
}

// 修改
export function edit({ id, data }) {
    return ax({
        url: `/userInfo/${id}`,
        method: 'put',
        data
    })
}

// 删除
export function del({ id }) {
    return ax({
        url: `/userInfo/${id}`,
        method: 'delete'
    })
}

// 详情
export function getDetail({ id }) {
    return ax({
        url: `/userInfo/${id}`,
        method: 'get'
    })
}
// 导出
export function _export() {
    return ax({
        url: `/userInfo/export/`,
        method: 'get',
        responseType: 'blob',
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

