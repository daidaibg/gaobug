export interface CommentListType {
    id: number;
    context: string;
    likeCount:number|string,
    linkId?:number|string,
    userName:string,
    userAvatar?:string
  }

  export interface PageData {
    current: number,
    size: number,
    total:number,
    totalPage?:number
  }