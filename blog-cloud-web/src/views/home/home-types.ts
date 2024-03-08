export interface HomeBlogList<T> {
    [_: string]: T
}
export interface HomeBlogPage {
    current: number, size: number
}
export interface HomeBlogState {
    blogList:HomeBlogList<any>[];
    blogPage:HomeBlogPage;
    loading:boolean|string,
    categoryId:string|number,
    keywords:string,
    loadingEnd:boolean
}


export interface TypeList {
    type:string,
    name:string
}


export interface ClassifyListType {
    id: string | number;
    categoryName: string;
  }


