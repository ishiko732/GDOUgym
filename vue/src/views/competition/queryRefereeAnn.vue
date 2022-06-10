<template>
<!-- 查询裁判公告 -->
    <div style="height:800px">
        <div class="form">
            <h2>查询裁判公告</h2>
            <el-form>
                <el-form-item label="赛事id：" :label-width="formLabelWidth">
                    <el-input v-model.trim="cid" autocomplete="off" placeholder="请输入赛事id"></el-input>
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
import { QueryRefereeAnn } from "@/request/api";
export default {
    data () {
        return {
            formLabelWidth: '200px',
            cid:"",
            RefereeAnnList:[]
        }
    },
    methods:{
        queryRefereeAnn(){
            if(this.cid==""){
                this.$message.warning("赛事id为空")
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
        .el-input{
            width: 200px;
        }
        /deep/.el-input__inner{
            width: 200px;
        }
        .el-button--primary{
            margin-left: 50px;
            transform: translateY(-15px);
        }
    }
</style>