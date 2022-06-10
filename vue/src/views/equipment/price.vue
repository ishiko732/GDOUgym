<template>
<div>
  <div style="height:1000px">
    <el-card class="box-card" style="overflow: auto">
      <div slot="header" class="clearfix">
        <span class="name">器材租用收费表</span>
        <el-button @click="$router.go(-1)" style="float: right; padding: 3px 0;font-size: 18px" type="text">返回</el-button>
      </div>
      <el-table
          :data="priceData"
          stripe
          style="width: 100%;">
        <el-table-column
            prop="name"
            label="器材名称"
            width="180">
        </el-table-column>

      </el-table>
    </el-card>

  </div>
</div>
</template>

<script>
import {queryEquipmentRentStandard,addEquipmentRentStandard} from "@/request/api";

export default {
  data(){
    return{
      eid:0,
      roleId:0,
      priceData: []
    }
  },
  methods:{

  },
  created() {
    this.roleId=localStorage.getItem("roleId")
    this.priceData=[]
    queryEquipmentRentStandard().then(res=>{
      res.data.forEach(item=>{
        var obj={}
        obj.name=item.name
        obj.price=item.price
        obj.time=item.maxRentTime
        obj.eid=item.eid
        obj.erid=item.erid
        this.priceData.push(obj)
      })
    })
  }
}
</script>

<style scoped lang="less">
.el-card{
  margin-top: 2%;
  margin-left: 20%;
  width: 50%;
  .name{
    margin-left: 41%;
    font-size: 22px;
  }
  /deep/.el-table .cell{
    text-align: center;
  }
  i{
    cursor: pointer;
  }

}
</style>