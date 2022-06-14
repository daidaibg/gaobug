import {StorageEnum,ThemeEnum} from '@/enums'
import {theme} from '@/config'
import { setLocalStorage,getLocalStorage } from '@/utils'
import {ThemeStateType} from './theme-store-type'
import { useColorMode } from '@vueuse/core'

const {YH_THEME_STORE} = StorageEnum;
const themeState =getLocalStorage(YH_THEME_STORE) 
const domSetAttribute =(theme: ThemeEnum)=>{
    document.documentElement.setAttribute("theme-mode", theme);
    document.documentElement.className = theme;
    
}

if(themeState&& themeState.theme==ThemeEnum.DARK){
    domSetAttribute(themeState.theme)
}
//state
const state:ThemeStateType={
    theme:themeState?.theme||theme
}
export default {
    namespaced: true,
    name:"themeStore",
    state,
    mutations: {
        changeTheme(state:any,theme: ThemeEnum): void {
            state.theme =theme;
            domSetAttribute(theme)
            setLocalStorage(YH_THEME_STORE,state)
            // document.documentElement.className=type;
        }
    },
    actions: {},

};