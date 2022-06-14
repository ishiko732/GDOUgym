<template>
<!-- 赛事创建 -->
    <div style="height:800px">
        <div class="form">
            <h2>创建赛事</h2>
            <el-form>
                <el-form-item label="赛事名称：" :label-width="formLabelWidth">
                    <el-input v-model.trim="name" autocomplete="off" placeholder="请输入赛事名称"></el-input>
                </el-form-item>
                <el-form-item label="赛事时间：" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="time"
                        type="datetime"
                        placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="赛事时长(单位:分)：" :label-width="formLabelWidth">
                    <el-input v-model.trim="time_length" autocomplete="off" placeholder="请输入赛事时长(大于10分钟)"></el-input>
                </el-form-item>
                <el-form-item label="赛事内容：" :label-width="formLabelWidth">
                    <el-input v-model.trim="context" autocomplete="off" placeholder="请输入赛事内容"></el-input>
                </el-form-item>
                <div class="button">
                    <el-button @click="clear">清 空</el-button>
                    <el-button type="primary" @click="create">确 定</el-button>
                </div>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { CreateCompetition } from "@/request/api";
export default {
    data () {
        return {
            name:"",
            time:"",
            time_length:"",
            money:0,
            context:"",
            formLabelWidth: '150px',
 
        }
    },
    methods:{
        create(){
            this.time = this.time.toLocaleString()
            this.time = this.time.replace(/\//g, '-')
            if(this.name==""||this.time==""||this.time_length==""||this.context==""){
                this.$message.warning("有内容为空")
            }else if(this.time_length<10){
                this.$message.warning("赛事时长小于10分钟")
            }else{
                CreateCompetition({
                    event_name:this.name,
                    event_time:this.time,
                    event_length:this.time_length,
                    money:this.money,
                    context:this.context
                }).then(res=>{

                    // console.log(res);
                    if(res.msg=="赛事审核id信息"){
                        this.$message.success("创建成功，等待审核")
                    }else{
                        this.$message.warning(res.msg)
                    }
                }).catch(err=>{
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
                this.clear()
            }
        },
        clear(){
            this.name=""
            this.time=""
            this.time_length=""
            this.context=""
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