import { setLocalStorage } from '@/utils'
import { StorageEnum, LangStateType, LangEnum } from '@/enums'
import i18n from '@/i18n/index'
const { global } = i18n;
const { YH_LANG_STORE } = StorageEnum
export default {
    namespaced: true,
    name:"langStore",
    state: {
        lang: global.locale
    },
    mutations: {
        changeLang(state:any,lang: LangEnum): void {
            state.lang=lang;
            setLocalStorage(YH_LANG_STORE,{lang:lang})
        }
    },
    actions: {},

};