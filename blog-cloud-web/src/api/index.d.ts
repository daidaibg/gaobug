export type Keys=string | number

// 定义文件类型提交方法
export interface fileconfigs {
    [headers: string]: {
        'Content-Type': string
    }
}

export interface UrlObjType {
    [key:string| number]:string
}

export interface RequestParamType {
    [key:string| number]:string
}


export interface Config {
    cancel?:Function,
    enc?:boolean,
    'Content-Type'?: string,
}