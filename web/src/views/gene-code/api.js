import ax from '@/request/ax'

// 列表
export function getList(params) {
    return ax({
        url: `/autoCode/`,
        method: 'get',
        params
    })
}

// 生成代码
export function geneCode({ tableName, menuName, version, model }) {
    return ax({
        url: `/autoCode/run`,
        method: 'get',
        params: { tableName, menuName, version, model }
    })
}

// 新增
export function add(data) {
    return ax({
        url: `/adv/upload`,
        method: 'post',
        data
    })
}

// 修改
export function edit({ id, data }) {
    return ax({
        url: `/adv/${id}`,
        method: 'put',
        data
    })
}

// 删除
export function del({ id }) {
    return ax({
        url: `/adv/${id}`,
        method: 'delete'
    })
}

// 详情
export function getDetail({ id }) {
    return ax({
        url: `/adv/${id}`,
        method: 'get'
    })
}
// 导出
export function _export({id}){
    return ax({
        url: `/adv/export/${id}`,
        method: 'get'
    })
}
