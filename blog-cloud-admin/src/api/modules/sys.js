
/**
 * @author liuxiaodai
 * @content 设置管理
 */
import * as API from "../api";
import { treeDataTranslate } from "@/lib/currency";
export const paramType ={
    1:"alertconfig", //告警类型
}
/******************      通用增删改查       ********************* */
/**
 * 通用列表
 * @param {*} param 
 */
 export const currentSysList =  (key,param)=> {
    return API.GET("/sys/"+paramType[key]+"/list", param)
}
/**
 * 通用新增
 * @param {*} param 
 */
 export const currentSysSave= (key,param)=> {
    return API.POST("/sys/"+paramType[key]+"/save", param)
}
/**
 * 通用修改
 * @param {*} param 
 */
 export const currentSysUpdate=  (key,param) => {
    return API.POST("/sys/"+paramType[key]+"/update", param)
}

/**
 * 通用删除
 * @param {*} param 
 */
 export const currentSysDelete= (key,param) => {
    return API.POST("/sys/"+paramType[key]+"/delete", param)
}

/**
 * 通用获取所有不分页
 * @param {*} param 
 */
 export const currentSysSelect=  (key,param)=> {
    return API.GET("/sys/"+paramType[key]+"/select", param)
}
/**
 * 用户列表
 * @param {*} param 
 */
export const userList = (param) => {
    return API.GET("/sys/user/list", param)
}

// 

/**
 * 修改密码
 * @param {*} param 
 */
export const userPassword = (param) => {
    return API.POST("/sys/user/password", param)
}


/**
 * 添加账号
 * @param {*} param 
 */
export const userSave = (param) => {
    return API.POST("/sys/user/save", param)
}

/**
 * 修改账号
 * @param {*} param 
 */
export const userUpdate = (param) => {
    return API.POST("/sys/user/update", param)
}

/**
 * 删除账号
 * @param {*} param 
 */
export const userDelete = (param) => {
    return API.POST("/sys/user/delete", param)
}
/**
* 重置密码
* @param {*} param 
*/
export const passwordRest = (param) => {
    return API.POST("/sys/user/passwordRest/" + param)
}
/**
* 获取用户详情
* @param {*} param 
*/
export const userInfo = (id) => {
    return API.GET("/sys/user/info/" + id)
}
/**
* 获取用户详情
* @param {*} param 
*/
export const getUserInfo = () => {
    return API.GET("/sys/user/info" )
}
/**
* 更新用户状态
* @param {*} param 
*/
export const userUpdateStatus = (param) => {
    return API.POST("/sys/user/updateStatus", param)
}

/**
* 查询可供选择的用户列表（选择管理员时使用）
* @param {*} param 
*/
export const userQueryUserList = (param) => {
    return API.GET("/sys/user/queryUserList", param)
}

// 用户接口集合
export const userApi = {
    passwordRest,
    userDelete,
    userUpdate,
    userSave,
    userPassword,
    userList,
    userInfo,
    userUpdateStatus,
    userQueryUserList
}
//  ---------------------------------------------------------  角色 -----------------------------------------------------------------------------

/**
 * 角色列表
 * @param {*} param 
 */
export const roleList = (param) => {
    return API.GET("/sys/role/list", param)
}

/**
 * 角色查询列表
 * @param {*} param 
 */
export const roleSelect = (param) => {
    return API.GET("/sys/role/select", param)
}
/**
 * 角色查询
 * @param {*} param 
 */
export const roleInfo = (param) => {
    return API.GET("/sys/role/info/" + param)
}
/**
 * 角色添加
 * @param {*} param 
 */
export const roleSave = (param) => {
    return API.POST("/sys/role/save", param)
}


/**
 * 角色更新
 * @param {*} param 
 */
export const roleUpdate = (param) => {
    return API.POST("/sys/role/update", param)
}

/**
 * 角色删除
 * @param {*} param 
 */
export const roleDelete = (param) => {
    return API.POST("/sys/role/delete", param)
}

// 角色接口集合
export const roleApi = {
    roleDelete,
    roleUpdate,
    roleSave,
    roleInfo,
    roleSelect,
    roleList
}
//-----------------------------------------------------------  菜单------------------------------------------------------------------------------------------------
// 
/**
 * 全量菜单列表
 * @param {*} param 
 */
export const menuList = (param) => {
    return API.GET("/sys/menu/list", param)
}
//  菜单列表根据登录用户啊
export const menuNav = (param) => {
    return API.GET("/sys/menu/nav", param)
}
//  所有菜单列表
export const allMenuList = (param) => {
    return API.GET("/sys/menu/list", param)
}
// 查询菜单列表（配置权限时使用）
export const menuListUser = (param) => {
    return API.GET("/sys/menu/queryMenuList", param)
}
/**
 * 菜单删除
 * @param {*} param 
 */
export const menuDelete = (param) => {
    return API.POST("/sys/menu/delete/" + param)
}
/**
 * 菜单添加
 * @param {*} param 
 */
export const menuSave = (param) => {
    return API.POST("/sys/menu/save", param)
}
/**
 * 菜单编辑
 * @param {*} param 
 */
export const menuUpdate = (param) => {
    return API.POST("/sys/menu/update", param)
}

// 菜单接口集合
export const menuApi = {
    menuUpdate,
    menuSave,
    menuDelete,
    menuListUser,
    allMenuList,
    menuNav,
    menuList
}


