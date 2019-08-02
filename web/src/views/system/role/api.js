import ax from '@/request/ax';

// 获取所有角色
export function roleList(params) {
    return ax({
        url: `/sys/role/list`,
        method: 'get',
        params
    })
}

// 获取角色详情
export function role({ roleId }) {
    return ax({
        url: `/sys/role/info/${roleId}`,
        method: 'get'
    })
}

// 新增角色
export function addRole(data) {
    return ax({
        url: `/sys/role/save`,
        method: 'post',
        data
    })
}

// 修改角色
export function editRole(data) {
    return ax({
        url: `/sys/role/update`,
        method: 'post',
        data
    })
}

// 删除角色
export function delRole(data) {
    return ax({
        url: `/sys/role/delete`,
        method: 'post',
        data
    })
}

// 获取所有菜单
export function getAllMenu() {
    return ax({
        url: `/sys/menu/list`,
        method: 'get'
    })
}
// 获取所有经销商
export function getAllDept() {
    return ax({
        url: `/sys/dept/list`,
        method: 'get'
    })
}
