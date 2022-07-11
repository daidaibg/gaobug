import { createRouter, createWebHashHistory, RouteRecordRaw, createWebHistory } from 'vue-router'
import { getScrollContainer } from "yhht-plus/packages/utils/dom"
const routes: Array<RouteRecordRaw> = [
  {
    path:"/",
    redirect:"/home"
  },
  {
    path: '/components',
    name: 'components',
    component: () => import(/* webpackChunkName: "Components" */ '../views/layouts'),
    children: [
     
      {
        path: '/home',
        name: "/home",
        component: () => import(/* webpackChunkName: "Components" */ '../views/home/home.vue'),
        meta: {
          title: "搞bug-管理系统",
          noSplice: true
        }
      },
      {
        path: '/sys/user',
        name: "/sys/user",
        component: () => import(/* webpackChunkName: "Components" */ '../views/sys/user/user.vue'),
        meta: {
          title: "用户管理",
        }
      },
      {
        path: '/login',
        name: '/login',
        component: () => import(/* webpackChunkName: "login" */ '../views/login/login.vue'),
        meta: {
          title: "登录"
        }
      },
      {
        path: '/register',
        name: '/register',
        component: () => import(/* webpackChunkName: "login" */ '../views/login/register/index'),
        meta: {
          title: "注册"
        }
      },
    ]
  },




]

const router = createRouter({
  history: createWebHistory('/admin/'),
  // history: createWebHashHistory(),
  routes,
  // scrollBehavior(to, from, saveScrollPosition) {
  //   return { left: 0, top: 0 };
  // },
})


// ---------------------- 路由拦截 方法 -----------------------------------//
router.beforeEach((to, from, next) => {
  // console.log(to,from);
  let text = ' | 搞bug'
  if (to.meta.noSplice) {
    text = ''
  }
  document.title = to.meta.title ? `${to.meta.title}  ${text}` : '搞bug';
  if (to.path !== from.path) {
    let body: any = getScrollContainer()
    body.scrollTop = 0
  }
  next();
})
// --------------------------- 路由拦截 方法---------------------------------------------- //

export default router
