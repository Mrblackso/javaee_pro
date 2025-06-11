<template>
  <div>
    <div class="card" style="margin-bottom: 5px; background-color: white">
      <!--      <el-card>-->
      <!--      <span style="display: inline-flex; align-items: center; margin-right: 6px;margin-top: 14px">-->
      <!--        <el-icon><Search/></el-icon>-->
      <!--      </span>-->

<!--      名字查询-->
      <el-input
          style="width: 240px"
          v-model="data.name"
          placeholder="请输入名称查询"
          :prefix-icon="Search"
      ></el-input>
      <el-button type="primary" @click="load">查 询</el-button>
      <el-button type="warning" @click="reset">重 置</el-button>
      <!--      </el-card>-->
      <!--      <button @click="">提交</button>-->
    </div>
    <div class="card" style="margin-bottom: 5px; background-color: white">
      <el-button type="primary" @click="handleAdd">新 增</el-button>
      <el-button type="danger"  @click="delBatch">批量删除</el-button>
<!--      <el-button type="info">导 入</el-button>-->
<!--      <el-button type="success">导 出</el-button>-->

    </div>

    <div class="card" style="margin-bottom: 5px">
<!--           @selection-change="handleSelectionChange" 要加在表格内     -->
      <el-table :data="data.tableData" stripe   @selection-change="handleSelectionChange"  >

        <el-table-column type="selection"  stripe  width="55"/>

        <el-table-column label="账号" prop="username"/>
        <el-table-column label="头像" prop="avatar">
          <template #default="scope">
            <img v-if="scope.row.avatar" :src="scope.row.avatar"
                 style="display: block;width: 40px; height: 40px; border-radius:50%"
            />
          </template>
        </el-table-column>
        <el-table-column label="名称" prop="name"/>
<!--        操作部分-->
        <el-table-column label="操作"  width="120px">
          <template #default="scope">
            <!-- link功能: 去掉样式 按钮的 边框-->
            <!-- #default="scope" 功能: 绑定当前行数据 -->
            <!-- circle 圆形按钮-->
            <!--  -->

            <el-button type="primary"  @click="handleEdit(scope.row)"  :icon="Edit" circle></el-button>
            <el-button type="warning"  @click="del(scope.row.id)"   :icon="Delete" circle></el-button>
          </template>
        </el-table-column>


      </el-table>

<!--      @current-change="load"-->
<!--      &lt;!&ndash;            实时加载 第几页的数据&ndash;&gt;-->

<!--      @size-change="load"-->
<!--      &lt;!&ndash;             实时加载 一页的数据量 &ndash;&gt;-->
      <div style="margin-top:15px">
        <el-pagination
            @current-change="load"
             @size-change="load"
            v-model:current-page="data.pageNum"
            v-model:page-size="data.pageSize"
            :page-sizes="[5, 10, 15, 20]"
            background
            layout="total, sizes, prev, pager, next, jumper"
            :total="data.total"
        />

<!--        新增窗口 -->
      </div>
<!--      关闭时销毁 destroy-on-close-->
        <el-dialog v-model="data.formVisible" title="员工信息" width="500" destroy-on-close>
          <el-form ref="formRef"  :rules="data.rules" :model="data.form" style="padding-right: 40px ;padding-top: 20px " label-width="80px">

            <el-form-item label="账号"  prop="username">
              <el-input disabled v-model="data.form.username" autocomplete="off" placeholder="请输入账号" />
            </el-form-item>

            <el-form-item label="头像" >

              <el-upload

                  action="http://localhost:8080/files/upload"
                  list_type="picture"
                  :on-success="handleAvatarSuccess"
              >
                <el-button type="primary">上传头像</el-button>
              </el-upload>

            </el-form-item>


            <el-form-item label="名称"  prop="name" >
              <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入名称" />
            </el-form-item>


          </el-form>


<!--          -->
          <template #footer>
            <div class="dialog-footer">

<!--             data.formVisible = false   注意 formVisible  'f'是小写-->
              <el-button @click="data.formVisible = false">取消</el-button>
              <el-button type="primary" @click="save">保 存</el-button>

            </div>
          </template>
        </el-dialog>

     </div>


  </div>
