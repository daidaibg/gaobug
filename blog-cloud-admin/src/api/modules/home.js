import * as API from "../api";
//搜索结果
export const searchApi =(param)=>{
    return API.GET("/biz/gateway/list",param)
}
//右边告警看板
export const gatewayApi =(param)=>{
    return API.GET("/biz/gateway/countDeviceNum",param)
} 
//网关设备详情
export const gatewayShowApi =(param)=>{
    return API.GET("/biz/gateway/info",param)
} 
//网关设备详情--实时数据
export const realShowApi =(param)=>{
    return API.GET("/biz/monitoritem/list",param)
}
//终端设备详情
export const terminalShowApi =(param)=>{
    return API.GET("/biz/gateway/terminalDetails",param)
}
//地图标记
export const mapMarkersApi =(param)=>{
    return API.GET("/biz/gateway/queryGatewayList",param)
} 
//设备及告警列表
export const alertdataApi =(param)=>{
    return API.GET("/biz/alertdata/list",param)
}