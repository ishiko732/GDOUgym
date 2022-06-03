<template>
  <div>
    <div class="top">
      <img src="https://www.gdou.edu.cn/img/logo1.png" class="icon">
      <img src="https://www.gdou.edu.cn/img/logo2.png" class="text">
    </div>
    <div class="nav">
      <el-menu class="el-menu-demo" mode="horizontal">
          <el-menu-item index="1" style="margin-left: 38%" @click="userManagement"
          :style="this.$route.path=='/home/userManagement'?'border-bottom: 2px solid #409EFF;':''">用户管理</el-menu-item>
          <el-menu-item index="2" @click="siteManagement"
          :style="this.$route.path=='/home/siteManagement'?'border-bottom: 2px solid #409EFF;':''">场地管理</el-menu-item>
          <el-menu-item index="3" @click="eventManagement"
          :style="this.$route.path=='/home/eventManagement'?'border-bottom: 2px solid #409EFF;':''">赛事管理</el-menu-item>
          <el-menu-item index="4" @click="equipmentManagement"
          :style="this.$route.path=='/home/equipmentManagement'?'border-bottom: 2px solid #409EFF;':''">器材管理</el-menu-item>
      </el-menu>
    </div>
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
  font-size: 20px;
}
.nav{
  font: normal 24px/45px "microsoft YaHei";
}
.top{
  position: relative;
  margin-left: 24%;
  margin-top: 5px;
  .text{
    position: absolute;
    left: 9%;
    top:35%;
  }
}
</style>