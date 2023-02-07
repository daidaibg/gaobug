/**
 * @description: 多久前
 * @param {string} time 2023-02-07 14:52:02
 * @return {*}
 */
export const getTimeInterval=(time: string):string =>{
    const currentTime = new Date();
    const inputTime = new Date(time);
    const timeDifference = currentTime.getTime() - inputTime.getTime();
  
    const seconds = Math.floor(timeDifference / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);
    const months = Math.floor(days / 30);
    const years = Math.floor(days / 365);
  
    if (seconds < 60) {
      return '刚刚';
    } else if (minutes < 60) {
      return `${minutes} 分钟前`;
    } else if (hours < 24) {
      return `${hours} 小时前`;
    } else if (days < 30) {
      return `${days} 天前`;
    } else if (months < 12) {
      return `${months} 个月前`;
    } else {
      return `${years} 年前`;
    }
  }

  