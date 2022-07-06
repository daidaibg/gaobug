export enum RequestEnum {
    // token key
    GB_TOKEN_KEY = 'auth-token',
    // 验签key
    GB_SIGN_KEY ="sign",
    // appid key
    GB_APP_ID_KEY="app-id",
       // 时间戳 key
    GB_TIMESTAMP_KEY="timestamp"
}
export enum ReqCodeEnum {
    Unauthorized =401,// token过期
    Success =200,//成功
}
