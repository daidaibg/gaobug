/*
 * @Author: daidai
 * @Date: 2021-12-23 17:17:03
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-14 12:23:32
 * @FilePath: \web-pc\src\lib\modules\table-list.js
 */
import { MessageBox } from 'element-ui'
import { message } from '@/directives/resetMessage'
import {warnMessage} from '@/config/messages-config'

/**     例子 
 * 
    import { deletsFun } from "@/lib/modules/table-list.js";
 * 
    deletsFun({
        name:"versionName",
        id:"id",
        type:5,
        fun:currentDelete
    },row,this.multipleSelection).then(res=>{
        console.log(res)
        if (res.success) {
            this.$message({
                type: "success",
                message: "删除成功!",
            });
            this.getData();
        } else {
            this.$message({
                type:  res.type|| "error",
                message: res.msg,
            });
        }
    }).catch(err=>{
        this.$message.info("取消删除")
    })
 */


/**        例子2 不需要处理提醒消息
 deletsFun(
    {
      name: "name",
      id: "id",
      fun: poleDelete,
      isMessage: true,
    },
    row,
    this.multipleSelection
  ).then((res) => {
    if (res.success) {
       this.eaitGetPole();
    }
  });
 
 */
/**
 * 单个删除与多个删除通用
 * @param {Array} list  如果是数组就要传  
 * @param {Object} row  单个对象格式 
 * @param {*} config  
 *  fun 接口 Promise
 *  name 获取名字字段key
 *  id 获取id字段key
 *  type  通用接口 需要传这个
 *  isMessage 是否提示element 的message ( 默认 false 不提示 ||  true 提示 )
 * 
 */

export function deletsFun(config, row, list) {
    return new Promise((resolve, reject) => {
        let str = "",
            ids = [];
        let nameKey = config.name || "name"
        let idKey = config.id || "id"
        if (row) {
            str = row[nameKey];
            ids.push(row[idKey])
        } else {
            //没有数据的时候
            if (list.length == 0) {
                if (config.isMessage) {
                    message.warning(warnMessage.delLeastOneData)
                }
                resolve({
                    success: false,
                    msg: warnMessage.delLeastOneData,
                    type: "warning"
                })
                return;
            } else {
                list.map((item) => {
                    str += item[nameKey] + "、";
                    ids.push(item[idKey]);
                });
                str = str.substr(0, str.length - 1);
            }
        }
        MessageBox.confirm(`确认永久删除${str}, 是否继续?`, "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
        })
            .then(() => {
                if (config.type) {
                    config.fun(config.type, ids).then((res) => {
                        return resHandle(res, resolve, config.isMessage)
                    });
                } else {
                    config.fun(ids).then((res) => {
                        return resHandle(res, resolve, config.isMessage)
                    });
                }

            })
            .catch((err) => {
                if (config.isMessage) {
                    message.info(warnMessage.delCancel)
                }
                resolve({
                    success: false,
                    msg: warnMessage.delCancel,
                    type: "info"
                })
            });
    })

}

function resHandle(res, resolve, isMessage) {
    if (res.success) {

        if (res.msg == null || res.msg && res.msg.indexOf("成功") != -1) {
            if (isMessage) {
                message.success(warnMessage.delSuccess)
            }
            resolve(res)

        } else {
            if (isMessage) {
                message.warning(res.msg)
            }
            resolve({
                success: false,
                msg: res.msg,
                type: "warning"
            })
        }

    } else {
        if (isMessage) {
            message.error(res.msg)
        }
        resolve({
            success: false,
            msg: res.msg,
            type: "error"
        })
    }
}