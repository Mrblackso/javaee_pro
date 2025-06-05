<template>

    <div   class="card" style="width: 50% ;padding-left: 20px;padding-top: 10px">
        <el-form ref="formRef"  :rules="data.rules" :model="data.form" style="padding-right: 40px ;padding-top: 20px " label-width="80px">

<!--          disabled 禁用-->
          <el-form-item label="账号"  prop="username">
            <el-input disabled v-model="data.form.username" autocomplete="off" placeholder="请输入账号" />
          </el-form-item>

          <el-form-item label="名称"  prop="name" >
            <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入名称" />
          </el-form-item>


          <div v-if="data.user.role=== 'EMP'">
            <el-form-item label="性别">
              <el-radio-group v-model="data.form.sex">
                <el-radio value="男">男</el-radio>
                <el-radio value="女">女</el-radio>
              </el-radio-group>
            </el-form-item>


            <el-form-item label="工号" prop="no">
              <el-input disabled v-model="data.form.no" autocomplete="off" placeholder="请输入工号"/>
            </el-form-item>

            <el-form-item label="年龄">
              <el-input-number   style="width: 180px" :min="18" v-model="data.form.age" autocomplete="off" placeholder="请输入年龄"/>
            </el-form-item>

            <el-form-item label="个人介绍">
              <el-input :rows="3" type="textarea" v-model="data.form.description" autocomplete="off" placeholder="请输入个人介绍"/>
            </el-form-item>

            <el-form-item label="所在部门">
              <el-input  v-model="data.form.departmentId" autocomplete="off" placeholder="请输入所在部门"/>
            </el-form-item>

          </div>


        </el-form>


        <!--          -->
        <div style="text-align: center">
          <el-button type="primary" @click="updateuser"  style="padding:20px 40px">更新个人信息</el-button>
        </div>
    </div>

</template>


<script setup>
import { ElMessageBox,ElMessage } from 'element-plus';
import { reactive, onMounted , ref,} from 'vue';
import request from '../utils/request';
import { Search, Edit, Delete } from '@element-plus/icons-vue';

const formRef = ref()

const data = reactive({
  user: JSON.parse(localStorage.getItem('token')),
  form: {},
  rules: {
    username: [
      { required: true, message: '请输入账号名称', trigger: 'blur' },
    ],
    name: [
      { required: true, message: '请输入名称', trigger: 'blur' },
    ],
    no: [
      { required: true, message: '请输入工号', trigger: 'blur' },
    ]

  }
});

const emit=defineEmits(
    ['updateuser']
)

if(data.user.role==='EMP'){
  request.get(`employee/selectById/${data.user.id}`).then(res => {
    data.form=data.user
  })
}else {
  data.form=data.user
}

const updateuser = () => {
  if(data.user.role==='EMP'){
    request.post('/employee/updata',data.form).then(res => {
      if(res.code==='200'){
        ElMessage.success('更新成功')
        //更新缓存数据
        localStorage.setItem('token',JSON.stringify(data.form))
        //更新父组件数据
        emit('updateuser')
      }else{
        ElMessage.error('更新失败')
      }
    })
  }else{
    request.post('/admin/updata',data.form).then(res => {
      if(res.code==='200'){
        ElMessage.success('更新成功')
        //更新缓存数据
        localStorage.setItem('token',JSON.stringify(data.form))
        //更新父组件数据
        emit('updateuser')
      }else{
        ElMessage.error('更新失败')
      }
    })
  }

};

</script>