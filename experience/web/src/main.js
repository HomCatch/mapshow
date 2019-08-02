import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import "@/common/css/index.scss";
Vue.use(ElementUI, {size: 'small', zIndex: 3000 });
Vue.config.productionTip = false

new Vue({
  router,
  store,
  el: '#app',
  render: h => h(App)
});
