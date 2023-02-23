import { PropType } from 'vue'
import {defaultOption} from "./config"
import type {Options,Theme} from "./config"
const themes=['vs', 'hc-black', 'vs-dark', 'hc-light','default-dark']



export const editorProps = {
  modelValue: {
    type: String as PropType<string>,
    default: null,
  },
  hightChange: {
    type: Boolean,
    default: false,
  },
  width: {
    type: [String, Number] as PropType<string | number>,
    default: '100%',
  },
  height: {
    type: [String, Number] as PropType<string | number>,
    default: '100%',
  },
  language: {
    type: String as PropType<string>,
    default: 'javascript',
  },
  readOnly: {
    type: Boolean,
    default: false,
  },
  theme: {
    type: String as PropType<Theme>,
    validator(value: string): boolean {
      return themes.includes(value)
    },
    default: 'vs',
  },
  options: {
    type: Object as PropType<Options>,
    default() {
      return defaultOption
    },
  },
}
