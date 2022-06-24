import * as API from "../api";
//告警数据列表
export const tableApi =(param)=>{
    return API.GET("/biz/alertdata/list",param)
}
//导出告警数据列表
export const warnExportApi =(param)=>{
	 return API.FILE({url:"/biz/alertdata/export"}, {},param)
}
//告警类型列表
export const alertconfigApi =(param)=>{
    return API.GET("/biz/alertconfig/list",param)
}
//通信数据列表
export const communiDataApi =(param)=>{
    return API.GET("/biz/communicatedata/list",param)
}
//导出通信数据列表
export const communiExportApi =(param)=>{
	 return API.FILE({url:"/biz/communicatedata/export"}, {},param)
}
//终端实时环境数据列表
export const terminalDataApi =(param)=>{
    return API.GET("/biz/monitordataterminal/queryDataList",param)
}
//删除终端实时环境数据列表
export const terminalDelApi =(param)=>{
    return API.POST("/biz/terminal/delete",param)
}
//终端历史数据列表
export const historyDataApi =(param)=>{
    return API.GET("/biz/monitordataterminalhistory/queryHisDataList",param)
}