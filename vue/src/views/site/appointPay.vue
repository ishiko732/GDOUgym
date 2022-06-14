<template>
  <div style="height:3000px;">
    <el-card class="box-card" style="margin-top:20px;" v-for="(item, index) in appointmentData">
      <div slot="header" class="clearfix">
        <span> 订单号 {{ item[0].id }}</span>
      </div>
      <div class="appointment_table">
        <template>
          <el-table :data="appointmentData[index]" style="width: 100%">
            <el-table-column prop="username" label="用户名" width="200">
            </el-table-column>
            <el-table-column prop="site" label="场地" width="100">
            </el-table-column>
            <el-table-column prop="time" label="时间" width="150">
            </el-table-column>
            <el-table-column prop="date" label="日期" width="100">
            </el-table-column>
            <el-table-column prop="money" label="元/小时" width="200">
            </el-table-column>
            <el-table-column prop="state" label="状态" width="200">
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <i class="el-icon-check" style="margin-left: 5px; cursor: pointer;"
                  @click="check(scope.row, scope.$index)"></i>
                <i class="el-icon-close" style="margin-left: 5px; cursor: pointer;"
                  @click="close(scope.row, scope.$index)"></i>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </div>
    </el-card>
  </div>
</template>

<script>
import { queryCheckByUid, cancelCheckById,pay } from '@/request/api'
export default {
  data () {
    return {
      appointmentData: []
    }
  },
  created () {
    queryCheckByUid().then(res => {
      res.data.forEach((item, index) => {
        var obj = {}
        var arr = []
        console.log(item.timeArrangeList[0].startTime);
        obj.username = item.userName
        obj.site = item.name
        obj.id = item.id
        obj.date = item.date
        obj.state = item.status
        obj.time = ""
        item.timeArrangeList.forEach(item2=>{
          obj.time = obj.time + item2.startTime + "-" + item2.endTime + '\r'
        })
        obj.money = item.money
        arr.push(obj)
        this.appointmentData.push(arr)
      });
      console.log(this.appointmentData);
    })
  },
  methods: {
    close (a,b) {
      if (a.state == "已退回"||a.state=="已取消") {
        this.$message.error("不能操作")
      } else {
        cancelCheckById({id:a.id}).then(res => {
          this.$message(res.msg)
          setTimeout(() => {
            location.reload();
          }, 1000);
        })
      }
    },
    check (a,b) {
      if (a.state == "已退回") {
        this.$message.error("已退回不能操作")
      }else {
        pay({id:a.id}).then(res=>{
          this.$message(res.msg)
          setTimeout(() => {
            location.reload();
          }, 1000);
        })
      }
    },
  }
}
</script>

<style scoped lang="less">
/deep/ * {
  overflow: hidden;
}
/deep/ .cell{
  text-align: center;
}

/deep/.box-card {
  width: 80%;
  margin: 0 auto;

  .clearfix {
    font-size: 18px;
  }

}
</style>