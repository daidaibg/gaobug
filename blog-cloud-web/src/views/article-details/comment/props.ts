import { PropType } from 'vue';
import {CommentProps} from "../type"


export default {
  articleId:{
    type:[String, Number] as PropType<CommentProps['articleId']>,
    require:true
  },
  avatarUrl:{
    type:[String, Number] as PropType<CommentProps['avatarUrl']>,
    default:''
  }
}