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
                        style="width: 100%"
                        @row-click="clickData">
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
                        prop="competition.eventLength"
                        label="赛事时长(分钟)"
                        width="80">
                        </el-table-column>
                        <el-table-column
                        prop="competition.createTime"
                        label="赛事创建时间"
                        width="180">
                        </el-table-column>
                        <el-table-column
                        prop="FieldName"
                        label="场地信息"
                        width="200">
                        </el-table-column>
                        <el-table-column
                        prop="FieldStatus"
                        label="场地审核状态"
                        width="120">
                        </el-table-column>
                        <el-table-column
                        prop="EquipmentName"
                        label="器材信息"
                        width="200">
                        </el-table-column>
                        <el-table-column
                        prop="competition.introduction"
                        label="赛事内容"
                        width="300">
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
import { QueryEventCheck,EventCheck,queryEquipment } from "@/request/api";
export default {
    data () {
        return {
            formLabelWidth: '200px',
            type:"待审核",
            EventCheckList:[],
            check_id:"",
            status:"",
            reason:"",
            ids:[],
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
        queryEquipment().then(res=>{
            this.ids=res.data[0]
        })
    },
    methods:{
        clickData(item){
            // console.log(item);
            this.check_id = item.id
        },
        queryEventCheck(){
            this.EventCheckList=[]
            if(this.type==""){
                this.$message.warning("未选择查询类型")
            }else{
                QueryEventCheck({
                    status:this.type
                }).then(res=>{
                    // console.log(res);
                    if(res.data.length==0){
                        this.$message.warning("当前查询类型没有数据")
                    }else{
                        for(let i in res.data){
                            if(res.data[i].competition.isCancel==true){

                            }else if(res.data[i].competition.competitionFields.length==0){
                                this.EventCheckList.push(res.data[i])
                            }else{
                                let FieldName = ""
                                let FieldStatus = ""
                                let EquipmentName=""
                                for(let j in res.data[i].competition.competitionFields){
                                    FieldName = FieldName+res.data[i].competition.competitionFields[j].name+";"
                                    FieldStatus = FieldStatus+res.data[i].competition.competitionFields[j].fieldStatus+";"
                                    for(let g in res.data[i].competition.competitionFields[j].competitionEquipments){
                                        for(let h in this.ids){
                                            if(res.data[i].competition.competitionFields[j].competitionEquipments[g].eid==this.ids[h].id){
                                                res.data[i].competition.competitionFields[j].competitionEquipments[g].name=this.ids[h].name
                                            }
                                        }
                                        EquipmentName = EquipmentName+res.data[i].competition.competitionFields[j].competitionEquipments[g].name+res.data[i].competition.competitionFields[j].competitionEquipments[g].number+"个；"
                                    }
                                }
                                res.data[i].EquipmentName = EquipmentName
                                res.data[i].FieldName = FieldName
                                res.data[i].FieldStatus = FieldStatus
                                this.EventCheckList.push(res.data[i])
                            }
                        }
                    }
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
        width: 1400px;
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