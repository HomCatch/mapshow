import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import app from './modules/app'
import auth from './modules/auth'

const store = new Vuex.Store({
    state: {

    },
    mutations: {

    },
    modules: {
        app, auth
    }
})

export default store