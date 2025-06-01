<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
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
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" @click="handleAdd">新 增</el-button>
      <el-button type="warning">批量删除</el-button>
<!--      <el-button type="info">导 入</el-button>-->
<!--      <el-button type="success">导 出</el-button>-->

    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" stripe>
        <el-table-column label="名称" prop="name"/>
        <el-table-column label="性别" prop="sex"/>
        <el-table-column label="工号" prop="no"/>
        <el-table-column label="年龄" prop="age"/>
        <el-table-column label="个人介绍" prop="description" show-overflow-tooltip />
<!--        show-overflow-tooltip 放不下时 鼠标 移动 显示全部 -->
        <el-table-column label="部门" prop="departmentName"/>

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
        <el-dialog v-model="data.formVisible" title="员工信息" width="500">
          <el-form :model="data.form" style="padding-right: 40px ;padding-top: 20px " label-width="80px">

            <el-form-item label="名称">
              <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入名称" />
            </el-form-item>

            <el-form-item label="性别" >
              <el-radio-group v-model="data.form.sex">
                 <el-radio label="男"/>
                 <el-radio label="女"/>
              </el-radio-group>
            </el-form-item>
             <el-form-item label="工号">
              <el-input v-model="data.form.no" autocomplete="off" placeholder="请输入工号"/>
            </el-form-item>
             <el-form-item label="年龄">
              <el-input-number   style="width: 180px" :min="18" v-model="data.form.age" autocomplete="off" placeholder="请输入年龄"/>
            </el-form-item>
            <el-form-item label="个人介绍">
              <el-input  rows="3"  type="textarea" v-model="data.form.description" autocomplete="off" placeholder="请输入个人介绍"/>
            </el-form-item>
            <el-form-item label="所在部门">
              <el-input  v-model="data.form.departmentName" autocomplete="off" placeholder="请输入所在部门"/>
            </el-form-item>
          </el-form>


<!--          -->
          <template #footer>
            <div class="dialog-footer">
              <el-button @click="data.FormVisible = false">取消</el-button>
              <el-button type="primary" @click="save">保 存</el-button>
            </div>
          </template>
        </el-dialog>

     </div>


  </div>
</template>


<script setup>
import { reactive, onMounted } from 'vue';
import request from '../utils/request';

const data = reactive({
  name: null,
  tableData: [],
  pageNum: 1,
  pageSize: 10,
  total: 0,
  formVisible: false,
   form: {},
});


const load = () => {
  request.get('/employee/selectPages', {
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
        departmentName: item.departmentId // 前端名字数据库不同时进行映射
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
    request.post('/employee/add', data.form).then(res => {
      if (res.code === '200') {
        alert('保存成功');
        data.formVisible = false;
        load();
      //  新增后 重载信息
      } else {
        alert('保存失败：' + res.msg);
      }
    });
};

onMounted(() => {
  load();
});
</script>


<style scoped>

</style>