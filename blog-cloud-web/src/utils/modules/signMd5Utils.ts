import {MD5} from 'crypto-js'

export default class signMd5Utils {
    /**
     * json参数升序
     * @param jsonObj 发送参数
     */

    static sortAsc(jsonObj: any) {
        let keyArr = Object.keys(jsonObj).sort()
        let sortObj: any = {};
        for (let i = 0, len = keyArr.length; i < len; i++) {
            sortObj[keyArr[i]] = jsonObj[keyArr[i]] + "";
        }
        return sortObj;
    }


    /**
     * @param url 请求的url,应该包含请求参数(url的?后面的参数)
     * @param requestParams 请求参数(POST的JSON参数)
     * @returns {string} 获取签名
     */
    static getSign(params: any, timestamp: number | undefined):any{
        const requestBody = this.sortAsc(params);
        const requestBodyStr = JSON.stringify(requestBody)
        return MD5(requestBodyStr + timestamp);
    }
    static getSignOld(url: any, requestParams: any) {
        let urlParams = this.parseQueryString(url);
        let jsonObj = this.mergeObject(urlParams, requestParams);
        let requestBody = this.sortAsc(jsonObj);
        return (MD5(JSON.stringify(requestBody)) as any).toUpperCase();
    }
    /**
     * @param url 请求的url
     * @returns {{}} 将url中请求参数组装成json对象(url的?后面的参数)
     */
    static parseQueryString(url: any) {

        // let urlReg1 =  /^[^\?]+\?([\w\W]+)$/;
        //     paramReg = /([^&=]+)=([\w\W]*?)(&|$|#)/g;
        let urlReg = new RegExp("^[^\\?]+\\?([\\w\\W]+)$", "g"),
            paramReg = new RegExp("([^&=]+)=([\\w\\W]*?)(&|$|#)", "g");
        let urlArray = urlReg.exec(url),
            result: any = {};
        if (urlArray && urlArray[1]) {
            let paramString = urlArray[1], paramResult;
            while ((paramResult = paramReg.exec(paramString)) != null) {
                result[paramResult[1]] = paramResult[2];
            }
        }
        return result;
    }

    /**
     * @returns {*} 将两个对象合并成一个
     */
    static mergeObject(objectOne: any, objectTwo: any) {
        if (Object.keys(objectTwo).length > 0) {
            for (let key in objectTwo) {
                if (objectTwo.hasOwnProperty(key) === true) {
                    objectOne[key] = objectTwo[key];
                }
            }
        }
        return objectOne;
    }

    static urlEncode(param: any, key: any, encode: any) {
        if (param == null) return '';
        let paramStr = '';
        let t = typeof (param);
        if (t == 'string' || t == 'number' || t == 'boolean') {
            paramStr += '&' + key + '=' + ((encode == null || encode) ? encodeURIComponent(param) : param);
        } else {
            for (let i in param) {
                let k = key == null ? i : key + (param instanceof Array ? '[' + i + ']' : '.' + i);
                paramStr += this.urlEncode(param[i], k, encode);
            }
        }
        return paramStr;
    };
}