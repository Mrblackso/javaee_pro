<template>
  <div>
<!--    <div style="width: 500px; height: 400px" id="main"></div>-->
<!--    <div style="width: 500px; height: 400px" id="line"></div>-->
    <div style="display: flex; grid-gap: 10px">
      <div class="card" style="flex: 1; height: 670px; background-color: white" id="line"></div>
<!--      <div class="card" style="flex: 1; height: 400px; background-color: white" id="line"></div>-->
    </div>
  </div>
</template>

<script setup>
import {reactive, onMounted} from 'vue';
import * as echarts from 'echarts';
import request from "../utils/request.js";

const lineOption = {
  title: {
    text: '近七天发布文章的数量'
  },
  tooltip: {},
  legend: {
    data: ['发布文章数量']
  },
  xAxis: {
    data: []
  },
  yAxis: {},
  series: [
    {
      name: '发布数量',
      type: 'line',
      data: [],
      smooth: true
    }
  ]
};

const data = reactive({

})

onMounted(() => {
  const lineChart = echarts.init(document.getElementById('line'))
  request.get('/lineData').then(res => {
    lineOption.xAxis.data = res.data.date
    lineOption.series[0].data = res.data.count
    lineChart.setOption(lineOption)
  })
})

</script>

<style scoped>

</style>
