import {MenuProps} from "@/components/menus"

export const manageMenuList:MenuProps[] =[
    {
        name:"内容管理",
        icon:"dd-icon-tuceng",
        url:'1',
        sub:[
            {
                name:'文章管理',
                url:'/blogs/manage/article',
            }
        ]
    }
]