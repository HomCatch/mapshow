import http from '@/request/ax';

// 退出
export function logout(params) {
    return http({
        url: '/access/logout',
        method: 'get',
        params
    })
}