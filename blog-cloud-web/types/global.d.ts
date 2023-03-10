
interface Window {
  // 语言
  $t: any,
  $message: any
}

declare function $t(key:string,params?:any):string
declare type codeText={
  [key:string]:string
}


// declare type Recordable<T = any, K = string> = Record<K extends null | undefined ? string : K, T>

declare type Recordable<T = any, K extends string | number | symbol = string> = Record<K extends null | undefined ? string : K, T>


