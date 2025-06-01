<template>
  <div class="home">
    <h1>员工列表</h1>
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>姓名</th>
        <th>性别</th>
        <th>工号</th>
        <th>年龄</th>
        <th>描述</th>
        <th>部门ID</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="employee in data.employeeList" :key="employee.id">
        <td>{{ employee.id }}</td>
        <td>{{ employee.name }}</td>
        <td>{{ employee.sex }}</td>
        <td>{{ employee.no }}</td>
        <td>{{ employee.age }}</td>
        <td>{{ employee.description }}</td>
        <td>{{ employee.departmentId }}</td>
      </tr>

      </tbody>
    </table>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import request from "../utils/request.js"


const data = reactive({

  employeeList: []
})

request.get("/employee/selectAll").then((res) => {
  console.log(res)
  data.employeeList = res.data  // 注意：不要写成 res.data.data   res.data 是一个数组
})
</script>
