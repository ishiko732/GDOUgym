<template>
  <div>
    <div class="top">
      <div>
        <img src="https://www.gdou.edu.cn/img/logo1.png" class="icon">
      </div>
      <div>
        <img src="https://www.gdou.edu.cn/img/logo2.png" class="text">
      </div>

    </div>
<!--    <div class="nav">-->
<!--      <el-menu class="el-menu-demo" mode="horizontal">-->
<!--          <el-menu-item index="1" style="margin-left: 38%" @click="userManagement"-->
<!--          :style="this.$route.path=='/home/userManagement'?'border-bottom: 2px solid #409EFF;':''">用户管理</el-menu-item>-->
<!--          <el-menu-item index="2" @click="siteManagement"-->
<!--          :style="this.$route.path=='/home/siteManagement'?'border-bottom: 2px solid #409EFF;':''">场地管理</el-menu-item>-->
<!--          <el-menu-item index="3" @click="eventManagement"-->
<!--          :style="this.$route.path=='/home/eventManagement'?'border-bottom: 2px solid #409EFF;':''">赛事管理</el-menu-item>-->
<!--          <el-menu-item index="4" @click="equipmentManagement"-->
<!--          :style="this.$route.path=='/home/equipmentManagement'?'border-bottom: 2px solid #409EFF;':''">器材管理</el-menu-item>-->
<!--      </el-menu>-->
<!--    </div>-->
    <el-menu
        class="el-menu-demo"
        mode="horizontal"
        background-color="rgba(14,44,116)"
        text-color="#fff"
        active-text-color="#ffd04b">
      <el-menu-item index="1"></el-menu-item>
    </el-menu>
    <el-col :span="3">
      <el-menu
          class="el-menu-vertical-demo"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          style="height: 1000px">
        <el-menu-item index="1" @click="userManagement"
                      :style="this.$route.path=='/home/userManagement'?'color: rgb(255, 208, 75);':''"> <i class="el-icon-setting"></i>用户管理</el-menu-item>
        <el-menu-item index="2" @click="siteManagement"
                      :style="this.$route.path=='/home/siteManagement'?'color: rgb(255, 208, 75);':''"> <i class="el-icon-setting"></i>场地管理</el-menu-item>
        <el-menu-item index="3" @click="eventManagement"
                      :style="this.$route.path=='/home/eventManagement'?'color: rgb(255, 208, 75);':''"> <i class="el-icon-setting"></i>赛事管理</el-menu-item>
        <el-menu-item index="4" @click="equipmentManagement"
                      :style="this.$route.path=='/home/equipmentManagement'?'color: rgb(255, 208, 75);':''"> <i class="el-icon-setting"></i>器材管理</el-menu-item>
      </el-menu>
    </el-col>
    <router-view></router-view>
  </div>
</template>

<script>



export default {
  name: 'Home',
  data(){
    return{

    }
  },
  methods:{
    equipmentManagement(){
      this.$router.push({path:'/home/equipmentManagement'})
    },
    userManagement(){
      this.$router.push({path:'/home/userManagement'})
    },
    siteManagement(){
      this.$router.push({path:'/home/siteManagement'})
    },
    eventManagement(){
      this.$router.push({path:'/home/eventManagement'})
    },
  },
  created() {
  },
}
</script>
<style lang="less" scoped>
.el-menu-item{
  font-size: 18px;
}
.nav{
  font: normal 24px/45px "microsoft YaHei";
}
.top{
  display: flex;
  margin-left: 40%;
  margin-top: 5px;
  .text{
    margin-left: 10px;
    margin-top: 23%;
  }
}
.el-menu.el-menu--horizontal {
  border: none;
}
</style>