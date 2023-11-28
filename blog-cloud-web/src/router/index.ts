import { createRouter, createWebHashHistory, RouteRecordRaw, createWebHistory, RouterOptions } from 'vue-router'
import { getScrollContainer } from "yhht-plus/lib/utils/index"
import { RouterEnum } from "@/enums";
import { createLink } from "@/utils/dom";

const routes: Array<RouteRecordRaw> = [
  // {
  //   path: '/',
  //   // redirect: "components",
  // },
  {
    path: '/components',
    name: 'components',
    component: () => import(/* webpackChunkName: "Components" */ '../views/layout/blog-layout/components.vue'),
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
          noSplice: true,
          keepAlive: true
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
        path: RouterEnum.Tool,
        name: RouterEnum.Tool,
        component: () => import(/* webpackChunkName: "tool" */ '../views/tools/tool/index'),
        meta: {
          title: "工具",
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
    path: RouterEnum.UserHomePage + ":id",
    name: RouterEnum.UserHomePage,
    component: () => import(/* webpackChunkName: "userHomePage" */ '../views/user-home-page/user-home-page.vue'),
    meta: {
      title: "主页"
    }
  },
  {
    path: '/tools',
    name: 'tools',
    component: () => import(/* webpackChunkName: "tools" */ '../views/tools/tools-layout.vue'),
    children: [
      {
        path: RouterEnum.JsonFormat,
        name: RouterEnum.JsonFormat,
        component: () => import(/* webpackChunkName: "tools" */ '../views/tools/code-format/code-format.vue'),
        meta: {
          title: "格式化工具",
          noSplice: true
        }
      },
      {
        path: RouterEnum.ReadXlsx,
        name: RouterEnum.ReadXlsx,
        component: () => import(/* webpackChunkName: "tools" */ '../views/tools/read-xlsx/read-xlsx.vue'),
        meta: {
          title: "读取表格(xlsx)",
          noSplice: true
        }
      },
      {
        path: RouterEnum.FestivalCountDown,
        name: RouterEnum.FestivalCountDown,
        component: () => import(/* webpackChunkName: "tools" */ '../views/tools/festival-count-down/festival-count-down.vue'),
        meta: {
          title: "节日倒计时",
          noSplice: true,
          isShowHeader: true
        }
      }
      ,
      {
        path: RouterEnum.Chicken,
        name: RouterEnum.Chicken,
        component: () => import(/* webpackChunkName: "tools" */ '../views/tools/chicken/chicken.vue'),
        meta: {
          title: "只因太美",
          noSplice: true
        }
      }
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
  history:createWebHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, saveScrollPosition) {
    if (to.path !== from.path && !saveScrollPosition) {
      return { top: 0 }
    } else if (saveScrollPosition) {
      return saveScrollPosition
    } else {
      return {}
    }
  },
})
//htmlloading元素
const container_nomountDom: any = document.querySelector(".container_nomount")
// ---------------------- 路由拦截 方法 -----------------------------------//
router.beforeEach((to, from, next) => {
  let text = ' - 搞bug'
  if (to.meta.noSplice) {
    text = ''
  }
  if (to.meta.loading) {
    container_nomountDom.style.display = "inline"
  }
  document.title = to.meta.title ? `${to.meta.title}  ${text}` : '搞bug';
  if (to.path !== from.path) {
    let body: any = getScrollContainer()
    body.scrollTop = 0
  }
  next();
})
// --------------------------- 路由拦截 方法---------------------------------------------- //
router.afterEach((to, from) => {
  container_nomountDom.style.display = "none"
})
export default router
