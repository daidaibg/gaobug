
export interface userDataType {
    [key:string]:string|undefined,
    nickName:string|undefined
}
export interface userStoreType {
    isLogin:boolean,
    modelLoginShow:boolean,
    userData:userDataType
}