
import { emojiObj } from "@/components/emoji/emoji";


export  const todayTime = () => {
    let hours = new Date().getHours();
    let hoursTip = ''
    
    if (hours >= 0 && hours < 12) {
        hoursTip = "上午好"
    } else if (hours >= 12 && hours < 18) {
        hoursTip = "下午好"
    } else {
        hoursTip = "晚上好"
    }
    return  hoursTip
}


export const handleCommen =(commen:string)=>{
    if (!commen) {
        return "";
      } else {
        commen = commen
          .replace(/&/g, "&amp;")
          .replace(/\</g, "&lt;")
          .replace(/\>/g, "&gt;")
          .replace(/\"/g, "&quot;")
          .replace(/'/g, "&apos;");
        let pattern1 = /\[[\u4e00-\u9fa5]+\]/g;
        let pattern2 = /\[[\u4e00-\u9fa5]+\]/;
        let content = commen.match(pattern1);
        // console.log(pattern1,content);
        let newStr = commen;
        if (content) {
          for (let i = 0; i < content.length; i++) {
            if (Object.prototype.hasOwnProperty.call(emojiObj, content[i])) {
              let imoj = `<img src="${emojiObj[content[i]]}" class="emoji"/>`;
              newStr = newStr.replace(pattern2, imoj);
            }
          }
        }
        // console.log(newStr);
        return newStr;
      }
}

/**
 * @description: 当前路径增加参数
 * @param {Object} query 当前路径参数
 * @param {string} queryKey key
 * @param {any} value value
 * @return {Object} 
 */
 export const addRouterParam = (query:any,queryKey: string, value: any) => {
  let newQuery:any = { ...query };
  if (value) {
    newQuery[queryKey] = value;
  } else {
    delete newQuery[queryKey];
  }
  return  newQuery
};

export function guid():string {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
      var r = Math.random() * 16 | 0,
          v = c == 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
  });
}

export const clone = function (obj: any) {
  let newObj: any = Array.isArray(obj) ? [] : {};
  if (obj && typeof obj === 'object') {
    for (let key in obj) {
      if (obj.hasOwnProperty(key)) {
        if (obj[key] && typeof obj[key] === 'object') {
          newObj[key] = clone(obj[key]);
        } else {
          newObj[key] = obj[key];
        }
      }
    }
  }
  return newObj;
};

// fast create dom with class
export function createClassDom (tag: string, className: string, innerText?: string) {
  let el = document.createElement(tag);
  el.setAttribute('class', className);
  if (innerText) el.innerText = innerText;
  return el;
}
