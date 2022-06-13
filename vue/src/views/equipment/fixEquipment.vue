<template >
  <div>
    <div class="container">
      <el-table
          :data="fixData"
          border
          style="width: 100%;">
        <el-table-column
            prop="type"
            label="器材种类"
            width="180">
        </el-table-column>
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
            label="确认维护">
          <template slot-scope="scope">
            <i class="el-icon-delete" @click="fix(scope.row,scope.$index)"></i>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import {queryFixEquipment,confirmFixEquipment} from '@/request/api'
export default {
  data(){
    return{
      type:"",
      number:"",
      name:"",
      fixData:[]
    }
  },
  methods:{
    cancel(){
      this.$router.go(-1)
    },
    fix(a,b){
      confirmFixEquipment({eid:a.reid}).then(res=>{
        this.$message(res.msg)
        setTimeout(() => {
          location.reload();
        }, 1000);
      })
    },
  },
  created() {
    queryFixEquipment().then(res=>{
      // console.log(res)
      res.data.forEach(item=>{
        var obj={}
        obj.type=item.type
        obj.reid=item.fid
        obj.name=item.name
        obj.number=item.number
        this.fixData.push(obj)
      })
    })
  }
}
</script>

<style scoped lang="less">
.container{
  width: 50%;
  height: 1000px;
  margin: 0 auto;
  margin-top: 3%;
  i{
    cursor: pointer;
  }
}
</style>