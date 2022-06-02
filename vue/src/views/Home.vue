<template>
  <div>
    <div class="nav">
      <el-menu class="el-menu-demo" mode="horizontal">
          <el-menu-item index="1" style="margin-left: 38%" @click="userManagement">用户管理</el-menu-item>
          <el-menu-item index="2" @click="siteManagement">场地管理</el-menu-item>
          <el-menu-item index="3" @click="eventManagement">赛事管理</el-menu-item>
          <el-menu-item index="4" @click="equipmentManagement">器材管理</el-menu-item>
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
  }
}
</script>
<style lang="less" scoped>
.el-menu-item{
  font-size: 20px;
}
</style>