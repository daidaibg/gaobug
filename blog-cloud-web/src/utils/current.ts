
import {clearLocalStorage} from "./storage"
import {StorageEnum} from "@/enums"
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
