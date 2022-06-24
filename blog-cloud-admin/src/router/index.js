/*
 * @Author: daidai
 * @Date: 2021-12-06 10:52:57
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2022-05-10 18:03:49
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
import { localMenuLIst } from './menu'
import { currentGET } from 'api/modules'
import {sysName} from '@/constants'
import store  from '@/store'
import { treeDataTranslate } from '@/lib/currency'


Vue.use(VueRouter)
// //获取原型对象上的push函数
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
};
const routes = [
  {
    path: '/',
    redirect: '/home',
  },
  {
    path: '/login',
    name: 'login',
    component: () => import(/* webpackChunkName: "SM.login" */ '../views/Login/Login.vue'),
  }, 
  {
    path: '/retrieve',
    name: 'retrieve',
    component: () => import(/* webpackChunkName: "SM.login" */ '../views/Login/Retrieve.vue'),
  },
  {
    path: '/pubilc-home',
    name: 'pubilc-home',
    component: () => import(/* webpackChunkName: "SM.pubilc-home" */'../views/manage-home/manage-main.vue'),
    children: localMenuLIst
  },
  {
    path: '*',
    redirect: '/404'
  }
]

const router = new VueRouter({
  mode: 'history',   // 模式，默认hash
  // base: '/', // 基础路径
  routes,
  isAddDynamicMenuRoutes: false, // 是否已经获取到菜单
})
// console.log(router)
//无法直接访问的非权限页
// Non permission page that cannot be accessed directly
const NPPTCBAD = []
router.beforeEach((to, from, next) => {
  document.title = `${to.meta.title ? to.meta.title + '-' + sysName : sysName} `;
  if (!to.meta.authorMenu && !(NPPTCBAD.includes(to.path))) {
    next()
  } else if (router.options.isAddDynamicMenuRoutes) {
    routerTo(next, to)
    //是否是权限菜单
  } else {
    next()
    return
    currentGET(14).then(res => {
    
      if (res.success) {
      
      // console.log(res)
        router.options.isAddDynamicMenuRoutes = true;
        // let newMenu = treeDataTranslate(
        //   res.data.menuList,
        //   'menuId',
        //   'parentId',
        //   'orderNum'
        // )
        let newMenu = res.data.menuList;
        // console.log('%c!<-------------------- 菜单 s -------------------->', 'color:blue')
        // console.log(newMenu)
        // console.log('%c!<-------------------- 菜单 e -------------------->', 'color:blue')
        store.commit('menus/updateMenuList', newMenu)
        sessionStorage.setItem('menuList', JSON.stringify(newMenu))
        sessionStorage.setItem('permissions', JSON.stringify(res.data.permissions || []));
        routerTo(next, to)
      } else {
        sessionStorageKong()
        next({path:"/login"})
      }
    })
      .catch(err => {
        sessionStorageKong()
        next({path:"/login"})
      })
  }

});

const routerTo = (next, to) => {
  if (to.meta.authorMenu) {
    next()
  } else {
    next()
  }
}
//接口出线错误设置空菜单 
const sessionStorageKong = () => {
  store.commit('menus/updateMenuList', [])
  sessionStorage.setItem('menuList', JSON.stringify([]))
  sessionStorage.setItem('permissions', JSON.stringify([]));
}
export default router
