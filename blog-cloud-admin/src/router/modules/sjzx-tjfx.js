/*
 * @Author: daidai
 * @Date: 2022-01-18 09:59:11
 * @LastEditors: daidai
 * @LastEditTime: 2022-02-08 16:18:23
 * @FilePath: \web-pc\src\router\modules\sjzx-tjfx.js
 */

export default [
    /*******************************数据中心 *****************************************/
    {
        path: '/data-center/wgsshjsj',
        name: '/data-center/wgsshjsj',
        component: () => import(/* webpackChunkName: "SM.sjzx" */'@/views/data-center/device-data/device-data.vue'),
        meta: {
            title: '网关实时数据',
            authorMenu: true
        }
    },
    {
        path: '/data-center/terminal-data',
        name: '/data-center/terminal-data',
        component: () => import(/* webpackChunkName: "SM.sjzx" */'@/views/data-center/terminal-data/terminal-data.vue'),
        meta: {
            title: '终端实时数据',
            authorMenu: true
        }
    },
    {
        path: '/data-center/terminal-history-data',
        name: '/data-center/terminal-history-data',
        component: () => import(/* webpackChunkName: "SM.sjzx" */'@/views/data-center/terminal-data/terminal-history-data.vue'),
        meta: {
            title: '终端历史数据',
            authorMenu: true
        }
    },
    {
        path: '/data-center/warning-data',
        name: '/data-center/warning-data',
        component: () => import(/* webpackChunkName: "SM.sjzx" */'@/views/data-center/warning-data/warning-data.vue'),
        meta: {
            title: '告警数据',
            authorMenu: true
        }
    },
    {
        path: '/data-center/communi-data',
        name: '/data-center/communi-data',
        component: () => import(/* webpackChunkName: "SM.sjzx" */'@/views/data-center/communi-data/communi-data.vue'),
        meta: {
            title: '通信数据',
            authorMenu: true
        }
    },
    {
        path: '/data-center/device-history-data',
        name: '/data-center/device-history-data',
        component: () => import(/* webpackChunkName: "SM.sjzx" */'@/views/data-center/device-data/device-history-data.vue'),
        meta: {
            title: '网关历史数据',
            authorMenu: true
        }
    },
    {
        path: '/sys/device-alarm',
        name: '/sys/device-alarm',
        component: () => import(/* webpackChunkName: "SM.sjzx" */'@/views/sys/device-alarm/device-alarm.vue'),
        meta: {
            title: '告警数据',
            authorMenu: true
        }
    },
    /*******************************统计分析 *****************************************/

    {
        path: '/statistics/number-statistics',
        name: '/statistics/number-statistics',
        component: () => import(/* webpackChunkName: "SM.tjfx" */'@/views/statistics/number-statistics/number-statistics.vue'),
        meta: {
            title: '雷击次数统计',
            authorMenu: true
        }
    },

    {
        path: '/statistics/surroundings-statistics',
        name: '/statistics/surroundings-statistics',
        component: () => import(/* webpackChunkName: "SM.tjfx" */'@/views/statistics/surroundings-statistics/surroundings-statistics'),
        meta: {
            title: '环境数据分析',
            authorMenu: true
        }
    },
    {
        path: '/statistics/lightning-statistics',
        name: '/statistics/lightning-statistics',
        component: () => import(/* webpackChunkName: "SM.tjfx" */'@/views/statistics/lightning-statistics/lightning-statistics'),
        meta: {
            title: '雷击强度分析',
            authorMenu: true
        }
    },
    {
        path: '/statistics/leakage-statistics',
        name: '/statistics/leakage-statistics',
        component: () => import(/* webpackChunkName: "SM.tjfx" */'@/views/statistics/leakage-statistics/leakage-statistics'),
        meta: {
            title: '漏电电流分析',
            authorMenu: true
        }
    },
]