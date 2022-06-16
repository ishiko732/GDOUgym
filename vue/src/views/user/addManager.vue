<template>
    <div style="height:800px">
        <div class="form">
            <h2>添加管理员</h2>
            <el-form>
                <el-form-item label="用户名：" :label-width="formLabelWidth">
                    <el-input v-model.trim="username" autocomplete="off" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="密码：" :label-width="formLabelWidth">
                    <el-input type="password" v-model.trim="password" autocomplete="off" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item label="自定义号码：" :label-width="formLabelWidth">
                    <el-input v-model.trim="id" autocomplete="off" placeholder="请输入学工号"></el-input>
                </el-form-item>
                <el-form-item label="姓名：" :label-width="formLabelWidth">
                    <el-input v-model.trim="truename" autocomplete="off" placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item label="权限：" :label-width="formLabelWidth">
                    <el-select v-model="role" placeholder="请选择">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                    </el-select>
                </el-form-item>
                <div class="button">
                    <el-button @click="clear">重置</el-button>
                    <el-button type="primary" @click="addManager">确 定</el-button>
                </div>
                
            </el-form>
        </div>
        
    </div>
</template>

<script>
import { AddManager,ExportUser,QueryUid } from "@/request/api";
export default {
    data () {
        return {
            oneMap:"",
            username:"",
            password:"",
            id:"",
            uid:"",
            truename:"",
            role:"",
            formLabelWidth: '150px',
            options: [{
                value: 'UM',
                label: '用户管理员'
            },{
                value: 'PM',
                label: '场地管理员'
            },{
                value: 'AM',
                label: '赛事管理员'
            },{
                value: 'EM',
                label: '器材管理员'
            }],
 
        }
    },
    methods:{
        addManager(){
            if(this.username==""||this.password==""){
                this.$message.warning("用户名或者密码为空")
                this.clear()
            }else if(this.id==""||this.truename==""){
                this.$message.warning("自定义号码或姓名为空")
            }else if(this.role == ""){
                this.$message.warning("未选择管理员权限")
                this.clear()
            }else{
                AddManager({
                    username:this.username,
                    password:this.password,
                    role:this.role
                })
                .then(res=>{
                    // console.log(res);
                    if(res.code=="200"){
                        this.$message.success(res.msg)
                        let username = this.username
                        let id = this.id
                        let truename = this.truename
                        QueryUid({username:this.username}).then(res=>{
                            // console.log("QueryUid:",res.data);
                            this.uid=res.data.id
                            // console.log(this.uid);
                            this.oneMap='{"id":"'+id+'","uid":"'+this.uid+'","uname":"'+username+'","truename":"'+truename+'"}'
                            ExportUser({map:this.oneMap}).then(res1=>{
                                // console.log("导入信息：",res1);
                                if(res1.msg=="导入信息"){
                                    this.$message.success(res1.msg)
                                }else{
                                    this.$message.warning(res1.msg)
                                }
                            }).catch(err1=>{
                                this.$message.error(err1.response.data.data+"，请重新登录")
                            })
                        })
                    }else if(res.code=="401"){
                        this.$message.error(res.msg)
                    }else{
                        this.$message.warning("发生未知错误")
                    }
                    
                    this.clear()
                    this.oneMap=""
                })
                .catch(err=>{
                    // console.log("err:",err.response.data);
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
            }
            
        },
        clear(){
            this.username = ""
            this.password = ""
            this.id = ""
            this.uid = ""
            this.truename = ""
            this.role = ""
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