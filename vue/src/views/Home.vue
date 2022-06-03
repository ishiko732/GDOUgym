<template>
  <div class="home_container">
    <div class="home_top">
      <el-menu
          class="el-menu-demo"
          mode="horizontal"
          background-color="#0099CC"
          text-color="#fff"
          active-text-color="#ffd04b">
        <el-menu-item index="1"></el-menu-item>
      </el-menu>
    </div>
    <div style="display: flex">
      <div class="home_nav">
        <el-row>
          <el-col>
            <el-menu
                class="el-menu-vertical-demo"
                background-color="#545c64"
                text-color="#fff"
                active-text-color="#ffd04b"
                unique-opened>
              <el-menu-item index="1">
                <i class="el-icon-setting"></i>
                <span slot="title" @click="userManagement">用户管理</span>
              </el-menu-item>
              <el-menu-item index="2">
                <i class="el-icon-setting"></i>
                <span slot="title" @click="siteManagement">场地管理</span>
              </el-menu-item>
              <el-menu-item index="3">
                <i class="el-icon-setting"></i>
                <span slot="title" @click="eventManagement">赛事管理</span>
              </el-menu-item>
              <el-menu-item index="4">
                <i class="el-icon-setting"></i>
                <span slot="title" @click="equipmentManagement">器材管理</span>
              </el-menu-item>
            </el-menu>
          </el-col>
        </el-row>
      </div>
      <div class="router">
        <router-view></router-view>
      </div>
    </div>
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
<style lang="less">
.home_container{
  overflow-y:auto;
  overflow-x:hidden;
  .el-menu.el-menu--horizontal {
    border-bottom: none;
  }
  .home_nav{
    width: 248px;
    flex: 0 0 auto;
    background-color: rgba(84,92,100);
    .el-menu{
    }
  }
  .router{
    flex-grow: 1;
  }
}
</style>
