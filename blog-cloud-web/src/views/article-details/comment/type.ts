export interface CommentListType {
    id: string;
    context: string;
    likeCount:number|string,
    linkId?:string|null,
    userName:string,
    userAvatar?:string,
    createTime:string,
    userId?:string,
  }

  export interface PageData {
    current: number,
    size: number,
    total:number,
    totalPage:number,
  }