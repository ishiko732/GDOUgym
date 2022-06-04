<template>
<div class="container">
  <div class="top">
    <img src="https://jw.gdou.edu.cn/zftal-ui-v5-1.0.2/assets/images/logo/logo_jw_d.png?t=1654220855681" class="topImg">
    <span class="text">广东海洋大学体育馆管理系统</span>
  </div>
  <div class="body">
    <img src="https://jw.gdou.edu.cn/xtgl/dlycssz_cxDlycsszZp.html?zplx=3&t=1654220855681" class="bodyImg">
    <div class="username_password">

        <div class="username_div">
          <span class="username">用户名</span>
          <el-input v-model="username" placeholder="请输入用户名"></el-input>
        </div>
        <div class="password_div">
          <span class="password">密码&nbsp;&nbsp;&nbsp;</span>
          <el-input v-model="password" placeholder="请输入密码"></el-input>

      </div>
      <Vcode :show="isShow" @success="success" ></Vcode>
      <el-button type="primary" @click="change">登录</el-button>
    </div>
  </div>
</div>
</template>

<script>
import Vcode from "vue-puzzle-vcode"
export default {
  name: "Login",
  data(){
    return{
      username:"",
      password:"",
      isShow:false,
      isLogin:false,
    }
  },
  methods: {
    change() {
      this.isShow = true;
    },
    // 用户通过了验证
    success(msg) {
      this.isShow = false;
      this.isLogin=true
      if(this.isLogin){
        this.$router.push({path: '/home'})
      }
    },

  },
  components:{
    Vcode
  }
}
</script>

<style scoped lang="less">

.container{
  margin: 0 auto;
  width: 80%;
  .top{
    margin-top: 20px;
    display: flex;
    .text{
      margin-left: 10px;
      font: normal 24px/45px "microsoft YaHei";
      color: #1069a4;
    }
  }
  .body{
    margin-top: 20px;
    display: flex;
    .bodyImg{
      width: 60%;
      flex: 3;
    }
    .username_password{
      margin-left: 30px;
      flex: 1;
      .el-input{
        width: 70%;
        margin-left: 60px;
      }
      .username{
        position: absolute;
        top:95px;
      }
      .password{
        position: absolute;
        top:190px;
      }
      .password_div,.username_div{
        margin-bottom: 50px;
      }
      .el-button{
        margin-left: 10px;
        width: 90%;
      }
    }
  }

}
</style>