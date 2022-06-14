/*
 * @Author: daidai
 * @Date: 2021-12-07 17:33:43
 * @LastEditors: daidai
 * @LastEditTime: 2022-02-08 15:57:00
 * @FilePath: \web-pc\src\router\menu.js
 */
import { isAuth } from '@/lib/currency'
const modulesFiles = require.context('./modules', true, /\.js$/)
const modules = modulesFiles.keys().reduce((modules, modulePath) => {
//   const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1');
  const value = modulesFiles(modulePath)
  modules= modules.concat(value.default)
  return modules;
}, [])
// console.log(modules)
export const localMenuLIst = [

    ...modules,
    {
	    path: '/home',
	    name: '/home',
	    component: () => import(/* webpackChunkName: "SM.home" */'../views/index/index.vue'),
	    meta: {
	        title: '首页',
	        authorMenu: true
	    }
	},

    //*******************************系统管理 *****************************************/
    {
        path: '/sys/menu',
        name: '/sys/menu',
        component: () => import(/* webpackChunkName: "SM.sys" */'../views/sys/menus/base-menu.vue'),
        meta: {
            title: '菜单管理',
            authorMenu: true
        }
    },
    {
        path: '/sys/user',
        name: '/sys/user',
        component: () => import(/* webpackChunkName: "SM.sys" */'../views/sys/user/base-user.vue'),
        meta: {
            title: '用户管理',
            authorMenu: true
        }
    },
    {
        path: '/sys/role',
        name: '/sys/role',
        component: () => import(/* webpackChunkName: "SM.sys" */'../views/sys/role/base-role.vue'),
        meta: {
            title: '角色管理',
            authorMenu: true
        }
    },

    {
        path: '/sys/log',
        name: '/sys/log',
        component: () => import(/* webpackChunkName: "SM.sys" */'../views/sys/logs/logs.vue'),
        meta: {
            title: '操作记录',
            authorMenu: true
        }
    },
    {
        path: '/sys/sjbgl',
        name: '/sys/sjbgl',
        component: () => import(/* webpackChunkName: "SM.sys" */'../views/sys/upgrade-package/upgrade-package.vue'),
        meta: {
            title: '升级包管理',
            authorMenu: true
        }
    },
    /*******************************台账管理 *****************************************/
    
    {
        path: '/sys/region',
        name: '/sys/region',
        component: () => import(/* webpackChunkName: "SM.tzgl" */'../views/sys/region/region.vue'),
        meta: {
            title: '区域管理',
            authorMenu: true
        }
    },
    {
        path: '/sys/company',
        name: '/sys/company',
        component: () => import(/* webpackChunkName: "SM.tzgl" */'../views/sys/company/company.vue'),
        meta: {
            title: '公司管理',
            authorMenu: true
        }
    },
    {
        path: '/tzgl/device',
        name: '/tzgl/device',
        component: () => import(/* webpackChunkName: "SM.tzgl" */'../views/tzgl/device/device.vue'),
        meta: {
            title: '设备管理',
            authorMenu: true
        }
    },
    {
        path: '/tzgl/line',
        name: '/tzgl/line',
        component: () => import(/* webpackChunkName: "SM.tzgl" */'../views/tzgl/line/line.vue'),
        meta: {
            title: '设备管理',
            authorMenu: true
        }
    },
   
  ]
  

