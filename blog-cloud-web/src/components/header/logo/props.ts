import { ThemeEnum } from '@/enums'
import {PropType} from "vue"
export default {
    theme: {
        type: String as PropType<ThemeEnum>,
        default: ()=>''
    },
    target:{
        type: String as PropType<string>,
        default: ()=>''
    }
}