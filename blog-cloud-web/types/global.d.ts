interface Window {
  // 语言
  $t: any
}

declare type Recordable<T = any> = Record<string, T>

declare type codeText={
  [key:string]:string
}