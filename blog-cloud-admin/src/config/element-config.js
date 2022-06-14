/*
 * @Author: daidai
 * @Date: 2021-12-16 14:26:37
 * @LastEditors: daidai
 * @LastEditTime: 2021-12-16 14:29:22
 * @FilePath: \web-pc\src\config\element-config.js
 */

// 日期快捷选择
export const pickerOptions= {
    // disabledDate(time) {
    //   return time.getTime() > Date.now();
    // },
    shortcuts: [{
      text: '今天',
      onClick(picker) {
        picker.$emit('pick', new Date());
      }
    }, {
      text: '昨天',
      onClick(picker) {
        const date = new Date();
        date.setTime(date.getTime() - 3600 * 1000 * 24);
        picker.$emit('pick', date);
      }
    }, {
      text: '一周前',
      onClick(picker) {
        const date = new Date();
        date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
        picker.$emit('pick', date);
      }
    }]
  }