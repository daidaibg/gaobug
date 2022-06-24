export interface articlesListType {
    id: string | number,
    title: string,
    articlesPart: "测试",
    updateTime: string,
    clickCount: string | number,
    collectCount: string | number,
    openComment: string | number,
    comment:string
}
export interface articlesType {
    current: number,
    size: number,
    total:number
    list:articlesListType[],
    publish:string|number|undefined
}