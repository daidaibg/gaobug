import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { setupStore } from '@/store'
import setDirectives from "./directives";
import YhPlus from 'yhht-plus'
import i18n from './i18n';
// import 'element-plus/dist/index.css'
import "yhht-plus/lib/style.css"
import './assets/tailwind.css'
import "./assets/css/index.scss";
const app=createApp(App)
// 注册所有自定义指令
setDirectives(app);
// 挂载状态管理
setupStore(app)
// console.log(YhPlus);
// 注册自定义组件

app.use(YhPlus);
app.use(i18n);

app.use(router).mount('#app')
