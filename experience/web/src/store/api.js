import http from '@/request/ax';


// 获取菜单项
export function getNav(params) {
    return http({
        url: '/sys/menu/nav',
        method: 'get',
        params
    })
}