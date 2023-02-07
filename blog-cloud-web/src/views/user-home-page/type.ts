export interface ArticleList{
    title:string,
    tag:string,
    createTime:string,
    isLike:boolean,
    summary:string,
    coverUrl?:string
}

export interface BlogData {
    list:ArticleList[],
    current:number,
    size:number,
    total:number,
    tabActive:number
}