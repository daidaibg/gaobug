<script setup lang='ts'>
import { nextTick, onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter, onBeforeRouteUpdate, RouteLocationNormalized } from 'vue-router'
import { viewMenu } from '@/store/interface'
import { useTemplateRefsList } from '@vueuse/core'
import horizontalScroll from '@/utils/horizontalScroll'
import { findMenu } from "./nav-tab-fun"
import { useMenuNavStore } from "@/store"
const route = useRoute()
const router = useRouter()

const menuNavStore = useMenuNavStore()
const tabScrollbarRef = ref()
const tabsRefs = useTemplateRefsList<HTMLDivElement>()

const contextmenuRef = ref()

const state: {
    contextmenuItems: any[]
} = reactive({
    contextmenuItems: [
        { name: 'refresh', label: '重新加载', icon: 'fa fa-refresh' },
        { name: 'close', label: '关闭标签', icon: 'fa fa-times' },
        { name: 'fullScreen', label: '当前标签全屏', icon: 'el-icon-FullScreen' },
        { name: 'closeOther', label: '关闭其他标签', icon: 'fa fa-minus' },
        { name: 'closeAll', label: '关闭全部标签', icon: 'fa fa-stop' },
    ],
})

const activeBoxStyle = reactive({
    width: '0',
    transform: 'translateX(0px)',
})
const navTabs = ref<any>({
    // 激活tab的index
    activeIndex: 0,
    // 激活的tab
    activeRoute: null,
    // tab列表
    tabsView: [],
})
const onTab = (menu: viewMenu) => {
    router.push({ path: menu.path })
}

const onContextmenu = (menu: viewMenu, el: MouseEvent) => {
    // 禁用刷新
    state.contextmenuItems[0].disabled = route.path !== menu.path
    // 禁用关闭其他和关闭全部
    state.contextmenuItems[4].disabled = state.contextmenuItems[3].disabled = navTabs.state.tabsView.length == 1 ? true : false

    const { clientX, clientY } = el
    contextmenuRef.value.onShowContextmenu(menu, {
        x: clientX,
        y: clientY,
    })
}

// tab 激活状态切换
const selectNavTab = function (dom: HTMLDivElement) {
    if (!dom) {
        return false
    }
    activeBoxStyle.width = dom.clientWidth + 'px'
    activeBoxStyle.transform = `translateX(${dom.offsetLeft}px)`
    let scrollLeft = dom.offsetLeft + dom.clientWidth - tabScrollbarRef.value.clientWidth
    if (dom.offsetLeft < tabScrollbarRef.value.scrollLeft) {
        tabScrollbarRef.value.scrollTo(dom.offsetLeft, 0)
    } else if (scrollLeft > tabScrollbarRef.value.scrollLeft) {
        tabScrollbarRef.value.scrollTo(scrollLeft, 0)
    }
}

const toLastTab = () => {
    const lastTab = navTabs.value.tabsView.slice(-1)[0]
    if (lastTab) {
        router.push(lastTab.path)
    } else {
        router.push('/home')
    }
}
//关闭一个标签
const closeTab = (route: viewMenu, idx: number) => {

    navTabs.value.tabsView.splice(idx, 1)
    if (navTabs.value.activeRoute?.path === route.path) {
        toLastTab()
    } else {
        setActiveRoute(navTabs.value.activeRoute as viewMenu)
        nextTick(() => {
            selectNavTab(tabsRefs.value[navTabs.value.activeIndex])
        })
    }

    // contextmenuRef.value.onHideContextmenu()
}

