import axios from "axios";
import { ElMessage } from "element-plus";

const request = axios.create({
    baseURL: "http://localhost:8080",
    timeout: 30000
});

// 请求拦截器
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    return config;
}, error => {
    return Promise.reject(error);
});

// 响应拦截器
request.interceptors.response.use(
    response => {
        let res = response.data;
        if (typeof res === "string" && res.trim() !== '') {
            try {
                res = JSON.parse(res);
            } catch (e) {
                // 忽略非 JSON 字符串
            }
        }
        return res; // ✅ 返回解析后的数据
    },
    error => {
        if (error.response) {
            switch (error.response.status) {
                case 404:
                    ElMessage.error("未找到请求入口");
                    break;
                case 500:
                    ElMessage.error("系统异常");
                    break;
                default:
                    ElMessage.error(error.message);
            }
        } else {
            ElMessage.error("网络连接失败");
        }
        return Promise.reject(error);
    }
);

export default request;
