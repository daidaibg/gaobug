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
        component: () => import(/* webpackChunkName: "sys" */ '../views/home/home.vue'),
        meta: {
          title: "搞bug-管理系统",
          noSplice: true
        }
      },
      {
        path: '/sys/user',
        name: "/sys/user",
        component: () => import(/* webpackChunkName: "sys" */ '../views/sys/user/user.vue'),
        meta: {
          title: "用户管理",
        }
      },
      {
        path: '/sys/role',
        name: "/sys/role",
        component: () => import(/* webpackChunkName: "sys" */ '../views/sys/role/role.vue'),
        meta: {
          title: "角色管理",
        }
      },
      {
        path: '/sys/menu',
        name: "/sys/menu",
        component: () => import(/* webpackChunkName: "sys" */ '../views/sys/menu/menu.vue'),
        meta: {
          title: "菜单管理",
        }
      },
    ]
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
console.log(import.meta.env);
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL as string),
  // history: createWebHashHistory(),
  routes,
  // scrollBehavior(to, from, saveScrollPosition) {
  //   return { left: 0, top: 0 };
  // },
})


// ---------------------- 路由拦截 方法 -----------------------------------//
router.beforeEach((to, from, next) => {
  // console.log(to,from);
  let text = ' - 搞bug'
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
