# java EE 期末作业

---
**[注意该处数据库修改为自己的数据库连接配置](src/main/resources/application.yml)**

---
## 流程图
```mermaid
sequenceDiagram
    title 前后端业务交互流程图
        前端浏览器->>后端服务器: 发起请求
        后端服务器->>数据库: Mybatis+MySQL获取数据
        数据库->>后端服务器: 转换为Java对象
        后端服务器->>前端浏览器: 转化为Json对象并发送
```

---
## 数据模型设计
### Employee用户模型
| 字段       | 类型     |说明|
|----------|--------|-|
| username | string ||
| password | string ||
| role     |||


