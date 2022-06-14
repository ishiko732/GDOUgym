<!-- 学生预约 -->
<template>
  <div class="arrangeContainer" style="height:1000px;">
    <el-tabs tab-position=" top" @tab-click="changeName">
    <el-tab-pane :label="name.typeName" v-for="(name,index) in nameList">
      <el-tabs tab-position="top" @tab-click="changeType">
        <el-tab-pane :label="data.description" v-for="(data,dataindex) in typeList">
          <el-tabs tab-position="top" style="height: 100%;" @tab-click="changeDate">
            <el-tab-pane :label="date" v-for="(date,dateindex) in dateList">
              <template>
                <el-table :data="msgData" style="width: 100%;" :cell-class-name="tableCellClassName"
                  @cell-click="cellClick">
                  <el-table-column prop="name" label="" width="115">
                  </el-table-column>
                  <el-table-column prop="state1" label="8:00-9:00" width="115">
                  </el-table-column>
                  <el-table-column prop="state2" label="9:00-10:00" width="115">
                  </el-table-column>
                  <el-table-column prop="state3" label="10:00-11:00" width="115">
                  </el-table-column>
                  <el-table-column prop="state4" label="11:00-12:00" width="115">
                  </el-table-column>
                  <el-table-column prop="state5" label="12:00-13:00" width="115">
                  </el-table-column>
                  <el-table-column prop="state6" label="13:00-14:00" width="115">
                  </el-table-column>
                  <el-table-column prop="state7" label="14:00-15:00" width="115">
                  </el-table-column>
                  <el-table-column prop="state8" label="15:00-16:00" width="115">
                  </el-table-column>
                  <el-table-column prop="state9" label="16:00-17:00" width="115">
                  </el-table-column>
                  <el-table-column prop="state10" label="17:00-18:00" width="115">
                  </el-table-column>
                  <el-table-column prop="state11" label="18:00-19:00" width="115">
                  </el-table-column>
                  <el-table-column prop="state12" label="19:00-20:00" width="115">
                  </el-table-column>
                  <el-table-column prop="state13" label="20:00-21:00">
                  </el-table-column>
                </el-table>
              </template>
            </el-tab-pane>
          </el-tabs>
        </el-tab-pane>
      </el-tabs>
    </el-tab-pane>
    </el-tabs>
    <div style="display:flex;margin-top: 3%;justify-content: space-evenly;" v-show="msgData.length>0?true:false">
      <span style="margin-top:6px;">你选中的是:</span>
      <el-input v-model="row" style="width:10%;" disabled></el-input>
      <el-input v-model="col" style="width:10%;" disabled></el-input>
      <el-input v-model="date" style="width:10%;" disabled></el-input>
      <el-input v-model="money" style="width:10%;" disabled></el-input>
      <el-button @click="submit">提交</el-button>
    </div>
  </div>
</template>

