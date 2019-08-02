import ax from '@/request/ax'

// 列表
export function getList(data) {
    return ax({
        url: `/registers/pages`,
        method: 'post',
        data
    })
}

// 新增
export function add(data) {
    return ax({
        url: `/registers/`,
        method: 'post',
        data
    })
}

// 修改
export function edit({ id, data }) {
    return ax({
        url: `/registers/${id}`,
        method: 'put',
        data
    })
}

// 删除
export function del({ userId }) {
    return ax({
        url: `/registers/${userId}`,
        method: 'delete'
    })
}

// 详情
export function getDetail({ id }) {
    return ax({
        url: `/registers/${id}`,
        method: 'get'
    })
}
// 导出
export function _export({ id }) {
    return ax({
        url: `/registers/export/${id}`,
        method: 'get',
        responseType: 'blob',
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

