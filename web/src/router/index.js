import Vue from 'vue';

import VueRouter from 'vue-router'
import {routes} from './routes'
// import store from '@/store'
Vue.use(VueRouter)

const router = new VueRouter({
    routes
})

// router.beforeEach((to, from, next) => {
    // if (to.fullPath !== '/login' && !store.state.auth.authority) {
        // router.push({ path: '/login' })
    // }
    // next();
// })
export default router