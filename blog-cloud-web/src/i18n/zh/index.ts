import home from './home'

const global = {
  // 头部
  doc: '说明文档',
  help: '帮助中心',
  contact: '联系我们',
  logout: '退出登录'

}

export default {
  global,
  ...home,
}
