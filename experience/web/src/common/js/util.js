export const formatterData = function (data, key = "deptId") {
    let minNum = data.reduce((res, item) => {
        return res > item.parentId ? item.parentId : res;
    }, 100)
    console.log('minNum', minNum);
    data.forEach(item => {

        let parentId = item.parentId;
        if (parentId === minNum) {
            //是根元素的hua ,不做任何操作
        } else {
            //如果item是子元素的话 ,把item扔到他的父亲的child数组中.
            data.forEach(d => {
                if (d[key] === parentId) {
                    let childArray = d.children;
                    if (!childArray) {
                        childArray = []
                    }
                    childArray.push(item);
                    d.children = childArray;
                }
            })
        }
    });
    //去除重复元素
    data = data.filter(item => item.parentId === minNum);
    return data;
}

// 依据route和menus解析当前路由的功能
export const funcsParse = function(route, menus){
      let _funcs = null;
      let path = route.fullPath.split("/");
      path.splice(0, 2);
      menus.map(item => {
        if (item.url === path[0] && item.childType === 0) {
          // 没有二级菜单
          _funcs = item.list.map(_item => _item.name);
        } else if (item.url === path[0] && item.childType === 1) {
          // 有二级菜单,遍历二级菜单
          item.list.map(subItem => {
            if (subItem.url === path[1]) {
              _funcs = subItem.list.map(_subItem => _subItem.name);
            }
          });
        }
      });
      return _funcs
}