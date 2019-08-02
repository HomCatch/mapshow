import Layout from '@/layout/index';
export const routes = [
    { path: '/', redirect: '/content' },
    { path: '/login', component: () => import("@/views/login/login") },
    // { path: '/print', component: () => import("@/components/z-print/z-print") },
    {
        path: '/content', name: '首页', component: Layout, redirect: '/content/member', children: [
            // { path: 'gene-code', name: '代码生成', component: () => import("@/views/gene-code/gene-code") },
            { path: 'member', name: '商户管理', component: () => import("@/views/member/member") },
            // { path: 'gene-code1', name: '代码生成1', component: () => import("@/views/gene-code.1/gene-code.1") },
            // 代码赋值了之后，在这里加一下路由就行了

            {
                path: 'system', redirect: 'system/role', component: () => import("@/layout/main"), name: '系统设置', children: [
                    { path: 'role', name: '角色管理', component: () => import("@/views/system/role/role") },
                    { path: 'account', name: '用户管理', component: () => import("@/views/system/account/account") },
                    { path: 'dept', name: '部门管理', component: () => import("@/views/system/dept/dept") }
                ]
            },
            {
                path: 'log', redirect: 'log/loginLog', component: () => import("@/layout/main"), name: '日志管理', children: [
                    { path: 'loginLog', name: '登陆日志', component: () => import("@/views/log/loginLog/loginLog") },
                    { path: 'operateLog', name: '操作日志', component: () => import("@/views/log/operateLog/operateLog") },
                    { path: 'errorLog', name: '异常日志', component: () => import("@/views/log/errorLog/errorLog") }
                ]
            },
           
        ]
    }
]