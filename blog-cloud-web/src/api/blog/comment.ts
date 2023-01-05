import { requestGet, requestPost } from "../api";

//评论接口地址
export const commentReqUrl = {
    like: '/blog/comment/like',//点赞评论
    comment: "/blog/comment",//评论
    commentExpand: "/blog/comment/expand/page",//获取评论展开列表
    commentDel: "/blog/comment/del/",//删除评论
    commentList: "/blog/comment/page",//获取评论列表
}
export interface Comment {
    articleId: string | number,//文章标识
}

//点赞评论
export const commentLike = (param: any = {}) => {
    return requestPost(commentReqUrl.like, param)
}

export interface commentType extends Comment {
    linkId: string|0 ,//评论关联标识顶级默认0 评论下传评论标识
    context: string,//评论内容
}
//评论
export const comment = (param: commentType) => {
    return requestPost<commentType>(commentReqUrl.comment, param)
}

//获取评论展开列表
export const getCommentExpand = (param: any = {}) => {
    return requestGet(commentReqUrl.commentExpand, param)
}

export interface CommentListType extends Comment {
    size: number,//评论关联标识顶级默认0 评论下传评论标识
    current: number,//评论内容
}
//获取评论列表
export const getCommentList = (param: CommentListType) => {
    return requestGet<CommentListType>(commentReqUrl.commentList, param)
}

//删除评论
export const commentDel = (id: string) => {
    return requestPost(commentReqUrl.commentDel+id,{})
}