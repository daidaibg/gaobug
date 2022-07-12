import zh from '../language/zh.json'
const zhModules:any =import.meta.globEager('./*.ts') 
const modules:any = {}
for (const key in zhModules) {
  modules[key.replace(/(\.\/|\.ts)/g, '')] = zhModules[key].default
}
const global = {
  // 头部
  doc: '说明文档',
  help: '帮助中心',
  contact: '联系我们',
  logout: '退出登录'

}

export default {
  global,
  ...zh,
  ...modules
}
