/*
 * @Author: daidai
 * @Date: 2021-12-10 16:19:46
 * @LastEditors: daidai
 * @LastEditTime: 2021-12-10 16:31:20
 * @FilePath: \web-pc\src\directives\eltable-height.js
 */

import Vue from 'vue';

// element 表格高度问题
Vue.directive('eltable-height', {
    bind(el, binding, vnode, oldVnode) {
        // console.log( binding, vnode, oldVnode)
        // const bodyWrapEl = el.querySelector('.el-table__body-wrapper');
        // console.log(bodyWrapEl,bodyWrapEl.offsetTop,el.offsetTop)
        // console.dir(bodyWrapEl)
        // 获取原有属性 ie dom元素.currentStyle 火狐谷歌 window.getComputedStyle(dom元素, null);
        
    },
    update (el){
        // const bodyWrapEl = el.querySelector('.el-table__body-wrapper');
        // console.log(bodyWrapEl,bodyWrapEl.offsetTop,el.offsetTop)
        // console.dir(bodyWrapEl)
    }
})
