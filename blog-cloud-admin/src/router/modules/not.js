/*
 * @Author: daidai
 * @Date: 2022-01-17 15:24:59
 * @LastEditors: daidai
 * @LastEditTime: 2022-02-08 15:56:25
 * @FilePath: \web-pc\src\router\modules\not.js
 */
export default [
{
    path: '/404',
    name: '404',
    component: () => import(/* webpackChunkName: "SM.home" */ '../../views/404.vue'),
    meta: {
        title: '404',
    }
},
{
    path: '/403',
    name: '403',
    component: () => import(/* webpackChunkName: "SM.home" */ '../../views/403.vue'),
    meta: {
        title: '无权限',
    }
},
]