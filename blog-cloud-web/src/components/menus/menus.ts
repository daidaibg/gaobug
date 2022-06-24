

export interface MenuSubProps{
    sub?:[],
    url:string,
    name:string,
    icon?:string
}
export interface MenuProps{
    sub?:MenuProps[],
    url:string,
    name:string,
    icon?:string
}