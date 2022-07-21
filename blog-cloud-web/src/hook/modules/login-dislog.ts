
import { useUserStore } from '@/store'
//
/**
 * @description: 是否登录显示登录弹窗
 * @param {*} boolean
 * @return {*}
 */
export const isShowLoginDislog = () => {
    const userStore = useUserStore()
    if (userStore.isLogin) {
        return true
    }
    userStore.onModelLogin()
    return false
}
// 展示登录弹窗
export const showLoginDislog = () => {
    const userStore = useUserStore()
    userStore.onModelLogin()
}