</template>


<script setup>
import { ElMessageBox,ElMessage } from 'element-plus';
import { reactive, onMounted , ref,} from 'vue';
import request from '../utils/request';
import { Search, Edit, Delete } from '@element-plus/icons-vue';
const data = reactive({
  name: null,
  tableData: [],
  pageNum: 1,
  pageSize: 10,
  total: 0,
  formVisible: false,
   form: {},
  ids: [],
  rules: {
    username: [
      { required: true, message: '请输入账号名称', trigger: 'blur' },
    ],
    name: [
      { required: true, message: '请输入名称', trigger: 'blur' },
    ],

   }
});


const load = () => {
  request.get('/admin/selectPages', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name : data.name ,

    }
  }).then(res => {
    console.log('接口返回：', res);
    if (res.code === '200') {
      data.tableData = res.data.list.map(item => ({
        ...item,
        // departmentName: item.departmentId // 前端名字数据库不同时进行映射
      }));
      data.total = res.data.total;
    //  设置前端显示数据条数
    } else {
      alert('加载失败：' + res.msg);
    }
  }).catch(err => {
    console.error('请求异常：', err);
    alert('请求失败，请检查网络或后端服务');
  });
};

const  reset = () => {
  // 参数清空  重新加载
  data.name = null;
  load();
};

const handleAdd = () => {
  data.formVisible = true;
  data.form = {};
};
const save = () => {
  formRef.value.validate(valid => {
    if (!valid) {
      ElMessage.warning('请填写必填字段');
      return;
    }

    // 验证通过，开始保存
    if (data.form.id) {
      request.post('/admin/updata', data.form).then(res => {
        if (res.code === '200') {
          ElMessage.success('更新成功');
          data.formVisible = false;
          load();
        } else {
          ElMessage.error('更新失败：' + res.msg);
        }
      });
      return;
    }

    request.post('/admin/add', data.form).then(res => {
      if (res.code === '200') {
        ElMessage.success('新增成功');
        data.formVisible = false;
        load();
      } else {
        ElMessage.error('新增失败：' + res.msg);
      }
    });
  });
};

const handleEdit = (row) => {
  data.formVisible = true;
  data.form = { ...row };
//  data.form = { ...row } 实现 数据的复制
};


//删除
const del  = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复,请确认 qwq ','删除确认', {type : 'warning'}).then(()=>
      request.delete(`/admin/deleteById/${id}`).then(res => {
         if (res.code === '200') {
          alert('删除成功');
          data.formVisible = false;
          load();
        } else {
          alert('删除失败：' + res.msg);
        }
      }).catch()
  )

  // request.delete(`/employee/deleteById/${id}`).then(res => {
  //
  //    if (res.code === '200') {
  //      alert('删除成功');
  //      data.formVisible = false;
  //      load();
  //    } else {
  //      alert('删除失败：' + res.msg);
  //    }
  //  });
};

// 返回所有的 行对象
const handleSelectionChange = (rows)=>{
   console.log(rows);
//    从选中的 行数组 中 所有的 行Id组成新的 数组
    data.ids = rows.map(row => row.id);
    console.log(data.ids);
}

const delBatch = () => {

  if( data.ids.length === 0){
      // alert('请选择要删除的行');
      ElMessage.warning( '请选择要删除的行')
      return
  }

  ElMessageBox.confirm('删除后数据无法恢复,请确认 qwq ','删除确认', {type : 'warning'}).then(()=>
      request.delete(`/admin/deleteBatch`, {data : data.ids}).then(res => {
         if (res.code === '200') {
          // alert('删除成功');
           ElMessage.success( '删除成功')
          // data.formVisible = false;
          load();
        } else {
          ElMessage.error( '删除失败：' + res.msg)
        }
      }).catch()
  )
 }
const handleAvatarSuccess  = (res) => {
  console.log(res)
  data.form.avatar = res.data
}

const formRef = ref(null);

onMounted(() => {
  load();
});
</script>


<style scoped>

</style>