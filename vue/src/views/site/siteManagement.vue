<template>
  <div style="height: 1000px">
    <div class="siteContainer">
      <div class="top">
        <div style="margin-left: 30%">
          <span class="top_text" @click="typeManage" v-show="roleId==1||roleId==3?true:false"
            :class="typeShow&&$route.path=='/home/siteManagement/siteTypeManagement'?'active':''">场地类型管理</span>
          <span class="top_text" @click="appointment" v-show="roleId==1||roleId==3?true:false"
            :class="appointmentShow&&$route.path=='/home/siteManagement/appointmentManagement'?'active':''">预约审核</span>
          <span class="top_text" @click="pay"
            :class="payShow&&$route.path=='/home/siteManagement/appointPay'?'active':''">预约付费</span>
          <span class="top_text" @click="siteappointment"
            :class="siteAppointment&&$route.path=='/home/siteManagement/siteAppointment'?'active':''">场地预约</span>
          <span class="top_text" @click="notice"
            :class="noticeShow&&$route.path=='/home/siteManagement/noticeManagement'?'active':''">公告</span>
        </div>
      </div>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  data() {
    return {
      roleId:"",
      typeShow:false,
      appointmentShow:false,
      noticeShow:false,
      siteAppointment:false,
      payShow:false,
    };
  },
  created () { 
    this.roleId = localStorage.getItem("roleId")
    console.log(this.roleId);
  },
  methods: {
    typeManage(){
      this.typeShow=true
      this.appointmentShow=false
      this.noticeShow=false
      this.siteAppointment=false
      this.$router.push({path:'/home/siteManagement/siteTypeManagement'})
    },
    appointment(){
      this.typeShow=false
      this.appointmentShow=true
      this.noticeShow=false
      this.siteAppointment=false
      this.$router.push({path:'/home/siteManagement/appointmentManagement'})
    },
    notice(){
      this.typeShow=false
      this.appointmentShow=false
      this.siteAppointment=false
      this.noticeShow=true
      this.$router.push({path:'/home/siteManagement/noticeManagement'})
    },
    siteappointment(){
      this.typeShow=false
      this.appointmentShow=false
      this.noticeShow=false
      this.siteAppointment=true
      this.$router.push({path:'/home/siteManagement/siteAppointment'})
    },
    pay(){
      this.typeShow=false
      this.appointmentShow=false
      this.noticeShow=false
      this.siteAppointment=false
      this.payShow=true
      this.$router.push({path:'/home/siteManagement/appointPay'})
    },
  }
}
</script>

<style scoped lang="less">
.top{
  width:100%;
  border-bottom: 1px solid #dcd8d8;
  height: 50px;
  .top_text{
    cursor: pointer;
    margin-left: 5%;
    height: 50px;
    line-height: 50px;
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