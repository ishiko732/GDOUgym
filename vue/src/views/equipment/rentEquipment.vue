<template>
  <div :style="rentData.length>10?'':'height:1000px'">
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
            prop="ableNumber"
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
            prop="username"
            type="index"
            label="租用人"
            width="180">
          <template slot-scope="scope">
            <el-input type="text" v-model="datalist[scope.$index].username"></el-input>
          </template>
        </el-table-column>
        <el-table-column
            fixed="right"
            width="120">
          <template slot-scope="scope">
            <el-button
                @click.native.prevent="deleteRow(scope.$index)"
                type="text"
                size="small">
              租用
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  data(){
    return{
      datalist:[],
      rentData: [{
        type:"球类",
        name: '篮球',
        number: 20,
        ableNumber: 19,
      },
        {
          type:"球类",
          name: '足球',
          number: 20,
          ableNumber: 19,
        },],
    }
  },
  methods:{
    deleteRow(index){
      console.log(this.datalist[index].rentNumber+this.datalist[index].username)
    }
  },
  created() {
    var length=this.rentData.length;
    for (let i = 0; i < length; i++) {
      let obj={}
      obj.username=""
      obj.rentNumber=""
      this.datalist.push(obj)
    }
  }
}
</script>

<style scoped lang="less">
.el-card{
  margin-top: 2%;
  margin-left: 10%;
  width: 80%;
  .name{
    margin-left: 41%;
    font-size: 22px;
  }
  .el-table .cell{
    text-align: center;
  }

}
</style>