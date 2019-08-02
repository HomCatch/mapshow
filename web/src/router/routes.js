import Layout from "@/layout/index";
export const routes = [
         { path: "/", redirect: "/start" },
         {
           path: "/login",
           component: () => import("@/views/login/login")
         },
         {
           path: "/register",
           component: () => import("@/views/register/register")
         },
         {
           path: "/forgetPwd",
           component: () => import("@/views/forgetPwd/forgetPwd")
         },
         {
           path: "/setNewPwd",
           component: () => import("@/views/setNewPwd/setNewPwd")
         },
         {
           path: "/findPwd",
           component: () => import("@/views/findPwd/findPwd")
         },
         {
           path: "/regUser",
           component: () => import("@/views/regUser/regUser")
         },
         {
           path: "/backToLogin",
           component: () => import("@/views/backToLogin/backToLogin")
         },
         {
           path: "/start",
           component: () => import("@/views/start/start")
         },
         // { path: '/print', component: () => import("@/components/z-print/z-print") },
         {
           path: "/content",
           name: "导航",
           component: Layout,
           redirect: "/content/waterMap",
           children: [
             {
               path: "gene-code",
               name: "代码生成",
               component: () => import("@/views/gene-code/gene-code")
             },
             // { path: 'gene-code1', name: '代码生成1', component: () => import("@/views/gene-code.1/gene-code.1") },
             // 代码赋值了之后，在这里加一下路由就行了
             {
               path: "dashboard",
               name: "系统总览",
               component: () =>
                 import("@/views/device/dashboard/dashboard")
             },
             {
               path: "waterMap",
               name: "水质地图",
               component: () => import("@/views/bdmap/bdmap")
             },
             {
               path: "filter-analysis",
               name: "滤芯报警管理",
               component: () => import("@/views/filter-analysis/index")
             },
             {
               path: "device",
               redirect: "device/dashboard",
               component: () => import("@/layout/main"),
               name: "设备管理",
               children: [
                 {
                   path: "details",
                   name: "水质详情",
                   component: () =>
                     import("@/views/device/detail/detail")
                 },
                 {
                   path: "devPandect",
                   name: "设备总览",
                   component: () =>
                     import("@/views/device/devPandect/devPandect")
                 },
                 {
                   path: "devMng",
                   name: "设备管理",
                   component: () =>
                     import("@/views/device/devMng/devMng")
                 },
                 {
                   path: "change_list",
                   name: "滤芯更换计划",
                   component: () =>
                     import("@/views/device/changelist/changeList")
                 }
               ]
             },
             {
               path: "user_info",
               name: "APP用户信息",
               component: () => import("@/views/userinfo/userInfo")
             },
             {
               path: "order",
               component: () => import("@/layout/main"),
               name: "订单管理",
               children: [
                 {
                   path: "cloudwaterdeposit",
                   name: "押金订单",
                   component: () =>
                     import(
                       "@/views/order/cloudwaterdeposit/cloudWaterDeposit"
                     )
                 },
                 {
                   path: "cloudwaterorder",
                   name: "用户账单",
                   component: () =>
                     import(
                       "@/views/order/cloudwaterorder/cloudWaterOrder"
                     )
                 }
               ]
             },
             {
               path: "system",
               redirect: "system/role",
               component: () => import("@/layout/main"),
               name: "系统设置",
               children: [
                 {
                   path: "role",
                   name: "角色管理",
                   component: () => import("@/views/system/role/role")
                 },
                 {
                   path: "account",
                   name: "用户管理",
                   component: () =>
                     import("@/views/system/account/account")
                 },
                 {
                   path: "dept",
                   name: "经销商管理",
                   component: () => import("@/views/system/dept/dept")
                 }
               ]
             },
             {
               path: "demo_registermng",
               redirect: "demo_registermng/registermng",
               component: () => import("@/layout/main"),
               name: "Demo申请管理",
               children: [
                 {
                   path: "registermng",
                   name: "申请者信息",
                   component: () =>
                     import("@/views/registermng/register")
                 }
               ]
             },
             {
               path: "log",
               redirect: "log/loginLog",
               component: () => import("@/layout/main"),
               name: "日志管理",
               children: [
                 {
                   path: "loginLog",
                   name: "登陆日志",
                   component: () =>
                     import("@/views/log/loginLog/loginLog")
                 },
                 {
                   path: "operateLog",
                   name: "操作日志",
                   component: () =>
                     import("@/views/log/operateLog/operateLog")
                 },
                 {
                   path: "errorLog",
                   name: "异常日志",
                   component: () =>
                     import("@/views/log/errorLog/errorLog")
                 }
               ]
             },
             {
               path: "sql",
               name: "sql监控",
               component: () => import("@/views/frame/frame")
             },
             {
               path: "dataStat",
               redirect: "dataStat/waterData",
               name: "数据统计",
               component: () => import("@/layout/main"),
               children: [
                 {
                   path: "waterData",
                   name: "水质数据",
                   component: () =>
                     import("@/views/dataStat/waterData/waterData")
                 },
                 {
                   path: "devData",
                   name: "设备数据",
                   component: () =>
                     import("@/views/dataStat/devData/devData")
                 },
                 {
                   path: "userData",
                   name: "用户数据",
                   component: () =>
                     import("@/views/dataStat/userData/userData")
                 },
                 {
                   path: "userImg",
                   name: "用户画像",
                   component: () =>
                     import("@/views/dataStat/userImg/userImg")
                 }
               ]
             },
             {
               path: "serviceMng",
               name: "服务管理",
               component: () => import("@/layout/main")
             },
             {
               path: "leaseMng",
               name: "租赁管理",
               component: () => import("@/layout/main")
             },
             {
               path: "financialMng",
               name: "财务管理",
               component: () =>
                 import("@/views/financialMng/financialMng")
             },
             {
               path: "set",
               name: "设置",
               component: () => import("@/layout/main")
             }
           ]
         }
       ];
