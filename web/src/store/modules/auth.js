import { getNav } from '../api'
const auth = {
    state: {
        menus: [],
        funcs: [],   // 是否登录
    },
    mutations: {
        setMenus(state, menuList){
            state.menus = menuList;
        },
        // 切换router时设置funcs
        // setFuncs(state, menuList){
            // state.funcs = 
        // }
    },
    actions: {
        getNav({commit}){
            // 加载left组件时ajax获取权限导航同时设置导航菜单并设置funcs
            getNav().then(res => {
                console.log(res)
                if(res.code === 0){
                    commit('setMenus', res.menuList);
                    // commit('setFuncs', res.menuList);
                }
            })
        }
    }
}

export default auth