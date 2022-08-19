
import { emojiObj } from "@/components/emoji/emoji";


export  const todayTime = () => {
    let date = new Date();
    let hoursTip = ''
    if (date.getHours() >= 0 && date.getHours() < 12) {
        hoursTip = "上午好"
    } else if (date.getHours() >= 12 && date.getHours() < 18) {
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

