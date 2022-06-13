<template>
<!-- 修改密码 -->
    <div style="height:800px">
        <div class="form">
            <h2>修改密码</h2>
            <el-form>
                <el-form-item label="用户名：" :label-width="formLabelWidth">
                    <el-input v-model.trim="username" autocomplete="off" disabled></el-input>
                </el-form-item>
                <el-form-item label="原密码：" :label-width="formLabelWidth">
                    <el-input type="password" v-model.trim="prepassword" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="新密码：" :label-width="formLabelWidth">
                    <el-input type="password" v-model.trim="password" autocomplete="off"></el-input>
                </el-form-item>
                <div class="button">
                    <el-button @click="clear">清空</el-button>
                    <el-button type="primary" @click="updatePassword">确 定</el-button>
                </div>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { UpdatePassword } from "@/request/api";
export default {
    data () {
        return {
            username:"",
            password:"",
            prepassword:"",
            formLabelWidth: '150px',
 
        }
    },
    created(){
        this.username = localStorage.getItem("username")
    },
    methods:{
        updatePassword(){
            if(this.username==""||this.password==""||this.prepassword==""){
                this.$message.warning("用户名、原密码或者新密码 为空")
                this.clear()
            }else{
                UpdatePassword({
                   username:this.username,
                   pre:this.prepassword,
                   new:this.password
                }).then(res=>{
                    console.log(res);
                    if(res.code=="200"){
                        if(res.msg=="修改成功"){
                            this.$message.success(res.msg)
                        }else{
                            this.$message.warning(res.msg)
                        }
                    }else{
                        this.$message.error(res.msg)
                    }
                    this.clear()
                }).catch(err=>{
                    // console.log("err:",err.response.data);
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
            }
        },
        clear(){
            this.prepassword=""
            this.password=""
        },
    }
}
</script>
 
<style lang = "less" scoped>
    .form{
        width: 450px;
        margin: 50px 300px;
        h2{
            text-align: center;
            margin-bottom: 50px;
            margin-left: 80px;
        }
        .button{
            padding-left: 50px;
            text-align: center;
            .el-button{
                margin: 0 40px;
            }
        }
    }
</style>