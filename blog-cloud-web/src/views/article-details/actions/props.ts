import { PropType } from 'vue';
import {ActionProps} from "../type"
export default {
  articleId:{
    type:[String, Number] as PropType<ActionProps['articleId']>,
    
  },
  likeNum:{
    likeNum:[String, Number] as PropType<ActionProps['likeNum']>,
    default:0
  },
  commentNum:{
    likeNum:[String, Number] as PropType<ActionProps['commentNum']>,
    default:0
  },
  collectCount:{
    likeNum:[String, Number] as PropType<ActionProps['collectCount']>,
    default:0
  },
}