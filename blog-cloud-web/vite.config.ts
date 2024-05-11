import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from "path";
//@ts-ignore
import ElementPlus from 'unplugin-element-plus/vite'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

// console.log(process.env);
// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {

  const env = loadEnv(mode, process.cwd())
  // console.log(command, mode);
  // console.log(opt);
  console.log(env);
  return {
    plugins: [vue(),
    ElementPlus(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()]
    }),
    ],
    publicDir: "public",
    base: env.VITE_PREFIX,
    server: {
      host: '0.0.0.0',
      port: 8118,
      open: false,
      strictPort: false,
      // proxy: { // 代理 
      // 字符串简写写法
      // '/foo': 'http://localhost:4567/foo',
      // 选项写法
      // '/api': {
      //   target: 'http://jsonplaceholder.typicode.com',
      //   changeOrigin: true,
      //   rewrite: (path) => path.replace(/^\/api/, '')
      // },
      // 正则表达式写法
      // '^/app/.*': {
      //   target: 'http://www.xihuanmantou.cn:19527/app',
      //   changeOrigin: true,
      //   rewrite: (path) => path.replace(/^\/fallback/, '')
      // }

      // }
    },
    resolve: {
      alias: {
        "@": resolve(__dirname, "./src"),
        "components": resolve(__dirname, "./src/components"),
        "api": resolve(__dirname, "./src/api"),
        // 'vue-i18n': 'vue-i18n/dist/vue-i18n.cjs.js'
      },
    },
    css: {
      // css预处理器
      preprocessorOptions: {
        scss: {
          // charset: false,
          // additionalData: '@import "./src/assets/css/variable.scss";',
          additionalData: `@use "./src/assets/css/variable.scss" as *;`,
        },
      },
    },
    optimizeDeps: {
      include: [
        `monaco-editor/esm/vs/language/json/json.worker`,
        `monaco-editor/esm/vs/language/css/css.worker`,
        `monaco-editor/esm/vs/language/html/html.worker`,
        `monaco-editor/esm/vs/language/typescript/ts.worker`,
        `monaco-editor/esm/vs/editor/editor.worker`,
      ],
    },
    build: {
      outDir: 'dist',
      chunkSizeWarningLimit: 500,
      rollupOptions: {
        external: [
          // 'highlight.js'
        ],
        plugins: [
        
        ],
        output: {
          // globals: {
          //   'highlight.js': 'hljs',  //暂不支持 使用上边plugins.externalGlobals方式
          // }
          // manualChunks(id) {
          //   if (id.includes('node_modules')) {
          //     const arr = id.toString().split('node_modules/')[1].split('/')
          //     switch(arr[0]) {
          //       case 'highlight.js':
          //         return '_' + arr[0]
          //         break
          //       default :
          //         return '__vendor'
          //         break
          //     }
          //     return id.toString().split('node_modules/')[1].split('/')[0].toString();
          //   }
          // }
        }
      }
      // lib: {
      //   entry: 'packages/index.js',
      //   formats: ['es'],
      //   fileName: (format) => `index.${format}.js`
      // },
    },


  }

})
