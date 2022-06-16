<template>
<!-- 赛事查询 -->
    <div style="height:1000px">
        <div class="form">
            <h2>查询赛事</h2>
            <el-form>
                <p><b>ps：填写一种类型即可查询(开始和结束时间必须同步填写,全部为空即查询全部赛事)</b></p>
                <el-form-item label="赛事id：" :label-width="formLabelWidth">
                    <el-input v-model.trim="cid" autocomplete="off" placeholder="请输入赛事id"></el-input>
                </el-form-item>
                <el-form-item label="赛事名称：" :label-width="formLabelWidth">
                    <el-input v-model.trim="name" autocomplete="off" placeholder="请输入赛事名称"></el-input>
                </el-form-item>
                <el-form-item label="创建赛事的用户名：" :label-width="formLabelWidth">
                    <el-input v-model.trim="uname" autocomplete="off" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="开始时间：" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="startTime"
                        type="datetime"
                        placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间：" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="endTime"
                        type="datetime"
                        placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <div class="button">
                    <el-button type="primary" @click="query">查 询</el-button>
                    <el-button type="primary" @click="queryTime">根据时间查询</el-button>
                </div>
                    
                <el-form-item label="赛事信息：" :label-width="formWidth">
                    <el-table
                        :data="eventInfoList"
                        height="400"
                        border
                        style="width: 100%">
                        <el-table-column
                        prop="id"
                        label="id"
                        width="50">
                        </el-table-column>
                        <el-table-column
                        prop="name"
                        label="赛事名称"
                        width="100">
                        </el-table-column>
                        <el-table-column
                        prop="competitionTime"
                        label="赛事时间"
                        width="180">
                        </el-table-column>
                        <el-table-column
                        prop="eventLength"
                        label="赛事时长(单位为：分钟)"
                        width="120">
                        </el-table-column>
                        <el-table-column
                        prop="cfId"
                        label="场地id"
                        width="100">
                        </el-table-column>
                        <el-table-column
                        prop="fieldName"
                        label="场地名称"
                        width="180">
                        </el-table-column>
                        <el-table-column
                        prop="introduction"
                        label="赛事内容">
                        </el-table-column>
                    </el-table>
                </el-form-item>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { QueryEvent } from "@/request/api";
export default {
    data () {
        return {
            eventInfoList:[],
            formLabelWidth: '200px',
            formWidth:'100px',
            cid:"",
            name:"",
            uname:"",
            startTime:"",
            endTime:"",
        }
    },
    methods:{
        query(){
            this.eventInfoList=[]
            QueryEvent({
                cid:this.cid,
                name:this.name,
                uname:this.uname
            }).then(res=>{
                // console.log(res);
                res.data.forEach(item=>{
                    if(item.isCheck=="审核通过"&&item.isCancel==false){
                        let fieldName = ""
                        let cfId = ""
                        item.competitionFields.forEach(item2=>{
                            fieldName = fieldName+item2.name+"；"
                            cfId = cfId+item2.id+", "
                        })
                        item.fieldName = fieldName
                        item.cfId = cfId
                        this.eventInfoList.push(item)
                    }
                })
                if(this.eventInfoList.length==0){
                    this.$message.warning("未查询到赛事")
                }
            }).catch(err=>{
                this.$message.error(err.response.data.data+"，请重新登录")
            })
        },
        queryTime(){
            this.eventInfoList=[]
            if(this.startTime==""||this.endTime==""||this.startTime==null||this.endTime==null){
                this.$message.warning("开始时间或者结束时间为空")
            }else{
                this.startTime = this.startTime.toLocaleString()
                this.startTime = this.startTime.replace(/\//g, '-')
                this.endTime = this.endTime.toLocaleString()
                this.endTime = this.endTime.replace(/\//g, '-')
                QueryEvent({
                    start_time:this.startTime,
                    end_time:this.endTime
                }).then(res=>{
                    // console.log(res);
                    res.data.forEach(item=>{
                        if(item.isCheck=="审核通过"){
                            let fieldName = ""
                            let cfId = ""
                            item.competitionFields.forEach(item2=>{
                                fieldName = fieldName+item2.name+"；"
                                cfId = cfId+item2.id+", "
                            })
                            item.fieldName = fieldName
                            item.cfId = cfId
                            this.eventInfoList.push(item)
                        }
                    })
                    if(this.eventInfoList.length==0){
                        this.$message.warning("未查询到赛事")
                    }
                }).catch(err=>{
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
            }
        },
    }
}
</script>
 
<style lang = "less" scoped>
    .form{
        width: 1100px;
        margin: 20px 300px;
        h2{
            text-align: center;
            margin-bottom: 20px;
            margin-left: 60px;
        }
        p{
            margin-left: 100px;
            color:red;
            padding-bottom: 20px;
        }
        .button{
            margin-left: 150px;
            margin-bottom: 20px;
        }
        .el-button--primary{
            margin-right:60px;
        }
        /deep/.el-input__inner,.el-input{
            width: 300px;
        }
    }
</style>