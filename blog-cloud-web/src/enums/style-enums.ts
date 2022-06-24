import {WritableComputedRef} from "vue"
// 主题
export enum ThemeEnum {
    DARK = 'dark',
    LIGHT = 'light'
}

// 语言
export enum LangEnum {
    ZH = 'ZH',
    EN = 'EN'
}
export interface LangStateType {
    // 当前语言
    lang: LangEnum,
}