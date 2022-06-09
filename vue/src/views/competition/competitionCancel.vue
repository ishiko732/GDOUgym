<template>
<!-- 赛事取消 -->
    <div style="height:800px">
        <div class="form">
            <h2>取消赛事</h2>
            <el-form>
                <el-form-item label="赛事id：" :label-width="formLabelWidth">
                    <el-input v-model.trim="cid" autocomplete="off" placeholder="请输入赛事id"></el-input>
                </el-form-item>
                <el-form-item label="取消原因：" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model.trim="context" autocomplete="off"></el-input>
                </el-form-item>
                
                <div class="button">
                    <el-button @click="clear">清空</el-button>
                    <el-button type="primary" @click="cancel">确 定</el-button>
                </div>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { CancelEvent } from "@/request/api";
export default {
    data () {
        return {
            formLabelWidth: '150px',
            cid:"",
            context:""
        }
    },
    methods:{
        cancel(){
            if(this.cid==""||this.context==""){
                this.$message.warning("赛事id或者取消原因为空")
            }else{
                CancelEvent({
                    cid:this.cid,
                    context:this.context
                }).then(res=>{
                    console.log(res);
                    if(res.msg=="取消成功"){
                        this.$message.success(res.msg)
                        this.clear()
                    }else if(res.msg=="取消失败"){
                        this.$message.warning(res.msg+"，该赛事已经取消")
                    }else{
                        this.$message.warning(res.msg)
                    }
                }).catch(err=>{
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
            }
        },
        clear(){
            this.cid=""
            this.context=""
        }
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
            margin-left: 80px;
        }
        .button{
            padding-left: 50px;
            text-align: center;
            .el-button{
                margin: 0 40px;
            }
        }
        /deep/.el-textarea__inner{
            width: 600px;
            height: 300px;
            font-size: 20px;
        }
        /deep/.el-form-item__label{
            width: 150px;
        }
    }
</style>