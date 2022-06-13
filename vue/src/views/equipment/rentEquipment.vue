<template>
  <div>
    <div style="height: 2000px;">
      <el-card class="box-card" style="overflow: auto">
        <div slot="header" class="clearfix">
          <span class="name">器材租用收费表</span>
          <el-button @click="$router.go(-1)" style="float: right; padding: 3px 0;font-size: 18px" type="text">返回</el-button>
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
              label="可租用时间(小时)"
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
              width="180">
            <template slot-scope="scope">
              <el-input type="number" v-model="datalist[scope.$index].rentNumber"></el-input>
            </template>
          </el-table-column>
          <el-table-column
              prop="time"
              type="index"
              label="租用时间(天)"
              width="180">
            <template slot-scope="scope">
              <el-input type="number" v-model="datalist[scope.$index].time"></el-input>
            </template>
          </el-table-column>
          <el-table-column
              fixed="right"
              >
            <template slot-scope="scope">
              <el-button
                  @click="rent(scope.row,scope.$index)"
                  type="text"
                  size="small">
                租用
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
  </div>
</template>

<script>
import {addRentEquipment, queryEquipment} from "@/request/api";

export default {
  data(){
    return{
      datalist:[],
      rentData: [],
    }
  },
  methods:{
    rent(row,index){
      let number=parseInt(this.datalist[index].rentNumber)
      let rentTime=parseInt(this.datalist[index].time)
      if(number<=0){
        this.$message.error("数量小于零不能租用")
      }else{
        addRentEquipment({eid:row.id,rentTime:rentTime,number:number}).then(res=>{
          this.$message(res.msg)
          setTimeout(()=>{
            location.reload()
          },1000)
        })
      }
    }
  },
  created() {
    queryEquipment().then(res=>{
      // console.log(res)
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
        obj.time=""
        obj.rentNumber=""
        this.datalist.push(obj)
      }
    })
  }
}
</script>

<style scoped lang="less">
/deep/ input[type=number]::-webkit-inner-spin-button, input[type=number]::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

/deep/ .el-input__inner{
  line-height: 1px !important;
  text-align: center;
}
.el-card{
  margin-top: 2%;
  margin-left: 10%;
  width: 80%;
  .name{
    margin-left: 41%;
    font-size: 22px;
  }
  /deep/.el-table .cell{
    text-align: center;
  }

}
</style>