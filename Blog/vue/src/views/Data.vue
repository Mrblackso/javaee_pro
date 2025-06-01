<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <!--      <el-card>-->
<!--      <span style="display: inline-flex; align-items: center; margin-right: 6px;margin-top: 14px">-->
<!--        <el-icon><Search/></el-icon>-->
<!--      </span>-->

      <el-input
          style="width: 240px"
          v-model="data.name"
          placeholder="请输入名称查询"
          :prefix-icon="Search"
      ></el-input>
      <el-button type="primary">查 询</el-button>
      <el-button type="warning">新 增</el-button>
      <!--      </el-card>-->
      <!--      <button @click="">提交</button>-->
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary">新 增</el-button>
      <el-button type="warning">批量删除</el-button>
      <el-button type="info">导 入</el-button>
      <el-button type="success">导 出</el-button>

    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" stripe>
        <el-table-column label="日期" prop="date"/>
        <el-table-column label="名称" prop="name"/>
        <el-table-column label="地址" prop="address"/>
      </el-table>

      <div style="margin-top:15px">
        <el-pagination
            v-model:current-page="data.pageNum"
            v-model:page-size="data.pageSize"
            :page-sizes="[5, 10, 15, 20]"
            background
            layout="total, sizes, prev, pager, next, jumper"
            :total="data.total"
        />
      </div>

    </div>
  </div>
</template>

<script setup>
import {reactive} from 'vue'
import {Search} from '@element-plus/icons-vue'

const data = reactive({
  name: null,
  tableData: [
    {
      id: 1, date: '2025-6-1', name: '张三', address: '上海'
    },
    {
      id: 2, date: '2025-6-1', name: '李四', address: '临海'
    },
  ],
  pageNum: 1,
  pageSize: 10,
  total: 47,
})
</script>

<style scoped>

</style>
