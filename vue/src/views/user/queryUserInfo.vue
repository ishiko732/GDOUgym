<template>
<!-- 查询用户个人信息 -->
    <div style="height:800px">
        <div class="form">
            <h2>查询用户个人信息</h2>
            <el-form>
                <p><b>ps：二选一填写即可 (两个都填写，则以用户id查询)</b></p>
                <el-form-item label="用户id：" :label-width="formLabelWidth">
                    <el-input v-model.trim="uid" autocomplete="off" placeholder="请输入用户id ( 不是学工号 )"></el-input>
                </el-form-item>
                <el-form-item label="学工号：" :label-width="formLabelWidth">
                    <el-input v-model.trim="id" autocomplete="off" placeholder="请输入学工号 ( 不是用户id )"></el-input>
                </el-form-item>
                <div class="button">
                    <el-button type="primary" @click="query">查 询</el-button>
                    <el-button type="primary" @click="queryAll">查询全部用户</el-button>
                </div>
                    
                <el-form-item label="用户信息：" :label-width="formLabelWidth">
                    <el-table
                        :data="UserInfoList"
                        height="300"
                        border
                        style="width: 100%">
                        <el-table-column
                        prop="uid"
                        label="id"
                        width="50">
                        </el-table-column>
                        <el-table-column
                        prop="truename"
                        label="姓名"
                        width="100">
                        </el-table-column>
                        <el-table-column
                        prop="uname"
                        label="用户名"
                        width="180">
                        </el-table-column>
                        <el-table-column
                        prop="id"
                        label="学工号">
                        </el-table-column>
                        <el-table-column
                        prop="class"
                        label="班级">
                        </el-table-column>
                    </el-table>
                </el-form-item>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { QueryUserInfoById,QueryUserInfoByUid,QueryUsers } from "@/request/api";
export default {
    data () {
        return {
            uid:"",
            id:"",
            UserInfoList:[],
            formLabelWidth: '100px',
        }
    },
    methods:{
        query(){
            this.UserInfoList=[]    //清空数组
            if(this.uid!=""){
                QueryUserInfoByUid({ID:this.uid}).then(res=>{
                    // console.log(res);
                    if(res.code=="200"){
                        if(res.msg=="未获取到用户信息"){
                            this.$message.warning(res.msg)
                        }else{
                            let { id,truename,uid,uname } = res.data
                            this.UserInfoList.push({
                                class:res.data.class,
                                uid,
                                id,
                                truename,
                                uname
                            })
                        }
                    }
                }).catch(err=>{
                    // console.log("err:",err.response.data);
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
            }else if(this.id!=""){
                QueryUserInfoById({ID:this.id}).then(res=>{
                    // console.log(res);
                    if(res.code=="200"){
                        if(res.msg=="未获取到用户信息"){
                            this.$message.warning(res.msg)
                        }else{
                            let { id,truename,uid,uname } = res.data
                            this.UserInfoList.push({
                                class:res.data.class,
                                uid,
                                id,
                                truename,
                                uname
                            })
                        }
                    }
                }).catch(err=>{
                    // console.log("err:",err.response.data);
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
            }else{
                this.$message.warning("用户id和学工号都为空，请输入后再查询")
            }
        },
        queryAll(){
            QueryUsers({
                current:1,      // 当前页数
                size:1000,      // 每页最多显示条数
                cnt:0
            }).then(res=>{
                // console.log(res);
                this.UserInfoList = res.data.infos
                // console.log(this.UserInfoList);
                
            })
        }
    },created(){
        this.queryAll()
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