import ax from '@/request/ax'

    

// 列表
export function getList(data) {
    return ax({
        url: `/devices/pages`,
        method: 'post',
        data
    })
}

// 新增
export function add(data) {
    return ax({
        url: `/devices/`,
        method: 'post',
        data
    })
}

// 修改
export function edit({ id, data }) {
    return ax({
        url: `/devices/${id}`,
        method: 'put',
        data
    })
}

// 删除
export function del({ id }) {
    return ax({
        url: `/devices/${id}`,
        method: 'delete'
    })
}

// 详情
export function getDetail({ id }) {
    return ax({
        url: `/devices/${id}`,
        method: 'get'
    })
}
// 导出
export function _export() {
    return ax({
        url: `/devices/export/`,
        method: 'get',
        responseType: 'blob',
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}
// 获取所有经销商
export function getAllDept() {
    return ax({
        url: `/sys/dept/list`,
        method: 'get'
    })
}


