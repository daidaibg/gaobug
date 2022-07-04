import {HeaderListType} from "../type"
import { PropType } from 'vue';

export default {
    active:{
        type:String,
        default:''
    },
    HeaderList:{
        type:Array as PropType<HeaderListType[]>,
        default:()=>[]
    },
    jump:{
        type:Function,
        default:()=>{}
    }
}