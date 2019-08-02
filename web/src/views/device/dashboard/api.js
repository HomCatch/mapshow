import ax from '@/request/ax'

// 所有类型
export function getTypeList() {
    return ax({
        url: `/dashboardPropertys/list`,
        method: 'get'
    })
}

// 新增面板
export function addPanel(data) {
    return ax({
        url: `/dashboardInstances/saveClick`,
        method: 'post',
        data
    })
}

// 删除面板
export function delPanel({id}){
    return ax({
        url: `/dashboardInstances/${id}`,
        method: 'delete'
    })
}

// 获取dashboard布局属性
export function dashboardLayout() {
    return ax({
        url: `/dashboardInstances/pages`,
        method: 'post',
        data: {}
    })
}

// 提交布局属性
export function commitDashboardLayout(data) {
    return ax({
        url: `/dashboardInstances`,
        method: 'post',
        data
    })
}

// 设备总览
export function devOverview() {
    return ax({
        url: `/devices/pandect`,
        method: 'post',
        data
    })
}