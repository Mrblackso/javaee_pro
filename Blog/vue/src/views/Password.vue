<template>

  <div class="card" style=" width: 50%;padding :40px 20px">
    <el-form ref="formRef"  :rules="data.rules" :model="data.form" label-width="100px" style="width: 450px ;border-radius: 5px" >
      <el-form-item label="原密码" prop="password">
        <el-input show-password v-model="data.form.password" placeholder="请输入原密码" type="password"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input show-password v-model="data.form.newPassword" placeholder="请输入新密码" type="newPassword"></el-input>
      </el-form-item>

      <el-form-item label="确认新密码" prop="confirmPassword">
        <el-input show-password v-model="data.form.confirmPassword" placeholder="请再次确认密码" type="confirmPassword"></el-input>
      </el-form-item>
    </el-form>

    <div style="text-align: center">
      <el-button type="primary" @click="updatePassword"  style="padding:20px 40px">立即修改</el-button>
    </div>
  </div>

</template>

<script setup>
import {reactive, ref} from "vue";
import request from "../utils/request.js";
import {ElMessage} from "element-plus";
const formRef = ref()



const validatePass = (rule, value, callback) => {

  if(!value){
    callback(new Error('请再次确认密码'))
  } else if(value !== data.form.newPassword){
    callback(new Error('两次密码不一致'))
  }else{
    callback()
  }

}
/*

    private Integer id;
    // 密码
    private String password;
    // 用户名
    private String username;
    // 角色
    private String role;
    // 名称
    private String name;

    private String newPassword;

*/


const data = reactive({
  user:  JSON.parse(localStorage.getItem('token')),
    form: {
      id: null,
      password: '',  // 原密码
      username:'',
      role: '',
      name:'',
      newPassword: '',  // 新密码
      confirmPassword: ''  // 确认密码
  },
  rules:{
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' }
    ],
    newPassword: [
      { required: true, message: '请输入新密码', trigger: 'blur' }
    ],
    confirmPassword: [
      { validator: validatePass, trigger: 'blur' }
    ],
  }

})
const updatePassword = () => {
  data.form.id = data.user.id;
  data.form.role = data.user.role;

  console.log("提交数据:", data.form); // 打印完整表单数据

  formRef.value.validate((valid) => {
    if (valid) {
      // 构造要提交的数据对象
      const submitData = {
        id: data.form.id,
        role: data.form.role,
        password: data.form.password,    // 原密码
        newPassword: data.form.newPassword // 新密码
      };
      console.log("提交数据:", submitData);
      request.put('/updatePassword', submitData).then(res => {
        if (res.code === '200') {
          ElMessage.success('修改成功');
          localStorage.removeItem('token');
          setTimeout(() => {
            location.href = '/login';
          }, 500);
        } else {
          ElMessage.error(res.msg || '修改失败'); // 显示后端返回的错误信息
        }
      }).catch(error => {
        console.error('请求错误:', error);
        ElMessage.error('请求失败: ' + (error.message || '未知错误'));
      });
    }
  });
};

</script>