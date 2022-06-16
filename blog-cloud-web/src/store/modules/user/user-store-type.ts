
export interface userDataType {
    [key:string]:string|undefined|null|number,
    nickName:string|undefined
}
export interface userStoreType {
    isLogin:boolean,
    modelLoginShow:boolean,
    userData:userDataType
}