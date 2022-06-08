<template>
  <div class="siteList" style="height:1000px;">
    <el-button type="text" @click="dialogVisible = true">新增场地类型</el-button>
    <el-table
        :data="siteData"
        style="width: 100%">
      <el-table-column
          prop="tid"
          label="编号"
          width="240">
      </el-table-column>
      <el-table-column
          prop="typeName"
          label="类型名称"
          width="240">
      </el-table-column>
      <el-table-column
          label="场地安排"
          width="240">
        <template slot-scope="scope">
          <i style="cursor: pointer" @click="arrange(scope.row,scope.$index)" class="el-icon-setting"></i>
        </template>
      </el-table-column>
      <el-table-column
          label="场地信息">
        <template slot-scope="scope">
          <i style="cursor: pointer" @click="message(scope.row,scope.$index)" class="el-icon-folder"></i>                </template>
      </el-table-column>
      <el-table-column
          label="操作">
        <template slot-scope="scope">
          <el-button @click="operate(scope.row,scope.$index)" type="text" size="small">编辑</el-button>
          <el-button type="text" size="small" @click="del(scope.row,scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
        title="新增场地类型"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
      <div style="display: flex">
        <span style="margin-top: 10px;">场地类型:&nbsp;&nbsp;</span>
        <el-input v-model="type" style="width: 60%"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="addtype">确定</el-button>
              </span>
    </el-dialog>
  </div>
</template>

<script>
import { queryType,addType,deleteType } from '@/request/api'
export default {
  name: "siteTypeManagement",
  data(){
    return{
      dialogVisible: false,
      type:"",
      siteData: []
    }
  },
  methods:{
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    operate(row,index) {
      console.log(index);
    },
    arrange(row,index) {
      this.$router.push({path:'/home/siteManagement/siteArrange',query:{tid:this.siteData[index].tid}})
    },
    message(row,index) {
      this.$router.push({ path: '/home/siteManagement/siteMessage', query: { tid: this.siteData[index].tid } })
    },
    del(row) {
      deleteType({tid:row.tid}).then(res=>{
        this.$message(res.msg)
        location.reload()
      })
    },
    addtype(){
      addType({typeName:this.type}).then(res=>{
        console.log(res)
      })
      this.dialogVisible=false
    }
  },
  created () {
    queryType().then(res => {
      this.siteData=res.data
    })
  }
}
</script>

<style scoped lang="less">
.siteList{
  width: 80%;
  margin-left: 10%;
}
i{
  margin-left: 8%;
}
</style>