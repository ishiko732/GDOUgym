<template>
    <div style="height:800px">
        <div class="form">
            <h2>添加管理员</h2>
            <el-form>
                <el-form-item label="用户名：" :label-width="formLabelWidth">
                    <el-input v-model.trim="username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码：" :label-width="formLabelWidth">
                    <el-input type="password" v-model.trim="password" autocomplete="off"></el-input>
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
import { AddManager } from "@/request/api";
export default {
    data () {
        return {
            username:"",
            password:"",
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
                    console.log(res);
                    if(res.code=="200"){
                        this.$message.success(res.msg)
                    }else if(res.code=="401"){
                        this.$message.error(res.msg)
                    }else{
                        this.$message.warning("发生未知错误")
                    }
                    
                    this.clear()
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