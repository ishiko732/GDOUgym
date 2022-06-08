<template>
<!-- 查询管理员信息（普通用户可查） -->
    <div style="height:800px">
        <div class="form">
            <h2>查询管理员信息</h2>
            <el-form>
                <p><b>ps：用户名为空查询，即查询所有管理员</b></p>
                <el-form-item label="用户名：" :label-width="formLabelWidth">
                    <el-input v-model.trim="username" autocomplete="off" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <div class="button">
                    <el-button type="primary" @click="query">查 询</el-button>
                </div>
                    
                <el-form-item label="管理员信息：" :label-width="formLabelWidth">
                    <el-table
                        :data="ManagerInfoList"
                        height="300"
                        border
                        style="width: 100%">
                        <el-table-column
                        prop="id"
                        label="id"
                        width="150">
                        </el-table-column>
                        <el-table-column
                        prop="name"
                        label="用户名">
                        </el-table-column>
                        <el-table-column
                        prop="truename"
                        label="姓名">
                        </el-table-column>
                    </el-table>
                </el-form-item>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { QueryManagerInfo } from "@/request/api";
export default {
    data () {
        return {
            username:"",
            ManagerInfoList:[],
            formLabelWidth: '100px',
 
        }
    },
    methods:{
        query(){
            this.ManagerInfoList=[]    //清空数组
            if(this.username!=""){
                QueryManagerInfo({username:this.username}).then(res=>{
                    console.log(res);
                    if(res.code=="200"){
                        if(res.msg=="查询结果为空"){
                            this.$message.warning(res.msg)
                        }else{
                            this.ManagerInfoList = res.data 
                        }
                    }
                }).catch(err=>{
                    // console.log("err:",err.response.data);
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
            }else{
                QueryManagerInfo().then(res=>{
                    console.log(res);
                    this.ManagerInfoList = res.data
                }).catch(err=>{
                    // console.log("err:",err.response.data);
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
            }
        },
        
    },created(){
        this.query()
    }
}
</script>
 
<style lang = "less" scoped>
    .form{
        width: 800px;
        margin: 50px 300px;
        h2{
            text-align: center;
            margin-bottom: 50px;
            margin-left: 60px;
        }
        p{
            margin-left: 30px;
            color:red;
            padding-bottom: 20px;
        }
        .button{
            margin-left: 150px;
            margin-bottom: 20px;
        }
        .el-button--primary{
            margin-right:10px;
        }
        /deep/.el-input__inner,.el-input{
            width: 300px;
        }
        
    
    }
</style>