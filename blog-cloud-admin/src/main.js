/*
 * @Author: daidai
 * @Date: 2021-12-06 10:52:57
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2022-05-09 11:18:05
 */
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import {isAuth,rowClassName,tableHeight} from '@/lib/currency'
// import ElementUI from 'element-ui';
import './directives/element-com'
import yhhtUi from 'yhht-ui';
import yhAndEleUi from 'yhht-ui/src/link-el'
import 'yhht-ui/lib/theme/index.css';
import "./assets/css/index.scss"
import './directives/directives';
import * as filters from './directives/filters'





Vue.config.productionTip = false
Vue.prototype.bus = new Vue()
Vue.prototype.isAuth = isAuth     // 权限方法
Vue.prototype.rowClassName = rowClassName  //表格每一行名字
Vue.prototype.$tableHeight = tableHeight
// Vue.use(ElementUI);


// 全局注册过滤器
Object.keys(filters).forEach(k => Vue.filter(k, filters[k]));

Vue.use(yhhtUi).use(yhAndEleUi) 
let app= new Vue({
  router,
  store,
  render: function (h) { return h(App) }
}).$mount('#app')
export default app


