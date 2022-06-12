<template>
<div>
  <div style="height: 2000px;">
    <el-table
        :data="rentData"
        style="width: 60%;margin: 0 auto;margin-top: 2%;">
      <el-table-column
          prop="name"
          label="器材名称"
          width="180">
      </el-table-column>
      <el-table-column
          prop="number"
          label="器材数量"
          width="180">
      </el-table-column>
      <el-table-column
          prop="rentTime"
          label="租借时间(小时)">
      </el-table-column>
      <el-table-column
          prop="status"
          label="归还状态"
          width="180">
      </el-table-column>
      <el-table-column
          label="器材归还">
        <template slot-scope="scope">
          <i class="el-icon-setting" @click="ret(scope.row,scope.$index)"></i>
        </template>
      </el-table-column>
    </el-table>
  </div>
</div>
</template>

<script>
import {queryRentEquipment, redeemEquipment} from "@/request/api";

export default {
  data(){
    return{
      username:"",
      rentData:[],
    }
  },
  methods:{
    ret(row,index){
      if(row.status=="未归还"){
        redeemEquipment({rid:row.rid}).then(res=>{
          this.$message(res.msg)
          setTimeout(()=>{
            location.reload()
          },1000)
        })
      }else{
        this.$message.warning("器材已归还")
      }
    }
  },
  created() {
    this.username=localStorage.getItem("username")
    queryRentEquipment({username:this.username}).then(res=>{
      // console.log(res)
      res.data.forEach(item=>{
        var obj={}
        obj.name=item.ename
        obj.number=item.number
        obj.eid=item.eid
        obj.rid=item.rid
        obj.uid=item.uid
        obj.rentTime=item.rentTime
        if(item.status==0){
          obj.status="未归还"
        }else if(item.status==1){
          obj.status="已归还"
        }
        this.rentData.push(obj)
      })
    })
  }
}
</script>

<style scoped lang="less">
/deep/ .cell{
  text-align: center;
}
i{
  cursor: pointer;
}
</style>