import ContextMenu from "./contextmenu.vue"
import { h, render, ComponentPublicInstance } from 'vue';
import type { CustomMouseMenuOptions } from './type';
import { createClassDom } from "@/utils/current"
export function CustomMouseMenu(options: CustomMouseMenuOptions) {
    const className = '__mouse__menu__container';
    let container: HTMLElement;
    if (document.querySelector(`.${className}`)) {
        container = document.querySelector(`.${className}`) as HTMLElement;
    } else {
        container = createClassDom('div', className);
    }
    const vm = h(ContextMenu as any, options);
    render(vm, container);
    document.body.appendChild(container);
    return vm.component?.proxy as ComponentPublicInstance<typeof ContextMenu>;
}
export default ContextMenu