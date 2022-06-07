<template>
  <div class="siteList">
    <el-button type="text" @click="dialogVisible = true">新增场地类型</el-button>
    <el-table
        :data="siteData"
        style="width: 100%">
      <el-table-column
          prop="siteId"
          label="编号"
          width="240">
      </el-table-column>
      <el-table-column
          prop="name"
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
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
      <el-input></el-input>
      <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="dialogVisible = false">确定</el-button>
              </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "siteTypeManagement",
  data(){
    return{
      dialogVisible: false,
      siteData: [{
        siteId: '2016-05-02',
        name: '王小虎1',
      },{
        siteId: '2016-05-02',
        name: '王小虎2',
      },{
        siteId: '2016-05-02',
        name: '王小虎3',
      },]
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
      this.$router.push({path:'/home/siteManagement/siteArrange',query:{id:this.siteData[index].name}})
    },
    message(row,index) {
      console.log(index);
    },
    del(row,index) {
      console.log(index);
    },
  },
  created() {
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