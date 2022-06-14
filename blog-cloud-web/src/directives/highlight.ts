import type { App } from "vue";

import hljs from 'highlight.js';


export default function setHighlight(app: App) {
  app.directive('highlight', {
    //指令有一组生命周期挂钩：
    //在装入绑定元素的父组件之前调用
    beforeMount(el: any) {
      let blocks = el.querySelectorAll('pre code');
      for (let i = 0; i < blocks.length; i++) {
        let item = blocks[i]
        hljs.highlightElement(item)

      }
    },
    // called after the containing component's VNode and the VNodes of its children // have updated
    updated(el: any, binding: any) {
      let targets = el.querySelectorAll('pre code');
      for (let i = 0; i < targets.length; i += 1) {
        let target = targets[i];
        if (typeof binding.value === 'string') {
          target.textContent = binding.value;
        }
        hljs.highlightElement(target);

      }
    }
  })
}
