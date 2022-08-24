//语言
import { lang } from '@/config'
import { createI18n } from 'vue-i18n' //引入vue-i18n组件
import { getLocalStorage } from '@/utils'
import { LangEnum, LangStateType,StorageEnum } from '@/enums'
import zh from './zh/index'
import en from './en/index'

const langStorage: LangStateType = getLocalStorage(StorageEnum.YH_LANG_STORE)
// 语言数组
export const langList = [
  {
    label: '中文',
    key: LangEnum.ZH
  },
  {
    label: 'English',
    key: LangEnum.EN
  }
]

const i18n = createI18n({
  locale: langStorage?.lang || lang,
  fallbackLocale: langStorage?.lang || lang,
  // 关闭控制台警告
  // silentFallbackWarn: true,
  // CompositionAPI
  allowComposition: true, 
  //注册全局
  globalInjection:true,
  // legacy:false,
  // messages
  messages: {
    [LangEnum.ZH]: zh,
    [LangEnum.EN]: en
  }
})

export default i18n
