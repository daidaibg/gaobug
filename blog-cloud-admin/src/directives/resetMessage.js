/*
 * @Author: daidai
 * @Date: 2021-12-10 10:01:39
 * @LastEditors: daidai
 * @LastEditTime: 2021-12-13 18:22:55
 * @FilePath: \web-pc\src\directives\resetMessage.js
 */
import { Message } from 'element-ui';
let messageInstance = null;
const resetMessage = (options) => {
    // console.log(options)
    // console.log(messageInstance)
  if(messageInstance) {
    messageInstance.close()
  }
  messageInstance = Message(options)
};
['error','success','info','warning'].forEach(type => {
  resetMessage[type] = options => {
    if(typeof options === 'string') {
      options = {
        message:options
      }
    }
    options.type = type
    return resetMessage(options)
  }
})
export const message = resetMessage
