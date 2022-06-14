<template>
  <div class="home_container">
    <div class="home_top">
      <nav class="nav">
        <div class="left">
          <a href="#">
            <img src="https://jw.gdou.edu.cn/zftal-ui-v5-1.0.2/assets/images/logo/logo_jw_w.png">
            <span>广东海洋大学体育馆管理系统</span>
          </a>
        </div>
        <div class="right">
          <span>用户名：{{username}}</span>
          <a href="#" @click="logout">注销</a>
        </div>
      </nav>
    </div>
    <div style="display: flex;height:5000px">
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
              <el-menu-item index="3" @click="comManagement">
                <i class="el-icon-setting"></i>
                <span slot="title">赛事管理</span>
              </el-menu-item>
              <el-menu-item index="4" @click="equipmentManagement">
                <i class="el-icon-setting"></i>
                <span slot="title">器材管理</span>
              </el-menu-item>
              <el-menu-item index="5" @click="annManagement">
                <i class="el-icon-setting"></i>
                <span slot="title">公告管理</span>
              </el-menu-item>
            </el-menu>
          </el-col>
        </el-row>
      </div>
      <div class="router">
        <router-view style="width: 1660px;height:auto"></router-view>
      </div>
    </div>
  </div>
</template>

<script>

import { Logout } from "@/request/api";

export default {
  name: 'Home',
  data(){
    return{
      username:"",
    }
  },
  created(){
    this.username = localStorage.getItem("username")
  },
  methods:{
    equipmentManagement(){
      this.$router.push({path:'/home/equipmentManagement'})
    },
    userManagement(){
      this.$router.push({path:'/home/userManagement/updatePassword'})
    },
    siteManagement(){
      this.$router.push({path:'/home/siteManagement'})
    },
    comManagement(){
      this.$router.push({path:'/home/comManagement/competitionQuery'})
    },
    annManagement(){
      this.$router.push({path:'/home/annManagement/queryNewAnn'})
    },
    logout(){
      Logout().then(res=>{
        // console.log(res);
        this.$message.success(res.msg)
        localStorage.clear()
        this.$router.push({path:"/login"})
      }).catch(err=>{
        // console.log("err:",err.response.data);
        this.$message.error("登录已失效")
        localStorage.clear()
        this.$router.push({path:"/login"})
      })
    }
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
      height: 1000px;
    }
  }
  .nav{
    padding: 0 30px;
    background-color: #0099CC;
    color:#fff;
    height: 60px;
    display: flex;
    justify-content: space-between;
    .left a{
      display: flex;
      height: 60px;
      line-height: 60px;
      text-decoration: none;
      color: white;
      font-size: 25px;
      img{
        width: 60px;
      }
    }
    .right{
      font-size: 20px;
      span{
        margin-right: 20px;
      }
      a{
        line-height: 60px;
        text-decoration: none;
        color: white;
        font-size: 15px;
      }
    }
  }
}
</style>
