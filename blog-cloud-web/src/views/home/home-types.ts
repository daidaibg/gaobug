export interface HomeBlogList<T> {
    [_: string]: T
}
export interface HomeBlogPage {
    current: number, size: number
}
export interface HomeBlogState {
    blogList:HomeBlogList<any>[];
    blogPage:HomeBlogPage;
    loading:boolean|string
}


export interface TypeList {
    type:string,
    name:string
}


