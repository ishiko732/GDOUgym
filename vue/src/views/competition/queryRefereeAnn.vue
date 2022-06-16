<template>
<!-- 查询裁判公告 -->
    <div style="height:800px">
        <div class="form">
            <h2>查询裁判公告</h2>
            <el-form>
                <span>ps：未选择赛事查询，即查询全部信息</span>
                <el-form-item label="赛事：" :label-width="formLabelWidth"  style="margin-top:20px">
                    <el-select v-model="cid" placeholder="请选择赛事" filterable>
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                    </el-select>
                    <el-button type="primary" @click="queryRefereeAnn">查 询</el-button>
                </el-form-item>
                
                <el-form-item label="赛事裁判信息：" :label-width="formLabelWidth">
                    <el-table
                        :data="RefereeAnnList"
                        height="350"
                        border
                        style="width: 100%">
                        <el-table-column
                        prop="cid"
                        label="赛事id"
                        width="65">
                        </el-table-column>
                        <el-table-column
                        prop="judgment"
                        label="裁判名称"
                        width="100">
                        </el-table-column>
                        <el-table-column
                        prop="fieldName"
                        label="场地名称"
                        width="100">
                        </el-table-column>
                        <el-table-column
                        prop="competitionName"
                        label="赛事名称"
                        width="140">
                        </el-table-column>
                        <el-table-column
                        prop="introduction"
                        label="裁判公告">
                        </el-table-column>
                        <el-table-column
                        prop="startTime"
                        label="赛事开始时间"
                        width="150">
                        </el-table-column>
                        <el-table-column
                        prop="endTime"
                        label="赛事结束时间"
                        width="150">
                        </el-table-column>
                    </el-table>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import { QueryRefereeAnn,QueryEvent } from "@/request/api";
export default {
    data () {
        return {
            formLabelWidth: '200px',
            cid:"",
            RefereeAnnList:[],
            options:[{
                value:"",
                label:"查询全部裁判信息"
            }],
        }
    },
    created(){
        QueryEvent().then(res=>{
            res.data.forEach(item=>{
                if(item.isCancel==false){
                    this.options.push({
                        value:item.id,
                        label:item.name+" "+item.competitionTime
                    })
                }
            })
        })
    },
    methods:{
        queryRefereeAnn(){
            if(this.cid==""){
                 QueryRefereeAnn().then(res=>{
                    // console.log(res);
                    if(res.data.length==0){
                        this.$message.warning("没有裁判信息")
                    }
                    this.RefereeAnnList=res.data
                }).catch(err=>{
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
            }else{
                QueryRefereeAnn({cid:this.cid}).then(res=>{
                    // console.log(res);
                    if(res.data.length==0){
                        this.$message.warning("没有当前赛事的裁判信息")
                    }
                    this.RefereeAnnList=res.data
                }).catch(err=>{
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
            }
        }
    }
}
</script>
 
<style lang = "less" scoped>
    .form{
        width: 1100px;
        margin: 50px 150px;
        h2{
            text-align: center;
            margin-bottom: 50px;
            margin-left: 80px;
        }
        /deep/.el-input__inner,.el-input{
            width: 300px;
        }
        .el-button--primary{
            margin-left: 50px;
            transform: translateY(-15px);
        }
        span{
            color: red;
            margin-left: 130px;
        }
    }
</style>