# Xplatform后台模板

## 项目依赖安装
```
npm install
```

### 启动
```
npm run serve（若有需要可在config.js内修改接口地址）
```

### 打包
```
npm run build
```

### 自动生成代码功能
#### 生成代码比如`order`
##### 前端操作：
1. 进入生成代码菜单，选择页面依赖表，点击生成代码。
2. 将`E:\temps\com\xiaohe\xplatform\modules\orders\js\order`目录拷贝到前端项目`src/views`下，然后在`src/route/routes.js`中添加该路由 `{ path: 'orders', name: '订单列表', component: () => import("@/views/orders/orders") }`
##### 后台操作：
1. `E:\temps\com\xiaohe\xplatform\modules`目录下对应文件拷贝至后台项目`src/main/java/com.xiaohe.xplatform/modules`下，然后删除`js/`和`sql/`两个目录
##### 数据库操作：

###


### 
