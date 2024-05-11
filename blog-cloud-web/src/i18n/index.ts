//语言
import { lang } from "@/config";
import { createI18n } from "vue-i18n"; //引入vue-i18n组件
import { getLocalStorage } from "@/utils";
import { LangEnum, LangStateType, StorageEnum } from "@/enums";
import zh from "./zh/index";
import en from "./en/index";

const langStorage: LangStateType = getLocalStorage(StorageEnum.YH_LANG_STORE);
// 语言数组
export const langList = [
  {
    label: "中文",
    key: LangEnum.ZH,
  },
  {
    label: "English",
    key: LangEnum.EN,
  },
];

const i18n = createI18n({
  locale: langStorage?.lang || lang,
  fallbackLocale: langStorage?.lang || lang,
  // 关闭控制台警告
  // silentFallbackWarn: true,
  // CompositionAPI
  /** 下个版本删除
   * [intlify] 'allowComposition' option will be dropped in the next major version. For more information, please see 👉 https://tinyurl.com/2p97mcze
   */
  // allowComposition: true,  //
  //注册全局
  globalInjection: true,
  /**默认使用 Legacy API 模式。如果要使用Composition API模式，需要将其设置为false。 */
  legacy:false,
  // legacy:false,
  // messages
  messages: {
    [LangEnum.ZH]: zh,
    [LangEnum.EN]: en,
  },
});

export default i18n;
