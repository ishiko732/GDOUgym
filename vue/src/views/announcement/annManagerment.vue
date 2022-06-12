<template>
<div>
  <div class="top">
    <div class="top_top">
      <div class="manager" >
          <span class="top_text" @click="updateAnnLogs" v-show="roleId==1?true:false"
                :class="$route.path=='/home/annManagement/updateAnnLogs'?'active':''">修改公告历史记录</span>
          <span class="top_text" @click="createAnnouncement" v-show="roleId<6?true:false"
                :class="$route.path=='/home/annManagement/createAnnouncement'?'active':''">创建公告</span>
          <span class="top_text" @click="updateAnnouncement" v-show="roleId<6?true:false"
                :class="$route.path=='/home/annManagement/updateAnnouncement'?'active':''">修改公告</span>
          <span class="top_text" @click="queryAnnLogs" v-show="roleId<6?true:false"
                :class="$route.path=='/home/annManagement/queryAnnLogs'?'active':''">查询公告历史记录</span>
          <span class="top_text" @click="queryNewAnn" v-show="roleId<8?true:false"
                :class="$route.path=='/home/annManagement/queryNewAnn'?'active':''">查询最新公告</span>
        </div>
    </div>
  </div>
  <router-view></router-view>
</div>
</template>

<script>

export default {
  name: "annManagement",
  data(){
    return{
      roleId:20,     // 角色权限id
    }
  },
  created(){
    this.roleId = localStorage.getItem("roleId")
  },
  methods:{
    createAnnouncement(){
      this.$router.push({path:"/home/annManagement/createAnnouncement"})
    },
    updateAnnouncement(){
      this.$router.push({path:"/home/annManagement/updateAnnouncement"})
    },
    queryAnnLogs(){
      this.$router.push({path:"/home/annManagement/queryAnnLogs"})
    },
    updateAnnLogs(){
      this.$router.push({path:"/home/annManagement/updateAnnLogs"})
    },
    queryNewAnn(){
      this.$router.push({path:"/home/annManagement/queryNewAnn"})
    },
  }
}
</script>

<style scoped lang="less">
.top{
  width:100%;
  .top_top{
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