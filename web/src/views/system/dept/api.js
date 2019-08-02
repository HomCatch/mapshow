import ax from '@/request/ax';


// 获取所有经销商（table列表）
export async function getDeptList(data) {
  return ax({
    url: `/sys/dept/list`,
    method: 'get'
  })
}

// 获取经销商列表供选择
export async function getSelectList(data) {
  return await ax({
    url: `/sys/dept/select`,
    method: 'post',
    data: data
  })
}

// 新增经销商
export async function addDept(data) {
  return ax({
    url: `/sys/dept/save`,
    method: 'post',
    data
  })
}

// 删除经销商
export async function delDept(data) {
  return ax({
    url: `/sys/dept/delete`,
    method: 'get',
    params: data
  })
}

// 编辑经销商
export function editDept(data) {
  return ax({
    url: `/sys/dept/update`,
    method: 'post',
    data
  })
}

// 获取详情
export function viewDept({ deptId }) {
  return ax({
    url: `/sys/dept/info/${deptId}`,
    method: 'get'
  })
}