/**---------------------------------------------------------- 部门--------------------------------------------------- */
/**
 * 部门列表
 * @param {*} param 
 */
export const deptList = (param) => {
    return API.GET("/sys/dept/list", param)
}

/**
 * 选择部门列表
 * @param {*} param 
 */
export const deptSelect = (param) => {
    return API.GET("/sys/dept/select", param)
}

/**
 * 部门添加
 * @param {*} param 
 */
export const deptSave = (param) => {
    return API.POST("/sys/dept/save", param)
}


/**
 * 部门修改
 * @param {*} param 
 */
export const deptUpdate = (param) => {
    return API.PUT("/sys/dept/update", param)
}

/**
 * 部门删除
 * @param {*} param 
 */
export const deptDelete = (param) => {
    return API.DELETE("/sys/dept/delete/" + param)
}
// 部门接口集合
export const deptApi = {
    deptDelete,
    deptUpdate,
    deptSave,
    deptSelect,
    deptList,
}

/**----------------------------------------------- 字典--------------------------------------------- */

/**
 * 字典列表
 * @param {*} param 
 */
export const dictList = (param) => {
    return API.GET("/sys/dict/list", param)
}



/**
 * 字典删除
 * @param {*} param 
 */
export const dictDelete = (param) => {
    return API.POST("/sys/dict/delete", param)
}
/**
 * 字典新增
 * @param {*} param 
 */
export const dictSave = (param) => {
    return API.POST("/sys/dict/save", param)
}
/**
 * 字典编辑
 * @param {*} param 
 */
export const dictUpdate = (param) => {
    return API.POST("/sys/dict/update", param)
}

/**
 * 字典查询
 * @param {Object} param  
 * @参数 { busType: 'TYPE_VALUE'} 
 * @dydj   电压等级
 * @log_select   日志
 */
export const dictSelect = (param) => {
    return API.GET("/sys/dict/select", param)
}

// 字典接口集合
export const dictApi = {
    dictSelect,
    dictUpdate,
    dictSave,
    dictDelete,
    dictList,
}

/**--------------------------------------------------  关注  ----------------------------------------------------------- */
/**
 * 关注列表
 * @param {*} param 
 */
export const interestList = (param) => {
    return API.GET("/zxjc/sys/interest/list", param)
}
/**
 * 字典删除
 * @param {*} param 
 */
export const interestDelete = (param) => {
    return API.POST("/zxjc/sys/interest/delete", param)
}


// 日志
//  
export const logList = (param) => {
    return API.GET("/sys/log/list", param)
}

//富文本
// 帮助
export const richTextList = (param) => {
    return API.GET("/sys/richText/list", param)
}
//详情
export const richTextInfo = (id) => {
    return API.GET("/sys/richText/info/" + id)
}
//添加帮助
export const richText = (param) => {
    return API.POST("/sys/richText", param)
}
///sys/richText
export const richTextPUT = (param) => {
    return API.PUT("/sys/richText", param)
}
// 删除
export const richTextDELETE = (param) => {
    return API.DELETE("/sys/richText/" + param)
}


/********************  区域 ************************************* */
//列表区域
export const regionList = () => {
    return API.GET("/biz/region/list")
}
//直接返回树状数据
export const regionListTree = (disabled) => {
    return new Promise((resolve, reject) => {
        API.GET("/biz/region/list").then(res => {
            if (res.success) {
                if (disabled) {
                    let newData = res.data.map(item => {
                        let newItem = {
                            ...item,
                        }
                        if (item.lev == 3) {
                            newItem.disabled = true
                        }
                        return newItem
                    })
                    // console.log(newData)
                    resolve({
                        success: true,
                        data: treeDataTranslate(newData, "id", "parentid"),
                        list:newData
                    })
                } else {
                    resolve({
                        success: true,
                        data: treeDataTranslate(res.data, "id", "parentid"),
                        list:res.data
                    })
                }

            } else {
                resolve(res)
            }
        }).catch(err => {
            reject(err)
        })
    })
}


// 分页查询区域列表
export const regionPage = (param) => {
    return API.GET("/biz/region/page", param)
}
//保存区域
export const regionSave = (param) => {
    return API.POST("/biz/region/save", param)
}
//修改区域
export const regionUpdate = (param) => {
    return API.POST("/biz/region/update", param)
}

// 删除区域
export const regionDelete = (param) => {
    return API.POST("/biz/region/delete", param)
}
// 区域接口集合
export const regionApi = {
    regionDelete,
    regionUpdate,
    regionSave,
    regionPage,
    regionListTree,
    regionList
}
/************       公司     ********************************* */


//分页查询公司列表
export const companyList = (param) => {
    return API.GET("/biz/company/list", param)
}

//保存公司
export const companySave = (param) => {
    return API.POST("/biz/company/save", param)
}
//修改公司
export const companyUpdate = (param) => {
    return API.POST("/biz/company/update", param)
}

// 删除公司
export const companyDelete = (param) => {
    return API.POST("/biz/company/delete", param)
}
// 公司接口集合
export const companyApi = {
    companyList,
    companySave,
    companyUpdate,
    companyDelete
}

//忘记密码
export const getCaptchaCode = (param) => {
    return API.GET("/sys/user/captcha", param)
}
// 重置密码
export const UpdatePassword = (param) => {
    return API.POST("/sys/user/updatePassword", param)
}