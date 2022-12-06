import { PropType } from 'vue'

export const CommonProps = {
    modelValue: {
        type: String as PropType<string>,
        default: "",
    },
    editorOption:{
        type:Object as any,
        default:null
    }
}
