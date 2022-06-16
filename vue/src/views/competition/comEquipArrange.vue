<template>
<!-- 赛事器材安排 -->
    <div style="height:1500px">
        <el-card class="box-card" style="overflow: auto">
            <div slot="header" class="clearfix">
                <span class="name">赛事器材安排</span>
            </div>
            <div style="display:flex">
                <span class="span">场地：</span>
                <el-select v-model="cfid" placeholder="请选择">
                <el-option
                    v-for="(item,index) in options"
                    :key="index"
                    :label="item.label"
                    :value="item.value">
                </el-option>
                </el-select>
                <el-button type="primary" @click="submit">提交</el-button>
            </div>
            <el-table
            :data="rentData"
            stripe
            style="width: 100%;">
                <el-table-column
                    type="index"
                    prop="type"
                    label="器材种类"
                    width="150">
                </el-table-column>
                <el-table-column
                    prop="name"
                    label="器材名称"
                    width="150">
                </el-table-column>
                <el-table-column
                    prop="number"
                    label="器材数量"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="maxRentTime"
                    label="可租用时间(天)"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="available_number"
                    label="可租用数量"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="rentNumber"
                    type="index"
                    label="租用数量"
                    width="200">
                    <template slot-scope="scope">
                    <el-input type="number" v-model="datalist[scope.$index].rentNumber"></el-input>
                    </template>
                </el-table-column>
            </el-table>
      </el-card>
    </div>
</template>

<script>
import { queryEquipment,EventArrangeEquip,QueryComField } from "@/request/api";
export default {
    data () {
        return {
            cfid:"",
            formLabelWidth:'150px',
            rentData: [],
            datalist:[],
            equipmentList:[],
            options:[]
        }
    },
    created() {
        this.display()
        QueryComField().then(res=>{
            // console.log(res);
            for(let i in res.data){
                this.options.push({
                    value:res.data[i].id,
                    label:res.data[i].name+res.data[i].startTime
                })
            }
        })
    },
    methods:{
        submit(){
            // console.log(this.datalist);
            if(this.cfid==""){
                this.$message.warning("场地id为空")
            }else{
                this.equipmentList=[]
                for(let i in this.datalist){
                    let number=parseInt(this.datalist[i].rentNumber)
                    if(number>0){
                        this.equipmentList.push({
                            eid:this.rentData[i].id,
                            number:number
                        })
                    }
                }
                if(this.equipmentList.length==0){
                    this.$message.warning("当前没有选择器材")
                }else{
                    var obj = {}
                    obj.cfId = this.cfid
                    obj.equipment = this.equipmentList
                    EventArrangeEquip(obj).then(res=>{
                        // console.log(res);
                        this.$message.success("申请成功")
                        this.display()
                    }).catch(err=>{
                        this.$message.error(err.response.data.data+"，请重新登录")
                    })
                }
            }
        },
        display(){
            queryEquipment().then(res=>{
                // console.log(res)
                this.cfid=""
                this.rentData=[]
                this.datalist=[]
                res.data[0].forEach((item,index)=>{
                    var obj={}
                    obj.id=item.id
                    obj.available_number=res.data[1][index]
                    obj.type=item.types
                    obj.number=item.number
                    obj.name=item.name
                    obj.maxRentTime=item.maxRentTime
                    obj.money=item.rentPrice
                    this.rentData.push(obj)
                })
                // console.log(this.rentData)
                var length=this.rentData.length;
                for (let i = 0; i < length; i++) {
                    let obj={}
                    obj.rentNumber="0"
                    this.datalist.push(obj)
                }
            })
        }
    },
}
</script>
 
<style lang = "less" scoped>
/deep/ .el-input__inner{
    text-align: center;
}
.el-card{
    margin-top: 2%;
    margin-left: 10%;
    width: 70%;
    .name{
        margin-left: 41%;
        font-size: 22px;
    }
    /deep/.el-table .cell{
        text-align: center;
    }
    /deep/.el-input__inner,.el-input{
        width: 150px;
        text-align: left;
    }
    .el-button--primary{
        margin-left: 50px;
    }
    .span{
        font-size: 20px;
        margin-top: 6px;
    }
}
</style>