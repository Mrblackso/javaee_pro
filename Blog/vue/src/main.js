import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'

// 导入封装好的 axios 实例
import request from './utils/request'

const app = createApp(App)

//  正确挂载 axios 到全局属性
app.config.globalProperties.$axios = request

app.use(router)
app.use(ElementPlus, {
    locale: zhCn,
})

app.mount('#app')
