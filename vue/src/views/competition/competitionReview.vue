<template>
<!-- 赛事审核 -->
    <div style="height:900px">
        <div class="form">
            <h2>赛事审核</h2>
            <el-form>
                <el-form-item label="赛事状态：" :label-width="formLabelWidth">
                    <el-select v-model="type" placeholder="请选择">
                    <el-option
                        v-for="item in type_options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                    </el-select>
                    <el-button type="primary" @click="queryEventCheck">查 询</el-button>
                    <el-button type="primary" @click="eventCheck">审 核</el-button>
                </el-form-item>
                
                <el-form-item label="查询的赛事信息：" :label-width="formLabelWidth">
                    <el-table
                        :data="EventCheckList"
                        height="350"
                        border
                        style="width: 100%">
                        <el-table-column
                        prop="id"
                        label="审核id"
                        width="65">
                        </el-table-column>
                        <el-table-column
                        prop="cid"
                        label="赛事id"
                        width="65">
                        </el-table-column>
                        <el-table-column
                        prop="competition.name"
                        label="赛事名称"
                        width="120">
                        </el-table-column>
                        <el-table-column
                        prop="competition.competitionTime"
                        label="赛事时间"
                        width="180">
                        </el-table-column>
                        <el-table-column
                        prop="competition.createTime"
                        label="赛事创建时间"
                        width="180">
                        </el-table-column>
                        <el-table-column
                        prop="competition.introduction"
                        label="赛事内容">
                        </el-table-column>
                        <el-table-column
                        prop="status"
                        label="状态"
                        width="100">
                        </el-table-column>
                    </el-table>
                </el-form-item>
                <el-form-item label="赛事审核id：" :label-width="formLabelWidth">
                    <el-input v-model.trim="check_id" autocomplete="off" placeholder="请输入赛事审核id"></el-input>
                </el-form-item>
                <el-form-item label="赛事审核状态：" :label-width="formLabelWidth">
                    <el-select v-model="status" placeholder="请选择">
                    <el-option
                        v-for="item in type_options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="审核理由：" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model.trim="reason" autocomplete="off" placeholder="请输入审核理由"></el-input>
                </el-form-item>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { QueryEventCheck,EventCheck } from "@/request/api";
export default {
    data () {
        return {
            formLabelWidth: '200px',
            type:"待审核",
            EventCheckList:[],
            check_id:"",
            status:"",
            reason:"",
            type_options:[
                {
                    value:"待审核",
                    label:"待审核"
                },
                {
                    value:"审核通过",
                    label:"审核通过"
                },
                {
                    value:"审核未通过",
                    label:"审核未通过"
                },
                {
                    value:"审核取消",
                    label:"审核取消"
                },
                {
                    value:"重新审核",
                    label:"重新审核"
                },
            ]
        }
    },
    created(){
        this.queryEventCheck()
    },
    methods:{
        queryEventCheck(){
            if(this.type==""){
                this.$message.warning("未选择查询类型")
            }else{
                QueryEventCheck({
                    status:this.type
                }).then(res=>{
                    // console.log(res);
                    if(res.data.length==0){
                        this.$message.warning("当前查询类型没有数据")
                    }
                    this.EventCheckList=res.data
                }).catch(err=>{
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
            }
        },
        eventCheck(){
            if(this.check_id==""||this.status==""||this.reason==""){
                this.$message.warning("有内容为空")
            }else{
                EventCheck({
                    check_id:this.check_id,
                    status:this.status,
                    reason:this.reason
                }).then(res=>{
                    // console.log(res);
                    if(res.data==true){
                        this.$message.success("审核成功")
                        this.queryEventCheck()
                    }else{
                        this.$message.warning("审核失败，审核id 不存在")
                    }
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
        width: 1200px;
        margin: 50px 150px;
        h2{
            text-align: center;
            margin-bottom: 50px;
            margin-left: 80px;
        }
        /deep/.el-textarea__inner{
            width: 600px;
            height: 100px;
            font-size: 20px;
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