import {scrollTo,getScrollContainer,getScroll} from "yhht-plus/lib/utils/index"

export type TARGET_CONTAINER = HTMLElement | Window | undefined;

export const windowScrollTo=async (targetId: string,offset:number=0)=>{
    const target = document.querySelector(targetId) as HTMLElement;
    if(!target)return
    const scrollContainer = window
    const scrollTop = getScroll(scrollContainer);
    const offsetTop = getOffsetTop(target, scrollContainer);
    const top = scrollTop + offsetTop - offset;
    await scrollTo(top, {});
}


function getOffsetTop(target: HTMLElement, container: TARGET_CONTAINER): number {
    const { top } = target.getBoundingClientRect();
    if (container === window) {
        // 减去document的边框
        return top - document.documentElement.clientTop;
    }
    return top - (container as HTMLElement).getBoundingClientRect().top;
}