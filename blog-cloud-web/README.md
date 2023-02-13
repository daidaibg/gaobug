# vue3+vite+ts+pinia
采用主流前端框架
- 💪 Vue 3 Composition API![vue3](https://img.shields.io/badge/vue-3-brightgreen)
- 🔥 Written in TypeScript ![TS](https://img.shields.io/badge/TS-4-brightgreen)
- 🛠️从一组受限的原始实用程序构建复杂的组件tailwindcss![tailwindcss](https://img.shields.io/badge/tailwindcss-2-green)
- 💡 Instant Server Start![vite](https://img.shields.io/badge/vite-2-blue)

## 预览地址
https://www.gaobug.com/

## 请求增加md5加密验签方式，方式参数篡改

自己可以修改加密方式

## 项目使用

### 安装

```node
npm i
```

### 运行

默认运行history路径

```node
npm run dev
```

如果需要hash路径 请运行

```js
npm run dev:hash
```

### 打包

默认打包history路径

```js
npm run build 
```

如需打包hash路径请运行

```js
npm run build:hash
```

###  目录结构
src/view   文件目录

### 升级依赖

```node
npm install -g npm-check-updates
ncu -u
npm install
```

