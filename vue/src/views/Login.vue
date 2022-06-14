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
        <el-input v-model.trim="username" placeholder="请输入用户名" @keyup.enter.native="login"></el-input>
      </div>
      <div class="password_div">
        <span class="password">密码&nbsp;&nbsp;&nbsp;</span>
        <el-input type="password" v-model.trim="password" placeholder="请输入密码" @keyup.enter.native="login"></el-input>
      </div>
      <Vcode :show="isShow" @success="success" @close="close" ></Vcode>
      <el-button type="primary" @click="login">登录</el-button>
      <el-button type="primary" @click="showRegister">注册</el-button>
      <!-- 注册模态窗口 -->
      <el-dialog title="注册" :visible.sync="isShowRegister" center>
        <el-form>
          <el-form-item label="学号：" :label-width="formLabelWidth">
            <el-input v-model.trim="id" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="用户名：" :label-width="formLabelWidth">
            <el-input v-model.trim="username_register" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码：" :label-width="formLabelWidth">
            <el-input type="password" v-model.trim="password_register" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="角色：" :label-width="formLabelWidth">
            <el-select v-model="role" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="isShowRegister = false">取 消</el-button>
          <el-button type="primary" @click="register">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</div>
</template>

<script>
import Vcode from "vue-puzzle-vcode"
import { Login,Register,SelLoginUserInfo } from "@/request/api";

export default {
  name: "Login",
  data(){
    return{
      id:"",
      role:"student",
      username:"",
      password:"",
      username_register:"",
      password_register:"",
      isShow:false,
      isShowRegister:false,
      formLabelWidth: '120px',
      options: [{
        value: 'student',
        label: '学生'
      }, {
        value: 'teacher',
        label: '老师'
      }],

    }
  },
  methods: {
    login() {
      if(this.username==""||this.password==""){
        this.$message.warning("用户名或密码为空")
      }else{
        this.isShow = true;
      }
    },
    // 用户通过了验证
    success(msg) {
      this.isShow = false;
      localStorage.removeItem("token")
      Login({
        username: this.username,
        password: this.password,
      })
      .then(res=>{
        this.username=""
        this.password=""
        // console.log(res);
        if(res.code == 200){
          if(res.msg == "Login success"){
            localStorage.setItem("token",res.data)
            this.$message.success("登录成功")
            setTimeout(()=>{
              this.$router.push({path: '/home'})
            },500)

            SelLoginUserInfo().then(res=>{
              localStorage.setItem("roleId",res.data.roleId)
              localStorage.setItem("username",res.data.name)
            })
          }else if(res.msg == "Login failed"){
            this.$message.error("密码错误")
          }else if(res.msg == "用户未注册"){
            this.$message.warning("用户名不存在")
          }
          
        }
      })
     
    },
    // 用户点击遮罩层，应该关闭模态框
    close(){
      this.isShow = false;
    },
    // 注册模块
    showRegister(){
      this.isShowRegister = true
    },
    register(){
      if(this.username_register == "" || this.password_register == ""){
        this.$message.warning("用户名或密码为空")
        this.username_register=""
        this.password_register=""
        this.id=""
      }else{
        Register({
          username:this.username_register,
          password:this.password_register,
          id:this.id,
          role:this.role
        })
        .then(res=>{
          this.username_register=""
          this.password_register=""
          this.id=""
          // console.log(res);
          this.$message(res.msg)
          
        })
      }
      
    }


  },
  components:{
    Vcode
  }
}
</script>

<style scoped lang="less">
/deep/ *{
  overflow: hidden;
}

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
  width: 45%;
}
}
}

}

</style>