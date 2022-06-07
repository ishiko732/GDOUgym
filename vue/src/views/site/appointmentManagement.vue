<template>
  <div>
    <el-card class="box-card" style="margin-top:20px;">
      <div slot="header" class="clearfix">
        <span>2022-06-02 10:33:30 订单号 1</span>
      </div>
      <div class="appointment_table">
        <template>
          <el-table :data="appointmentData" style="width: 100%">
            <el-table-column prop="username" label="用户名" width="250">
            </el-table-column>
            <el-table-column prop="site" label="场地" width="250">
            </el-table-column>
            <el-table-column prop="time" label="时间" width="250">
            </el-table-column>
            <el-table-column prop="state" label="状态" width="250">
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope"> 
                <i class="el-icon-check" style="margin-left: 5px; cursor: pointer;"
                  @click="check(scope.row,scope.$index)"></i>
                <i class="el-icon-close" style="margin-left: 5px; cursor: pointer;"
                  @click="close(scope.row,scope.$index)"></i>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "appointmentManagement",
  data () {
    return {
      appointmentData:[{
        time: '2016-05-02',
        username: '王小虎',
        state: '未审核',
        site: '羽毛球场'
      }, {
          time: '2016-05-02',
          username: '王小虎',
          state: '未付款',
          site: '篮球场'
        },]
    }
  },
  methods: {
    close () { 

    },
    check () {
      
    },
  }
}
</script>

<style scoped lang="less">






/deep/ *{
  overflow: hidden;
}
/deep/.box-card{
  width: 90%;
  margin: 0 auto;
  .clearfix{
    font-size: 18px;
  }
  
}
</style>