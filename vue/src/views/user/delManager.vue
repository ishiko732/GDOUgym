<template>
    <div style="height:800px">
        <div class="form">
            <h2>删除管理员</h2>
            <el-form>
                <el-form-item label="用户id：" :label-width="formLabelWidth">
                    <el-input v-model.trim="id" autocomplete="off" placeholder="请输入用户id ( 不是学工号 )"></el-input>
                </el-form-item>
                <div class="button">
                    <el-button @click="clear">清 空</el-button>
                    <el-button type="primary" @click="delManager">确 定</el-button>
                </div>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { DelManager } from "@/request/api";
export default {
    data () {
        return {
            id:"",
            formLabelWidth: '150px',
 
        }
    },
    methods:{
        delManager(){
            if(this.id==""){
                this.$message.warning("用户id为空")
            }else{
                DelManager({ID:this.id}).then(res=>{
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
            this.id=""
        }
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