import ax from '@/request/ax'

// 列表
export function getList(data) {
    return ax({
        url: `/changeLists/pages`,
        method: 'post',
        data
    })
}

// 新增
export function add(data) {
    return ax({
        url: `/changeLists/`,
        method: 'post',
        data
    })
}

// 修改
export function edit({ id, data }) {
    return ax({
        url: `/changeLists/${id}`,
        method: 'put',
        data
    })
}

// 删除
export function del({ id }) {
    return ax({
        url: `/changeLists/${id}`,
        method: 'delete'
    })
}

// 详情
export function getDetail({ id }) {
    return ax({
        url: `/changeLists/${id}`,
        method: 'get'
    })
}
// 导出
export function _export() {
    return ax({
        url: `/changeLists/export/`,
        method: 'get',
        responseType: 'blob',
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

// 生成
export function gene(){
    return ax({
        url: `/filterLists/gen_replace_plan`,
        method: 'get'
    })
}