<template>
<div :style="priceData.length>10?'':'height:1000px'">
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
          label="每小时器材租用费用"
          width="180">
      </el-table-column>
      <el-table-column
          prop="time"
          label="器材可租用时间(天)">
      </el-table-column>
    </el-table>
  </el-card>
</div>
</template>

<script>
export default {
  data(){
    return{
      priceData: [{
        name: '篮球',
        price: 20,
        time: 3
      },
        {
          name: '篮球',
          price: 20,
          time: 3
        },{
          name: '篮球',
          price: 20,
          time: 3
        },{
          name: '篮球',
          price: 20,
          time: 3
        },{
          name: '篮球',
          price: 20,
          time: 3
        },{
          name: '篮球',
          price: 20,
          time: 3
        },{
          name: '篮球',
          price: 20,
          time: 3
        },{
          name: '篮球',
          price: 20,
          time: 3
        },{
          name: '篮球',
          price: 20,
          time: 3
        },{
          name: '篮球',
          price: 20,
          time: 3
        }
      ]
    }
  },
}
</script>

<style scoped lang="less">
.el-card{
  margin-top: 2%;
  margin-left: 20%;
  width: 60%;
  .name{
    margin-left: 41%;
    font-size: 22px;
  }
  .el-table .cell{
    text-align: center;
  }

}
</style>