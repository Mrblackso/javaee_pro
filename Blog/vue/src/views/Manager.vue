<template>
  <div style="display: flex; flex-direction: column; min-height: 100vh;">

    <!-- 头部 -->
    <div style="height: 60px; background-color: #3c7fff; display: flex;align-items: center">
      <div style="width: 200px;display: flex ;align-items: center;">
        <img src="../assets/logo.png" alt=""
             style="width: 40px;height: 40px ;padding-left: 10px ;padding-right: 6px">

        <span style="font-size: 20px;color: white">
          后台管理系统
        </span>
      </div>
      <div style="flex:1;"></div>
      <div style="width: 150px;display: flex ;align-items: center;padding-right: 10px">
        <img :src="data.user.avatar ||'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" alt=""
             style="width: 40px;height: 40px ;border-radius: 50%">
        <span style="color: white;margin-left: 5px">{{data.user.name}}</span>
      </div>
    </div>


    <!-- 主体内容 -->
    <div style="flex: 1; display: flex;">

      <!-- 左侧菜单 -->
      <div style="width: 200px; border-right: 1px solid #ddd; height: calc(100vh - 60px);">
        <!--     default-active="" 高亮显示    -->
        <el-menu
            router
            :default-active="router.currentRoute.value.path" :default-openeds="['1']" style="border: 0"
        >
          <!--        default-opends 默认展开设置-->

          <el-menu-item index="/manager/home">
            <el-icon>
              <House/>
            </el-icon>
            系统首页
          </el-menu-item>
          <!--          -->
          <el-menu-item index="/manager/data">
            <el-icon>
              <DataAnalysis/>
            </el-icon>
            数据统计
          </el-menu-item>
          <!--          -->

          <el-menu-item index="/manager/article">
            <el-icon>
              <Document/>
            </el-icon>
            文章管理
          </el-menu-item>

<!--          -->
          <el-sub-menu index="1">
            <template #title>
              <el-icon>
                <User/>
              </el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item index="/manager/Admin">管理员信息</el-menu-item>
            <el-menu-item index="/manager/Employee">员工信息</el-menu-item>
          </el-sub-menu>
          <!--          个人信息-->
          <el-menu-item index="/manager/person">
            <el-icon>
              <UserFilled/>
            </el-icon>
            个人信息
          </el-menu-item>
<!--          修改密码-->
          <el-menu-item index="/manager/password">
            <el-icon>
              <Lock/>
            </el-icon>
           修改密码
          </el-menu-item>

          <!--        退出登录-->
          <el-menu-item @click="logout">
            <el-icon>
              <SwitchButton/>
            </el-icon>
            退出登录
          </el-menu-item>
        </el-menu>


      </div>


      <!-- 右侧内容 -->
      <div style="flex: 1; padding: 10px; background-color: #f5f7ff;">
        <div style="; min-height: 100px;">
          <RouterView @updateuser="updateuser" />
        </div>
      </div>
    </div>

  </div>
</template>


<script setup>
import {useRouter} from 'vue-router'

const router = useRouter()
import {User} from '@element-plus/icons-vue'
import {House} from '@element-plus/icons-vue'
import {DataAnalysis, SwitchButton, UserFilled,Lock,Document} from '@element-plus/icons-vue'
 import {reactive} from 'vue'


const  data = reactive({
   user:JSON.parse(localStorage.getItem('token'))
})

const logout = () => {
  localStorage.removeItem('token')
  router.push('/login')
}

const updateuser = () => {
  data.user = JSON.parse(localStorage.getItem('token'))

}
</script>

<style>
.el-menu .is-active {
  background-color: #CAEDF3FF !important;
}

.el-menu .is-active > .el-sub-menu__title {
  background-color: #c3d3ef !important;
}
</style>
