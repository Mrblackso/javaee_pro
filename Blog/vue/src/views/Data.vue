<template>
  <div>
<!--    <div style="width: 500px; height: 400px" id="main"></div>-->
<!--    <div style="width: 500px; height: 400px" id="line"></div>-->
    <div style="display: flex; grid-gap: 10px">
      <div class="card" style="flex: 1; height: 400px; background-color: white" id="bar"></div>
      <div class="card" style="flex: 1; height: 400px; background-color: white" id="line"></div>
    </div>
  </div>
</template>

<script setup>
import {reactive, onMounted} from 'vue';
import * as echarts from 'echarts';

const option = {
  title: {
    text: 'example'
  },
  tooltip: {},
  legend: {
    data: ['aa']
  },
  xAxis: {
    data: ['1', '2', '3', '4', '5']
  },
  yAxis: {},
  series: [
    {
      name: 'aa',
      type: 'bar',
      data: [1, 2, 3, 4, 5]
    }
  ]
};

const option1 = {
  title: {
    text: 'example'
  },
  tooltip: {},
  legend: {
    data: ['aa']
  },
  xAxis: {
    data: ['1', '2', '3', '4', '5']
  },
  yAxis: {},
  series: [
    {
      name: 'aa',
      type: 'line',
      data: [1, 2, 5, 4, 5]
    }
  ]
};

const data = reactive({

})

onMounted(() => {
  const myChart = echarts.init(document.getElementById('main'))
  myChart.setOption(option);
  const myChart1 = echarts.init(document.getElementById('line'))
  myChart1.setOption(option1)
})

</script>

<style scoped>

</style>
