import { RouteLocationNormalized } from 'vue-router'
import { viewMenu } from '@/store/interface'
import { reactive, toRefs } from "vue"
export const navTabFun = () => {
    const navTabs = reactive<any>({
        // tab列表
        tabsView: [],
        // 激活tab的index
        activeIndex: 0,
    })
     function addTab(route: RouteLocationNormalized | viewMenu) {
        for (const key in navTabs.tabsView) {
            if (navTabs.tabsView[key].path === route.path) {
                navTabs.tabsView[key].params = route.params ?? {}
                navTabs.tabsView[key].query = route.query ?? {}
                return
            }
        }
        const currentRoute = findMenu(navTabs.tabsViewRoutes, route.path)
        if (!currentRoute) return
        currentRoute!.params = route.params ?? {}
        currentRoute!.query = route.query ?? {}
        navTabs.tabsView.push(
            Object.assign({}, currentRoute, {
                title: currentRoute.title || 'pagesTitle.noTitle',
            })
        )
    }

    return {
        navTabs,
        addTab,

    }
}
// 在菜单集合中递归查找 path 的数据
export function findMenu(tabsViewRoutes: viewMenu[], path: string): viewMenu | undefined {
    for (const key in tabsViewRoutes) {
        if (tabsViewRoutes[key].path == path) {
            return tabsViewRoutes[key]
        } else if (tabsViewRoutes[key].children) {
            const done = findMenu(tabsViewRoutes[key].children as viewMenu[], path)
            if (done) return done
        }
    }
}