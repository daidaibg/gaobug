/*
 * @Author: your name
 * @Date: 2021-07-26 09:32:49
 * @LastEditTime: 2021-12-16 16:51:44
 * @LastEditors: daidai
 * @Description: In User Settings Edit
 * @FilePath: \web-pc\src\store\index.js
 */
import Vuex from 'vuex';
import Vue from 'vue';

Vue.use(Vuex)
const modulesFiles = require.context('./modules', true, /\.js$/)
const modules = modulesFiles.keys().reduce((modules, modulePath) => {
  const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1')
  const value = modulesFiles(modulePath)
  modules[moduleName]=value.default
  modules[moduleName].namespaced = true;   //打开命名空间
  return modules
}, {})
/*
  reduce callback函数第一个参数是上一次回调函数的返回值，或者提供的初始值（initialValue）
  callback函数第二个参数是当前正在处理的元素
  reduce方法第二个参数写的是[]，modules就是数组。
  reduce方法第二个参数写的是{}，modules就是对象。
*/
export default new Vuex.Store({
  modules,
  state: {
    collapsed:false,
    userData:{},
    loading:false
  },
  mutations: {
      setCollapsed(state,value){
        state.collapsed =!state.collapsed
      },
      //用户数据
      setUserData(state,value){
        state.userData = value;
      },
      setLoading(state,loading){
        state.loading = loading;
      },
  },
  actions: {
    getUserdata({commit}){

    },
    
  },
  
})
