<template>
  <div class="login-container">

    <div class="login_box">

      <div style="box-shadow: 0 0 10px rgba(0, 0, 0, 0.2); ">

        <el-form ref="formRef"  :rules="data.rules" :model="data.form"  style="width: 450px ;border-radius: 5px" >
          <div style="margin-bottom: 30px;font-size: 30px;color: aquamarine ;font-weight:  bold ;text-align: center;">欢迎登录后台管理系统</div>
<!--          -->
          <el-form-item style="padding-left: 15px" prop="username">
            <el-input size="large" v-model="data.form.username" placeholder="请输入账号" :prefix-icon="User" style="height: 50px"></el-input>
          </el-form-item>
          <el-form-item  style="padding-left: 15px" prop="password">
            <el-input show-password size="large" v-model="data.form.password " placeholder="请输入密码" :prefix-icon="Lock" style="height: 50px"></el-input>
          </el-form-item>

          <el-form-item  style="padding-left: 15px;height: 50px"  prop="role">
            <el-select v-model="data.form.role">
              <el-option label="管理员" value="ADMIN"></el-option>
              <el-option label="员工" value="EMP"></el-option>
            </el-select>
          </el-form-item>
<!---->
          <div style="padding-left: 15px">
              <el-button size="large" type="primary" style="width: 100%;height: 40px;margin-top: 10px;" @click="login">登 录</el-button>
          </div>
          <div style="text-align: right;padding-top: 5px ;color: white">还没有账号？ 请 <a style="color: deepskyblue;text-decoration: none" href="/register">注册</a></div>
          <div style="height: 5px"></div>
        </el-form>
      </div>
    </div>

  </div>
</template>


<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'
import request from '../utils/request'
import { ElMessage } from 'element-plus'

const router = useRouter()

const data = reactive({
  form: {role:'ADMIN'},
  rules: {
    username: [
      { required: true, message: '请输入账号', trigger: 'blur' }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' }
    ]
  }
})

const formRef = ref()

const login = () => {
  formRef.value.validate(valid => {
    if (valid) {
      request.post('/login', data.form).then(res => {
        if (res.code == 200) {
          localStorage.setItem('token', JSON.stringify(res.data))  //json 转换成 json 字符串
          ElMessage.success('登录成功')
          router.push('/manager/home') // Vue Router 跳转
        } else {
          ElMessage.error(res.msg)
        }
      }).catch(err => {
        console.error('请求失败:', err)
        ElMessage.error('网络异常，请重试')
      })
    } else {
      ElMessage.warning('请填写完整信息')
      return false
    }
  })
}
</script>


<style>

 /*600 *450*/
.login_box {
  width: 470px;
  height: 620px;
  align-items: center;

}

.login-container {
  min-height: 100vh;
  background-image: url("../assets/bg.png");
  background-size: cover;
  background-position: center;
  padding-top: 22vh; /* 留出顶部 25%，即内容从 1/4 处开始 */
  box-sizing: border-box;
  display: flex;

  justify-content: center; /* 水平居中 */
  /*align-items: center;     !* 垂直居中 *!*/
}


</style>