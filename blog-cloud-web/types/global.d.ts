interface Window {
  // 语言
  $t: any
}

declare type Recordable<T = any> = Record<string, T>
declare function $t(key:string,params?:any):string
declare type codeText={
  [key:string]:string
}