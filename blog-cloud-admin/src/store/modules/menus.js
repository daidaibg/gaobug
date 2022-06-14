/*
 * @Author: daidai
 * @Date: 2021-12-06 11:01:16
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-05 11:58:47
 * @FilePath: \web-pc\src\store\modules\menus.js
 */

export default {
    state: () => ({
        menuList: [],//侧边栏列表

    }),
    getters: {
        //根据菜单路径获取 菜单信息

    },
    mutations: {
        updateMenuList(state, list) {
            state.menuList = list
        },

    },
    actions: {
       
    },
}