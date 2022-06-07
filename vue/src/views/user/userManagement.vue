<template>
<div style="height:870px">
  <div class="top">
    <div class="top_top">
      <div class="userManager" v-show="roleId==2||roleId==1?isShowUserManager=true:isShowUserManager=false">
        <span class="top_text" @click="typeManage"
              :class="appointmentShow?'active':''">导入信息</span>
        <span class="top_text" @click="notice"
              :class="noticeShow&&$route.path=='/home/siteManagement/noticeManagement'?'active':''">查询用户个人信息</span>
        <span class="top_text" @click="typeManage"
              :class="typeShow&&$route.path=='/home/siteManagement/siteTypeManagement'?'active':''">修改密码(强制)</span>
      </div>
      <div class="manager" v-show="roleId<6?isShowManager=true:isShowManager=false">
          <span class="top_text" @click="createAnnouncement"
                :class="$route.path=='/home/userManagement/createAnnouncement'?'active':''">创建公告</span>
          <span class="top_text" @click="updateAnnouncement"
                :class="$route.path=='/home/userManagement/updateAnnouncement'?'active':''">修改公告</span>
          <span class="top_text" @click="queryAnnLogs"
                :class="$route.path=='/home/userManagement/queryAnnLogs'?'active':''">查询公告历史记录</span>
          <span class="top_text" @click="notice"
                :class="noticeShow&&$route.path=='/home/siteManagement/noticeManagement'?'active':''">查询管理员用户</span>
        </div>
    </div>
    <div class="top_bottom">
      <div class="superManager" v-show="roleId==1?isShowSuperManager=true:isShowSuperManager=false">
        <span class="top_text" @click="addManager"
              :class="$route.path=='/home/userManagement/addManager'?'active':''">添加管理员</span>
        <span class="top_text" @click="delManager"
              :class="$route.path=='/home/userManagement/delManager'?'active':''">删除管理员</span>
        <span class="top_text" @click="updateManager"
              :class="$route.path=='/home/userManagement/updateManager'?'active':''">更新管理员</span>
        <span class="top_text" @click="updateAnnLogs"
              :class="$route.path=='/home/userManagement/updateAnnLogs'?'active':''">修改公告历史记录</span>
      </div>
      <div class="user" v-show="roleId<8?isShowUser=true:isShowUser=false">
        <span class="top_text" @click="updatePassword"
              :class="$route.path=='/home/userManagement/updatePassword'?'active':''">修改密码</span>
        <span class="top_text" @click="appointment"
              :class="appointmentShow&&$route.path=='/home/siteManagement/appointmentManagement'?'active':''">查询管理员信息</span>
        <span class="top_text" @click="queryNewAnn"
              :class="$route.path=='/home/userManagement/queryNewAnn'?'active':''">查询最新公告</span>
      </div>
    </div>
  </div>
  <router-view></router-view>
</div>
</template>

<script>

export default {
  name: "userManagement",
  data(){
    return{
      roleId:20,     // 角色权限id
      isShowUserManager:false,
      isShowManager:false,
      isShowSuperManager:false,
      isShowUser:false,   
      
      typeShow:false,
      appointmentShow:false,
      noticeShow:false,
    }
  },
  created(){
    this.roleId = this.$store.state.roleId
  },
  methods:{
    addManager(){
      this.$router.push({path:"/home/userManagement/addManager"})
    },
    delManager(){
      this.$router.push({path:"/home/userManagement/delManager"})
    },
    updateManager(){
      this.$router.push({path:"/home/userManagement/updateManager"})
    },
    createAnnouncement(){
      this.$router.push({path:"/home/userManagement/createAnnouncement"})
    },
    updateAnnouncement(){
      this.$router.push({path:"/home/userManagement/updateAnnouncement"})
    },
    queryAnnLogs(){
      this.$router.push({path:"/home/userManagement/queryAnnLogs"})
    },
    updateAnnLogs(){
      this.$router.push({path:"/home/userManagement/updateAnnLogs"})
    },
    queryNewAnn(){
      this.$router.push({path:"/home/userManagement/queryNewAnn"})
    },
    updatePassword(){
      this.$router.push({path:"/home/userManagement/updatePassword"})
    },
    typeManage(){
      
    },
    appointment(){
      
    },
    notice(){
      
    },
  }
}
</script>

<style scoped lang="less">
.top{
  width:100%;
  .top_top,.top_bottom{
    display: flex;
    border-bottom: 1px solid #dcd8d8;
  }
  .top_text{
    cursor: pointer;
    margin-left: 40px;
    height: 40px;
    line-height: 40px;
    padding: 10px;
  }
  .active{
    color:deepskyblue;
    border-bottom: 1px solid deepskyblue;
  }
}
/deep/ *{
  overflow: hidden;
}
.el-tabs{
  i{
    margin-left: 7%;
  }
}
.el-tabs__item{
  height: 500px;
}
/deep/.el-tabs__nav-scroll {
  overflow: hidden;
  height: 45px;
  font-size: 18px;
  margin-left: 35%;
}
</style>