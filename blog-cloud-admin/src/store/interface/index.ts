import type { Component } from 'vue'
export interface MenuNavStateType {
    isCollapse:boolean,
    menusViewRoutes:viewMenu[]
}

export interface viewMenu {
    title: string
    path: string
    name?: string
    type?: string
    icon?: string
    keepAlive?: string
    query?: anyObj
    params?: anyObj
    children?: viewMenu[]
    component?: Component
}
