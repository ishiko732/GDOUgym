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
              <el-menu-item index="1" @click="userManagement">
                <i class="el-icon-setting"></i>
                <span slot="title">用户管理</span>
              </el-menu-item>
              <el-menu-item index="2" @click="siteManagement">
                <i class="el-icon-setting"></i>
                <span slot="title">场地管理</span>
              </el-menu-item>
              <el-menu-item index="3" @click="eventManagement">
                <i class="el-icon-setting"></i>
                <span slot="title">赛事管理</span>
              </el-menu-item>
              <el-menu-item index="4" @click="equipmentManagement">
                <i class="el-icon-setting"></i>
                <span slot="title">器材管理</span>
              </el-menu-item>
            </el-menu>
          </el-col>
        </el-row>
      </div>
      <div class="router">
        <router-view style="width: 1660px;"></router-view>
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
<style lang="less" scoped>
/deep/ *{
  overflow: hidden;
}
.home_container {
  .el-menu.el-menu--horizontal {
    border-bottom: none;
  }
  .home_nav {
    width: 247px;
    flex: 0 0 auto;
    background-color: rgba(84, 92, 100);
    .el-menu {
      border: none;
      border-top:1px solid rgba(84, 92, 100);
      .el-menu-item {
        width: 247px;
        overflow: hidden;
      }
    }

    .router {
      flex-grow: 1;
    }
  }
}
</style>
