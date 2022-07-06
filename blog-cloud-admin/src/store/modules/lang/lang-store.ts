import { defineStore } from "pinia";
import { setLocalStorage ,getLocalStorage} from '@/utils'
import { StorageEnum, LangStateType, LangEnum } from '@/enums'
import i18n from '@/i18n/index'
const { YH_LANG_STORE } = StorageEnum
const storageLang: LangStateType = getLocalStorage(YH_LANG_STORE)
export const useLangStore = defineStore({
    id: "useLangStore",
    state: ():LangStateType => storageLang||{
        lang: i18n.global.locale
    },
    getters: {
        getLang(): LangEnum {
            return this.lang
        }
    },
    actions: {
        changeLang( lang: LangEnum): void {
            if (this.lang === lang) return
            this.lang = lang
            let i18nLang:any = lang
            i18n.global.locale = i18nLang
            setLocalStorage(YH_LANG_STORE, { lang: lang })
        }
    },
});