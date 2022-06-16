<template>
<!-- 赛事创建 -->
    <div style="height:1000px">
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
                <span>ps:点击<b>查询</b>可以查看当前用户创建的赛事审核情况</span>
                <div class="button">
                    <el-button @click="clear">清 空</el-button>
                    <el-button type="primary" @click="create">确 定</el-button>
                    <el-button type="primary" @click="query">查 询</el-button>
                </div>
                <el-form-item label="创建的赛事信息：" :label-width="formLabelWidth">
                    <el-table
                        :data="comInfoList"
                        height="300"
                        border
                        style="width: 100%">
                        <el-table-column
                        prop="cid"
                        label="赛事id"
                        width="100">
                        </el-table-column>
                        <el-table-column
                        prop="competition.name"
                        label="赛事名称"
                        width="150">
                        </el-table-column>
                        <el-table-column
                        prop="competition.competitionTime"
                        label="赛事时间"
                        width="180">
                        </el-table-column>
                        <el-table-column
                        prop="competition.eventLength"
                        label="赛事时长(分钟)"
                        width="100">
                        </el-table-column>
                        <el-table-column
                        prop="status"
                        label="审核状态"
                        width="100">
                        </el-table-column>
                        <el-table-column
                        prop="reason"
                        label="审核理由">
                        </el-table-column>
                    </el-table>
                </el-form-item>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { CreateCompetition,SelLoginUserInfo,QueryEventCheck } from "@/request/api";
export default {
    data () {
        return {
            uid:"",
            name:"",
            time:"",
            time_length:"",
            money:0,
            context:"",
            formLabelWidth: '150px',
            comInfoList:[],
 
        }
    },
    created(){
        SelLoginUserInfo().then(res=>{
            this.uid=res.data.id
        })
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
            this.comInfoList=[]
        },
        query(){
            QueryEventCheck({uid:this.uid}).then(res=>{
                if(res.data.length==0){
                    this.$message.warning("当前用户没有创建过赛事")
                }else{
                    this.comInfoList=res.data
                }
            }).catch(err=>{
                this.$message.error(err.response.data.data+"，请重新登录")
            })
        }
    }
}
</script>
 
<style lang = "less" scoped>
.form{
    width: 900px;
    margin: 50px 300px;
    h2{
        text-align: center;
        margin-bottom: 50px;
        margin-left: 80px;
    }
    span{
        color:red;
        margin-left: 90px;
    }
    .button{
        margin-top: 20px;
        margin-bottom: 30px;
        padding-left: 50px;
        .el-button{
            margin: 0 40px;
        }
    }
    /deep/.el-input__inner,.el-input{
        width: 300px;
    }
}
</style>