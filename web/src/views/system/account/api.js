import ax from '@/request/ax';

// 获取所有用户
export function users(params){
    return ax({
        url: `/sys/user/list`,
        method: 'get',
        params
    })
}

// 新增用户
export function addUser(data){
    return ax({
        url: `/sys/user/save`,
        method: 'post',
        data
    })
}

// 修改用户
export function editUser(data){
    return ax({
        url: `/sys/user/update`,
        method: 'post',
        data
    })
}

// 删除用户
export function delUser(data){
    return ax({
        url: `/sys/user/delete`,
        method: 'post',
        data
    })
}

// 获取用户详情
export function getUser({user_id}){
    return ax({
        url: `sys/user/info/${user_id}`,
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
