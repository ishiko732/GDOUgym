<template>
<div>
  <div class="top">
    <div class="top_top">
      <div class="userManager">
        <span class="top_text" @click="importInfo" v-show="roleId==2||roleId==1?isShowUserManager=true:isShowUserManager=false"
              :class="$route.path=='/home/userManagement/importInfo'?'active':''">导入信息</span>
        <span class="top_text" @click="queryUserInfo" v-show="roleId==2||roleId==1?isShowUserManager=true:isShowUserManager=false"
              :class="$route.path=='/home/userManagement/queryUserInfo'?'active':''">查询用户个人信息</span>
        <span class="top_text" @click="updatePwdForce" v-show="roleId==2||roleId==1?isShowUserManager=true:isShowUserManager=false"
              :class="$route.path=='/home/userManagement/updatePwdForce'?'active':''">修改密码(强制)</span>
        <span class="top_text" @click="addManager" v-show="roleId==1?isShowSuperManager=true:isShowSuperManager=false"
              :class="$route.path=='/home/userManagement/addManager'?'active':''">添加管理员</span>
        <span class="top_text" @click="updatePassword" v-show="roleId<8?isShowUser=true:isShowUser=false"
              :class="$route.path=='/home/userManagement/updatePassword'?'active':''">修改密码</span>
        <span class="top_text" @click="queryManagerInfo" v-show="roleId<8?isShowUser=true:isShowUser=false"
              :class="$route.path=='/home/userManagement/queryManagerInfo'?'active':''">查询管理员信息</span>
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
      
    }
  },
  created(){
    this.roleId = localStorage.getItem("roleId")
  },
  methods:{
    addManager(){
      this.$router.push({path:"/home/userManagement/addManager"})
    },
    updatePassword(){
      this.$router.push({path:"/home/userManagement/updatePassword"})
    },
    importInfo(){
      this.$router.push({path:"/home/userManagement/importInfo"})
    },
    queryUserInfo(){
      this.$router.push({path:"/home/userManagement/queryUserInfo"})
    },
    updatePwdForce(){
      this.$router.push({path:"/home/userManagement/updatePwdForce"})
    },
    queryManagerInfo(){
      this.$router.push({path:"/home/userManagement/queryManagerInfo"})
    },
    queryManager(){
      this.$router.push({path:"/home/userManagement/queryManager"})
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