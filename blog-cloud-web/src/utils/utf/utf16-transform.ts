//把utf16的emoji表情字符进行转码成八进制的字符
export function utf16toEntities(str:String) {
    const patt = /[\ud800-\udbff][\udc00-\udfff]/g; // 检测utf16字符正则  

    // const  patt= /(\ud83c[\udf00-\udfff])|(\ud83d[\udc00-\ude4f\ude80-\udeff])|[\u2600-\u2B55]/g
    return str.replace(patt, function(char) {
        var H, L, code;
        if (char.length === 2) {
            H = char.charCodeAt(0); // 取出高位  
            L = char.charCodeAt(1); // 取出低位  
            code = (H - 0xD800) * 0x400 + 0x10000 + L - 0xDC00; // 转换算法  
            return "&#" + code + ";";
        } else {
            return char;
        }
    });
}


//将编码后的八进制的emoji表情重新解码成十六进制的表情字符
export function entitiesToUtf16(str:String) {
    return str.replace(/&#(\d+);/g, function(match, dec) {
        let H = Math.floor((dec - 0x10000) / 0x400) + 0xD800;
        let L = Math.floor(dec - 0x10000) % 0x400 + 0xDC00;
        return String.fromCharCode(H, L);
    });
}