const closeAllTab = () => {
    // navTabs.closeTabs(false)
    // let firstRoute = getFirstRoute(navTabs.state.tabsViewRoutes)
    // if (firstRoute) routePush(firstRoute.name)
}
// 设置当前选中路由标签
const setActiveRoute = (route: RouteLocationNormalized | viewMenu, currentRoute?: any): void => {
    const currentRouteIndex: number = navTabs.value.tabsView.findIndex((item: viewMenu) => {
        return item.path === route.path
    })
    if (currentRouteIndex === -1) return
    navTabs.value.activeRoute = currentRoute
    navTabs.value.activeIndex = currentRouteIndex
}
//更新选中标签
const updateTab = (newRoute: RouteLocationNormalized | viewMenu) => {
    const currentRoute = findMenu(menuNavStore.menusViewRoutes, newRoute.path)
    if (!currentRoute) return
    // 添加tab
    addTab(newRoute, currentRoute)
    // 激活当前tab
    setActiveRoute(newRoute, currentRoute)
    nextTick(() => {
        selectNavTab(tabsRefs.value[navTabs.value.activeIndex])
    })
}
function addTab(route: RouteLocationNormalized | viewMenu, currentRoute: any) {
    for (const key in navTabs.value.tabsView) {
        if (navTabs.value.tabsView[key].path === route.path) {
            return
        }
    }
    navTabs.value.tabsView.push(
        Object.assign({}, currentRoute, {
            title: currentRoute.title || 'pagesTitle.noTitle',
        })
    )
}

onBeforeRouteUpdate(async (to, from) => {
    updateTab(to)
})

onMounted(() => {
    updateTab(route)
    new horizontalScroll(tabScrollbarRef.value)
})
</script>

<template>
    <div class="nav-tabs" ref="tabScrollbarRef">
        <!-- @contextmenu.prevent="onContextmenu(item, $event)" -->

        <div v-for="(item, idx) in navTabs.tabsView" @click="onTab(item)" class="ba-nav-tab"
            :class="navTabs.activeIndex == idx ? 'active' : ''" :ref="tabsRefs.set" :key="idx">
            {{ item.title }}
            <transition @after-leave="selectNavTab(tabsRefs[navTabs.activeIndex])" name="el-fade-in">
                <i v-show="navTabs.tabsView.length > 1" class="close-icon yh-icons-close"
                    @click.stop="closeTab(item, idx)" size="15"></i>
            </transition>
        </div>
        <div :style="activeBoxStyle" class="nav-tabs-active-box"></div>
    </div>
</template>

<style scoped lang='scss'>
.nav-tabs {
    overflow-x: auto;
    overflow-y: hidden;
    margin-right: var(--main-space);
    scrollbar-width: none;
    display: flex;
    height: 100%;
    position: relative;
    flex: 1;

    &::-webkit-scrollbar {
        height: 5px;
    }

    &::-webkit-scrollbar-thumb {
        background: #eaeaea;
        border-radius: var(--el-border-radius-base);
        box-shadow: none;
        -webkit-box-shadow: none;
    }

    &::-webkit-scrollbar-track {
        background: #FFF;
    }

    &:hover {
        &::-webkit-scrollbar-thumb:hover {
            background: #c8c9cc;
        }
    }
}

.ba-nav-tab {
    white-space: nowrap;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0 20px;
    cursor: pointer;
    z-index: 1;
    user-select: none;
    color: var(--yh-text-color-secondary);
    transition: all 0.2s;
    -webkit-transition: all 0.2s;

    .close-icon {
        padding: 2px;
        margin: 2px 0 0 4px;
        font-size: 12px;
    }

    .close-icon:hover {
        background: var(--color-primary-sub-0);
        color: var(--color-sub-1) !important;
        border-radius: 50%;
    }

    &.active {
        // color: var(--yh-text-color-primary)
    }

    &:hover {
        color: var(--yh-text-color-primary);
    }

}

.nav-tabs-active-box {
    
    position: absolute;
    height: 40px;
    border-radius: var(--el-border-radius-base);
    background-color: var(--yh-bg-color-container);
    box-shadow: var(--el-box-shadow-light);
    transition: all 0.2s;
    -webkit-transition: all 0.2s;
}
</style>