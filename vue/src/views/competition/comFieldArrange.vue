<template>
<!-- 比赛场地安排 -->
    <div style="height:1000px"> 
        <div class="form">
            <h2>比赛场地安排</h2>
            <el-form>
                <el-form-item label="赛事：" :label-width="formLabelWidth"  style="margin-top:20px">
                    <el-select v-model="cid" placeholder="请选择赛事" filterable>
                    <el-option
                        v-for="item in com_options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                    </el-select>
                    <el-button type="primary" @click="submit">提 交</el-button>
                    <el-button type="primary" @click="clear">重 置</el-button>
                </el-form-item>
                <el-form-item label="选择场地和时间：" :label-width="formLabelWidth" class="elCascader">
                    <el-cascader
                    ref="select"
                    :options="options"
                    :props="props"
                    clearable
                    v-model="changeFieldList">
                    </el-cascader>
                    <el-button type="primary" @click="add">添 加</el-button>
                </el-form-item>
                <el-form-item label="选择的场地信息：" :label-width="formLabelWidth">
                    <el-table
                        :data="fieldInfoList"
                        height="400"
                        border
                        style="width: 100%">
                        <el-table-column
                        prop="date"
                        label="日期"
                        width="150">
                        </el-table-column>
                        <el-table-column
                        prop="tname"
                        label="场地类型"
                        width="100">
                        </el-table-column>
                        <el-table-column
                        prop="fname"
                        label="场地名"
                        width="100">
                        </el-table-column>
                        <el-table-column
                        prop="index"
                        label="场地号"
                        width="100">
                        </el-table-column>
                        <el-table-column
                        prop="time"
                        label="时间段">
                        </el-table-column>
                    </el-table>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import { loadingDate,queryType,queryFieldByType,QueryFid,QueryTime,ComArrangeField,EventSetFields,QueryEvent } from "@/request/api";
export default {
    data () {
        return {
            cid:"",
            props: { multiple: true },
            formLabelWidth:'150px',
            fieldInfoList:[],
            options: [],
            changeFieldList:[],
            submitList:[],
            ids:[],     // 审核id
            com_options:[],
 
        }
    },
    created(){
        // 获取日期
        loadingDate().then(res=>{
            for(let i in res.data){
                let type=[]
                // 获取场地类型
                queryType().then(res1=>{
                    for (let j in res1.data) {
                        let byType = []
                        // 根据类型查询场地
                        queryFieldByType({tid:res1.data[j].tid}).then(res2=>{
                            for(let g in res2.data){
                                let fid=[]
                                //根据场地id找场地号
                                QueryFid({fid:res2.data[g].fid}).then(res3=>{
                                    for(let h in res3.data){
                                        let time = []
                                        // 获取时间段下拉列表
                                        QueryTime({
                                            date:res.data[i],
                                            tid:res1.data[j].tid,
                                            fid:res2.data[g].fid,
                                            index:res3.data[h]
                                        }).then(res4=>{
                                            for(let f in res4.data){
                                                time.push({
                                                    value:res4.data[f].timeId,
                                                    label:res4.data[f].startTime+"-"+res4.data[f].endTime
                                                })
                                            }

                                        })
                                        fid.push({
                                            value:res3.data[h],
                                            label:res3.data[h],
                                            children:time
                                        })
                                    }
                                })
                                byType.push({
                                    // value:res2.data[g].fid,
                                    value:res2.data[g].description,
                                    label:res2.data[g].description,
                                    children:fid
                                })
                            }
                        })
                        type.push({
                            // value:res1.data[j].tid,
                            value:res1.data[j].typeName,
                            label:res1.data[j].typeName,
                            children:byType
                        })
                    }
                })
                this.options.push({
                    value:res.data[i],
                    label:res.data[i],
                    children:type
                })
            }
        }).catch(err=>{
            // console.log("err:",err.response.data);
            this.$message.error(err.response.data.data+"，请重新登录")
        })

        QueryEvent().then(res=>{
            res.data.forEach(item=>{
                if(item.isCancel==false && item.isCheck=="待审核"){
                    this.com_options.push({
                        value:item.id,
                        label:item.name+" "+item.competitionTime
                    })
                }
            })
        })
    },
    methods: {
        add(){
            if(this.changeFieldList.length==0){
                // this.$message.warning("暂未选择场地")
                this.fieldInfoList=[]
                this.submitList=[]
            }else{
                this.fieldInfoList=[]
                this.submitList=[]
                for(let i in this.changeFieldList){
                    // 获取选中的label值
                    // console.log(this.$refs.select.getCheckedNodes()[i].pathLabels);
                    // console.log(this.changeFieldList[i]);
                    this.fieldInfoList.push({
                        date:(this.changeFieldList[i])[0],
                        tname:(this.changeFieldList[i])[1],
                        fname:(this.changeFieldList[i])[2],
                        index:(this.changeFieldList[i])[3],
                        time:this.$refs.select.getCheckedNodes()[i].pathLabels[4]
                    })
                    this.submitList.push({
                        name:(this.changeFieldList[i])[2]+((this.changeFieldList[i])[1])[0]+(this.changeFieldList[i])[3],
                        timeId:(this.changeFieldList[i])[4]
                    })
                }
            }
        },
        submit(){
            if(this.cid==""){
                this.$message.warning("未选择赛事")
            }else if(this.submitList.length==0){
                this.$message.warning("未选择场地")
            }else{
                var obj={}
                obj.queryTimeVos=this.submitList
                ComArrangeField(obj).then(res=>{
                    // console.log(res);
                    this.ids.push(res.data.id)
                    if(res.msg=="提交审核成功"){
                        this.$message.success(res.msg)
                        var obj={}
                        obj.cid=this.cid
                        obj.ids=this.ids
                        EventSetFields(obj).then(res=>{
                            // console.log(res);
                            if(res.msg=="返回成功绑定的赛事场地id信息"){
                                this.$message.success("赛事绑定场地成功")
                                this.clear()
                            }else{
                                this.$message.warning(res.msg)
                            }
                        })
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
            this.changeFieldList=[]
            this.submitList=[]
            this.fieldInfoList=[]
        }
    },
}
</script>
 
<style lang = "less" scoped>
    .form{
        width: 900px;
        margin: 50px 200px;
        h2{
            text-align: center;
            margin-bottom: 50px;
            margin-left: 80px;
        }
        .el-button--primary{
            margin-left: 50px;
            transform: translateY(-15px);
        }
        .elCascader .el-button--primary{
            transform: translateY(-30px)!important;
        }
        .button{
            margin-left: 150px;
            margin-bottom: 20px;
        }
        /deep/.el-input__inner,.el-input{
            width: 300px;
        }
    }
</style>