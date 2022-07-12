
import {clearLocalStorage} from "./storage"
import {StorageEnum} from "@/enums"
import { useUserStore } from '@/store'

export const clearnUserData = () => {
    clearLocalStorage(StorageEnum.GB_TOKEN_STORE)
}

export  const todayTime = () => {
    let date = new Date();
    let hoursTip = ''
    if (date.getHours() >= 0 && date.getHours() < 12) {
        hoursTip = "上午好"
    } else if (date.getHours() >= 12 && date.getHours() < 18) {
        hoursTip = "下午好"
    } else {
        hoursTip = "晚上好"
    }
    return  hoursTip
}
//是否登录显示登录弹窗
export const isLoginShowDislog=()=>{
    const userStore = useUserStore()
    if(userStore.isLogin){
        return true 
    }else{
        userStore.onModelLogin()
        return false
    }
}
// 展示登录弹窗
export const showLoginDislog = ()=>{
    const userStore = useUserStore()
    userStore.onModelLogin()
}


/**
 * 是否是外部链接
 * @param {string} path
 * @return {Boolean}
 */
 export function isExternal(path: string) {
    return /^(https?|ftp|mailto|tel):/.test(path)
}