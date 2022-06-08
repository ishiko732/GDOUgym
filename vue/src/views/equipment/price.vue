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
        <el-table-column
            prop="price"
            label="每小时器材租用费用(元)"
            width="250">
        </el-table-column>
        <el-table-column
            prop="time"
            label="器材可租用时间(天)"
            >
        </el-table-column>
        <el-table-column
            v-if="roleId==1||roleId==5?true:false"
            label="器材报废"
            width="150">
          <template slot-scope="scope">
            <i class="el-icon-edit" @click="edit(scope.row,scope.$index)"></i>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog
        title="更改收费标准"
        :visible.sync="dialogVisible"
        width="30%"
    >
      <div style="display: flex;margin-bottom: 10px;margin-left: 100px;">
        <span style="margin-top: 10px">租用价格:&nbsp;&nbsp;&nbsp;</span>
        <el-input v-model="price" style="width: 50%"></el-input>
      </div>
      <div style="display: flex;margin-bottom: 10px;margin-left: 100px;">
        <span style="margin-top: 10px">租用时间:&nbsp;&nbsp;&nbsp;</span>
        <el-input v-model="maxRentTime" style="width: 50%"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="edit_price">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</div>
</template>

<script>
import {queryEquipmentRentStandard} from "@/request/api";

export default {
  data(){
    return{
      eid:0,
      roleId:0,
      dialogVisible:false,
      price:"",
      maxRentTime:"",
      priceData: []
    }
  },
  methods:{
    edit(a,b){
      this.dialogVisible=true
      this.eid=a.eid
      console.log(a)
    },
    edit_price(){

    }
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