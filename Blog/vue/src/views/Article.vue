<template>

<!--  <div>-->
<!--    <h1>article</h1>-->
<!--  </div>-->
  <div>
    <div class="card" style="margin-bottom: 5px; background-color: white">
      <!--      <el-card>-->
      <!--      <span style="display: inline-flex; align-items: center; margin-right: 6px;margin-top: 14px">-->
      <!--        <el-icon><Search/></el-icon>-->
      <!--      </span>-->

      <!--      名字查询-->
      <el-input
          style="width: 240px"
          v-model="data.title"
          placeholder="请输入标题查询"
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

        <el-table-column label="标题" prop="title"/>
        <el-table-column label="封面" prop="img">

          <template #default="scope">

            <el-image v-if="scope.row.img" :src="scope.row.img"
                    :preview-src-list="[scope.row.img]"
                      preview-teleported
                 style="display: block;width: 100px; height: 60px;"
            ></el-image>
          </template>

        </el-table-column>

        <el-table-column label="简介" prop="description"  show-overflow-tooltip=""/>
<!---->

        <el-table-column label="内容" prop="content"  show-overflow-tooltip="">

          <template #default="scope">
            <el-button type="primary" @click="view(scope.row.content)">查看内容</el-button>
          </template>

        </el-table-column>



        <el-table-column label="发布时间" prop="time"/>


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
      <el-dialog v-model="data.formVisible" title="文章信息" width="50%" destroy-on-close>
        <el-form ref="formRef"  :rules="data.rules" :model="data.form" style="padding-right: 40px ;padding-top: 20px " label-width="80px">

          <el-form-item label="标题"  prop="title">
            <el-input  v-model="data.form.title" autocomplete="off" placeholder="请输入标题" />
          </el-form-item>

          <el-form-item label="封面" >

            <el-upload

                action="http://localhost:8080/files/upload"
                list_type="picture"
                :on-success="handleImgSuccess"
            >
              <el-button type="primary">上传封面</el-button>
            </el-upload>

          </el-form-item>


          <el-form-item label="简介"  prop="description" >
            <el-input type="textarea" :rows="3" v-model="data.form.description" autocomplete="off" placeholder="请输入简介" />
          </el-form-item>

          <el-form-item label="内容"  prop="content" >
            <div style="border:1px solid #ccc;width: 100%">
                <Toolbar  :editor="editorRef" :node="mode"  style="border-bottom: solid #ccc;width: 100%"/>
                <Editor
                    style="height: 500px;overflow: hidden"
                    v-model:value="data.form.content"
                    :editor="editorRef"
                    :mode="mode"
                    :defaultConfig="editorConfig"
                    @onCreated="handleCreated"
                />
            </div>
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

      <el-dialog v-model="data.viewVisible" title="文章内容" width="50%"  :close-on-click-outside="false"  destroy-on-close>
        <div v-html="data.content"  class="editor-content-view" style="padding:20px "></div>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="data.viewVisible = false">关 闭</el-button>
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
import '@wangeditor/editor/dist/css/style.css'
import {  onBeforeUnmount,shallowRef } from 'vue';
import {Editor,Toolbar} from "@wangeditor/editor-for-vue";
import '../assets/vue.css'

const data = reactive({
  title: null,
  tableData: [],
  pageNum: 1,
  pageSize: 10,
  total: 0,
  formVisible: false,
  form: {},
  ids: [],
  viewVisible:false,
  content:null,

});

const baseUrl= 'http://localhost:8080';
const editorRef=shallowRef()
const mode = 'default'
const editorConfig = {MENU_CONF:{}}
import { nextTick } from 'vue' // 确保导入nextTick

editorConfig.MENU_CONF['uploadImage']={
  server:  baseUrl+'/files/wang/upload',
  fieldName: 'file',
}
onBeforeUnmount(() => {
  const  editor = editorRef.value
  if(editor==null) return
  editor.destroy()
})

const handleCreated = (editor) => {
  editorRef.value = editor
  // 编辑器创建后立即检查是否有待设置的内容
  if (data.form.content) {
    nextTick(() => {
      editor.setHtml(data.form.content)
      console.log('编辑器初始化内容:', data.form.content)
    })
  }
}

//

const view = (content) => {
    data.content=content;
    data.viewVisible=true;
};

const load = () => {
  request.get('/article/selectPages', {
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
  console.log('保存前表单数据:', JSON.stringify(data.form))
  // formRef.value.validate(valid => {
  //   if (!valid) {
  //     ElMessage.warning('请填写必填字段');
  //     return;
  //   }
  if (editorRef.value) {
    data.form.content = editorRef.value.getHtml()
    console.log('编辑器内容:', data.form.content) // 调试日志
  }


    // 验证通过，开始保存
    if (data.form.id) {
      request.post('/article/updata', data.form).then(res => {
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

    request.post('/article/add', data.form).then(res => {
      if (res.code === '200') {
        ElMessage.success('新增成功');
        data.formVisible = false;
        load();
      } else {
        ElMessage.error('新增失败：' + res.msg);
      }
    });


};

const handleEdit = (row) => {
  data.formVisible = true;
  data.form = { ...row };


};


//删除
const del  = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复,请确认 qwq ','删除确认', {type : 'warning'}).then(()=>
      request.delete(`/article/deleteById/${id}`).then(res => {
        if (res.code === '200') {
          alert('删除成功');
          data.formVisible = false;
          load();
        } else {
          alert('删除失败：' + res.msg);
        }
      }).catch()
  )

  // request.delete(`/article/deleteById/${id}`).then(res => {
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
      request.delete(`/article/deleteBatch`, {data : data.ids}).then(res => {
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
const handleImgSuccess  = (res) => {
  console.log(res)
  data.form.img = res.data
}

const formRef = ref(null);

onMounted(() => {
  load();
});
</script>


<style scoped>

</style>