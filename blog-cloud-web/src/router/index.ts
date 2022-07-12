import { createRouter, createWebHashHistory, RouteRecordRaw, createWebHistory } from 'vue-router'
import { getScrollContainer } from "yhht-plus/packages/utils/dom"
const routes: Array<RouteRecordRaw> = [
  // {
  //   path: '/',
  //   // redirect: "components",

  // },
  {
    path: '/components',
    name: 'components',
    component: () => import(/* webpackChunkName: "Components" */ '../views/components.vue'),
    children: [
      {
        path: '/article/details/:id',
        name: "/article/details",
        component: () => import(/* webpackChunkName: "Components" */ '../views/article-details/article-details.vue'),
        meta: {
          title: "详情"
        }
      },
      {
        path: '/',
        name: "/",
        component: () => import(/* webpackChunkName: "Components" */ '../views/home/home.vue'),
        meta: {
          title: "搞bug",
          noSplice: true
        }
      },
      {
        path: '/message/board',
        name: "/message/board",
        component: () => import(/* webpackChunkName: "Components" */ '../views/nav-page/message-board'),
        meta: {
          title: "留言板",
          noSplice: true
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
  {
    path: '/write/essay',
    name: '/write/essay',
    component: () => import(/* webpackChunkName: "Components" */ '../views/write-essay/write-essay.vue'),
    meta: {
      title: "写文章"
    }
  },


  {
    path: '/blogs/manage',
    name: '/blogs/manage',
    redirect: "/blogs/manage/article",
    component: () => import(/* webpackChunkName: "blog-manage" */ '../views/blog-manage/blog-manage.vue'),
    meta: {
      title: "文章管理"
    },
    children: [
      {
        path: '/blogs/manage/article',
        name: "/blogs/manage/article",
        component: () => import(/* webpackChunkName: "blog-manage" */ '../views/blog-manage/article/articles.vue'),
      }
    ],
  },

  {
    path: '/user/setting',
    name: 'user/setting',
    redirect: "/user/setting/profile",
    component: () => import(/* webpackChunkName: "user" */ '../views/user/settting/index'),
    meta: {
      title: "用户管理"
    },
    children: [
      {
        path: '/user/setting/profile',
        name: "/user/setting/profile",
        component: () => import(/* webpackChunkName: "user" */ '../views/user/settting/profile/index'),
        meta: {
          title: "个人资料"
        },
      },
      {
        path: '/user/setting/account',
        name: "/user/setting/account",
        component: () => import(/* webpackChunkName: "user" */ '../views/user/settting/account'),
        meta: {
          title: "账号设置"
        },
      }
    ],
  }
]

const router = createRouter({
  history: createWebHistory('/'),
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
