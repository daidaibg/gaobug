import { request, requestPost, requestGet } from "../api";
import { MessageParamsWithType } from "element-plus";

export const Home = {
  blogList: "/index/blog/list", //首页博客列表
  category: "/blog/category/page", //博客分类列表
  userInfo: "/auth/cs/user/info", //用户详情
  blogSearch: "/index/blog/search", //头部博客搜索
  upload: "/platform/oss/upload", //上传文件

  deleteBlog: "/blog/del/", //删除博客
  addBlog: "/blog/add", //新增博客
  iBlogList: "/blog/page", //我的博客列表
  publish: "/blog/publish/", //发布博客
  blogDetail: "/blog/info/", //博客详情  需要登录
  indexBlogDetail: "/index/info/", //博客详情  不需要登录
  updataBlog: "/blog/update", //更新博客
  blogLike: "/blog/like", //点赞

  login: "/auth/cs/user/login", //登录
  register: "/auth/cs/user/register", //注册
  registerEmail: "/auth/cs/user/register", //邮箱注册
  captchaEmail: "/captcha/email", //验证码
};

export interface GetBlogListType {
  current: number;
  size: number;
  type: string;
  categoryId: string | number;
  keywords: string | number;
}

//获取博客列表
export const getBlog = (param: GetBlogListType) => {
  return requestGet<
    GetBlogListType,
    { code: number; data: { records: any; totalPage: number }; msg: MessageParamsWithType }
  >(Home.blogList, param);
};

//登录
export const postLogin = (param: any) => {
  return requestPost(Home.login, param);
  // return requestPost(Home.login, param, { enc: false, 'Content-Type': 'application/x-www-form-urlencoded' })
};
