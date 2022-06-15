import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { setupStore } from '@/store'
import setDirectives from "./directives";
import CodeLight from '@/components/codes/code-highlights.vue'
import CodeWrap from '@/components/codes/Code-wrap.vue'
import TableParam from '@/components/scFormTable/table-code.vue'
import YhPlus from 'yhht-plus'
import i18n from './i18n';
// import 'element-plus/dist/index.css'
import "yhht-plus/lib/theme/index.css"
import "./assets/css/code.scss";
import './assets/tailwind.css'
import "./assets/css/index.scss";
const app=createApp(App)
// 注册所有自定义指令
setDirectives(app);
// 挂载状态管理
setupStore(app)
// console.log(YhPlus);
// 注册自定义组件
app.component('code-light',CodeLight)
app.component('code-wrap',CodeWrap)
app.component("TableParam",TableParam)
app.use(YhPlus);
app.use(i18n);

app.use(router).mount('#app')