<script>
import { queryType, queryFieldByType, listTimeByDate, queryDate, orderField, queryMoneyByTimeId } from '@/request/api'
export default {
  name: "siteAppointment",
  data(){
    return {
      row: "",
      col: "",
      tid: "",
      fid: "",
      timeId:"",
      nameList:[],
      typeList:[],
      dateList:[],
      msgData: [],
      wholeData:[],
      money: "",
      date: "",
      name: "",
      description:"",
    }
  },
  created() {
    queryType().then(res => {
      this.nameList=res.data
    })
  },
  methods:{
    changeType (tab, event) {
      this.msgData = []
      this.description=this.typeList[tab.index].description
      this.fid = this.typeList[tab.index].fid
      queryDate({ tid: this.tid, fid: this.fid }).then(res => {
        this.dateList = res.data.date
      })
      listTimeByDate({ tid: this.tid, fid: this.fid, date: "" }).then(res => {
        console.log(res);
        this.wholeData=res.data
        res.data.fieldDateList.forEach((item,index) => {
          var obj = {}
          obj.name = res.data.name + index
          obj.state1 = item.timeArrangeList[0].status
          obj.state2 = item.timeArrangeList[1].status
          obj.state3 = item.timeArrangeList[2].status
          obj.state4 = item.timeArrangeList[3].status
          obj.state5 = item.timeArrangeList[4].status
          obj.state6 = item.timeArrangeList[5].status
          obj.state7 = item.timeArrangeList[6].status
          obj.state8 = item.timeArrangeList[7].status
          obj.state9 = item.timeArrangeList[8].status
          obj.state10 = item.timeArrangeList[9].status
          obj.state11 = item.timeArrangeList[10].status
          obj.state12 = item.timeArrangeList[11].status
          obj.state13 = item.timeArrangeList[12].status
          this.msgData.push(obj)
        });
      })
    },
    changeDate (tab, event) {
      this.msgData=[]
      listTimeByDate({ tid: this.tid, fid: this.fid, date: this.dateList[tab.index] }).then(res => {
        console.log(res);
        this.wholeData = res.data
        res.data.fieldDateList.forEach((item, index) => {
          var obj = {}

          obj.name = res.data.name + (parseInt(index) + 1)

          obj.state1 = item.timeArrangeList[0].status
          obj.state2 = item.timeArrangeList[1].status
          obj.state3 = item.timeArrangeList[2].status
          obj.state4 = item.timeArrangeList[3].status
          obj.state5 = item.timeArrangeList[4].status
          obj.state6 = item.timeArrangeList[5].status
          obj.state7 = item.timeArrangeList[6].status
          obj.state8 = item.timeArrangeList[7].status
          obj.state9 = item.timeArrangeList[8].status
          obj.state10 = item.timeArrangeList[9].status
          obj.state11 = item.timeArrangeList[10].status
          obj.state12 = item.timeArrangeList[11].status
          obj.state13 = item.timeArrangeList[12].status
          this.msgData.push(obj)
        });
      })
    },
    changeName (tab, event) {
      this.msgData = []
      this.tid=this.nameList[tab.index].tid
      queryFieldByType({ tid: this.tid}).then(res => {
        this.typeList=res.data
      })
    },
    tableCellClassName ({ row, column, rowIndex, columnIndex }) {
      row.index = rowIndex;
      column.index = columnIndex;
    },
    cellClick (row, column, cell, event) {
      if (this.wholeData.fieldDateList[row.index].timeArrangeList[column.index-1].status != "空闲") {
        this.$message.warning("场地"+this.wholeData.fieldDateList[row.index].timeArrangeList[column.index-1].status)
      } else {
        if (column.index > 0) {
          this.row = this.msgData[row.index].name;
          this.name=this.description+this.msgData[row.index].name
          this.timeId = this.wholeData.fieldDateList[row.index].timeArrangeList[column.index - 1].timeId
          this.date = this.wholeData.fieldDateList[row.index].timeArrangeList[column.index-1].startTime
            + "-" + this.wholeData.fieldDateList[row.index].timeArrangeList[column.index-1].endTime
          queryMoneyByTimeId({ timeId: this.timeId }).then(res => {
            console.log(res);
            this.money=res.data
          })
          switch (column.index) {
            case 1:
              this.col = this.msgData[row.index].state1
              break;
            case 2:
              this.col = this.msgData[row.index].state2
              break;
            case 3:
              this.col = this.msgData[row.index].state3
              break;
            case 4:
              this.col = this.msgData[row.index].state4
              break;
            case 5:
              this.col = this.msgData[row.index].state5
              break;
            case 6:
              this.col = this.msgData[row.index].state6
              break;
            case 7:
              this.col = this.msgData[row.index].state7
              break;
            case 8:
              this.col = this.msgData[row.index].state8
              break;
            case 9:
              this.col = this.msgData[row.index].state9
              break;
            case 10:
              this.col = this.msgData[row.index].state10
              break;
            case 11:
              this.col = this.msgData[row.index].state11
              break;
            case 12:
              this.col = this.msgData[row.index].state12
              break;
            case 13:
              this.col = this.msgData[row.index].state13
              break;
          }
        }
      }
    },
    submit () { 
      orderField({ timeId: this.timeId, name: this.name, money: this.money }).then(res => {
        this.money = ""
        this.row = ""
        this.col = ""
        this.date = ""
        this.$message.success(res.data+res.msg)
        setTimeout(() => {
          location.reload();
        }, 1000);
      })
    }
  }
}
</script>

<style scoped lang="less">

/deep/ *{
  overflow: hidden;
}
/deep/.cell{
  text-align: center;
  cursor: pointer;
}

</style>