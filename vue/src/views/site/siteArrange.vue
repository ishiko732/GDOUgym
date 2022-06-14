<!-- 学生预约 -->
<template>
  <div class="arrangeContainer" style="height: 1000px">
    <el-tabs tab-position="top" @tab-click="changeType">
      <el-tab-pane :label="data.description" v-for="(data, dataindex) in typeList">
        <el-tabs tab-position="top" style="height: 100%;" @tab-click="changeDate">
          <el-tab-pane :label="date" v-for="(date, dateindex) in dateList">
            <template>
              <el-table :data="msgData" style="width: 100%;" :cell-class-name="tableCellClassName"
                @cell-click="cellClick" v-show="msgData.length>0?true:false">
                <el-table-column prop="name" label="" width="115">
                </el-table-column>
                <el-table-column  label="8:00-9:00" width="115">
                  <template slot-scope="scope">
                    <el-input  v-model="msgData[scope.$index].state1"></el-input>
                  </template>
                </el-table-column>
                <el-table-column  label="9:00-10:00" width="115">
                  <template slot-scope="scope">
                    <el-input  v-model="msgData[scope.$index].state2"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="10:00-11:00" width="115">
                  <template slot-scope="scope">
                    <el-input  v-model="msgData[scope.$index].state3"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="11:00-12:00" width="115">
                  <template slot-scope="scope">
                    <el-input  v-model="msgData[scope.$index].state4"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="12:00-13:00" width="115">
                  <template slot-scope="scope">
                    <el-input  v-model="msgData[scope.$index].state5"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="13:00-14:00" width="115">
                  <template slot-scope="scope">
                    <el-input  v-model="msgData[scope.$index].state6"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="14:00-15:00" width="115">
                  <template slot-scope="scope">
                    <el-input  v-model="msgData[scope.$index].state7"></el-input>
                  </template>
                </el-table-column>
                <el-table-column  label="15:00-16:00" width="115">
                  <template slot-scope="scope">
                    <el-input  v-model="msgData[scope.$index].state8"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="16:00-17:00" width="115">
                  <template slot-scope="scope">
                    <el-input  v-model="msgData[scope.$index].state9"></el-input>
                  </template>
                </el-table-column>
                <el-table-column  label="17:00-18:00" width="115">
                  <template slot-scope="scope">
                    <el-input  v-model="msgData[scope.$index].state10"></el-input>
                  </template>
                </el-table-column>
                <el-table-column  label="18:00-19:00" width="115">
                  <template slot-scope="scope">
                    <el-input  v-model="msgData[scope.$index].state11"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="19:00-20:00" width="115">
                  <template slot-scope="scope">
                    <el-input  v-model="msgData[scope.$index].state12"></el-input>
                  </template>
                </el-table-column>
                <el-table-column  label="20:00-21:00">
                  <template slot-scope="scope">
                    <el-input  v-model="msgData[scope.$index].state13"></el-input>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </el-tab-pane>
        </el-tabs>
      </el-tab-pane>
    </el-tabs>
    <div style="display:flex;margin-top: 3%;justify-content: space-evenly;" v-show="msgData.length>0?true:false">
      <el-button @click="submit">提交</el-button>
    </div>
  </div>
</template>

<script>
import {
  queryType,
  queryFieldByType,
  listTimeByDate,
  queryDate,
  orderField,
  updateStatus,
  updateStatusById
} from '@/request/api'
export default {
  name: "siteAppointment",
  data () {
    return {
      row: "",
      col: "",
      tid: "",
      fid: "",
      timeId: "",
      typeList: [],
      dateList: [],
      msgData: [],
      wholeData: [],
      money: "",
      date: "",
      name: "",
      description: "",
      requestList:[],
    }
  },
  created () {
    this.tid=this.$route.query.tid
    queryFieldByType({ tid: this.tid }).then(res => {
      this.typeList = res.data
    })
  },
  methods: {
    changeType (tab, event) {
      this.msgData = []
      this.description = this.typeList[tab.index].description
      this.fid = this.typeList[tab.index].fid
      queryDate({ tid: this.tid, fid: this.fid }).then(res => {
        this.dateList = res.data.date
      })
    },
    changeDate (tab, event) {
      this.msgData = []
      listTimeByDate({ tid: this.tid, fid: this.fid, date: this.dateList[tab.index] }).then(res => {
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
          obj.timeId1 = item.timeArrangeList[0].timeId
          obj.timeId2 = item.timeArrangeList[1].timeId
          obj.timeId3 = item.timeArrangeList[2].timeId
          obj.timeId4 = item.timeArrangeList[3].timeId
          obj.timeId5 = item.timeArrangeList[4].timeId
          obj.timeId6 = item.timeArrangeList[5].timeId
          obj.timeId7 = item.timeArrangeList[6].timeId
          obj.timeId8 = item.timeArrangeList[7].timeId
          obj.timeId9 = item.timeArrangeList[8].timeId
          obj.timeId10 = item.timeArrangeList[9].timeId
          obj.timeId11 = item.timeArrangeList[10].timeId
          obj.timeId12 = item.timeArrangeList[11].timeId
          obj.timeId13 = item.timeArrangeList[12].timeId
          this.msgData.push(obj)
        });
        console.log(this.msgData)
      })
    },
    tableCellClassName ({ row, column, rowIndex, columnIndex }) {
      row.index = rowIndex;
      column.index = columnIndex;
    },
    cellClick (row, column, cell, event) {
       if (column.index > 0) {
          this.row = this.msgData[row.index].name;
          this.name = this.description + this.msgData[row.index].name
          this.timeId = this.wholeData.fieldDateList[row.index].timeArrangeList[column.index - 1].timeId
          this.date = this.wholeData.fieldDateList[row.index].timeArrangeList[column.index - 1].startTime
            + "-" + this.wholeData.fieldDateList[row.index].timeArrangeList[column.index - 1].endTime
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
      
    },
    submit () {
      this.requestList=[]
      this.msgData.forEach((item,index)=>{
        console.log(item)
        let obj1={}
        obj1.timeId=item.timeId1
        obj1.status=item.state1
        this.requestList.push(obj1)
        let obj2={}
        obj2.timeId=item.timeId2
        obj2.status=item.state2
        this.requestList.push(obj2)
        let obj3={}
        obj3.timeId=item.timeId3
        obj3.status=item.state3
        this.requestList.push(obj3)
        let obj4={}
        obj4.timeId=item.timeId4
        obj4.status=item.state4
        this.requestList.push(obj4)
        let obj5={}
        obj5.timeId=item.timeId5
        obj5.status=item.state5
        this.requestList.push(obj5)
        let obj6={}
        obj6.timeId=item.timeId6
        obj6.status=item.state6
        this.requestList.push(obj6)
        let obj7={}
        obj7.timeId=item.timeId7
        obj7.status=item.state7
        this.requestList.push(obj7)
        let obj8={}
        obj8.timeId=item.timeId8
        obj8.status=item.state8
        this.requestList.push(obj8)
        let obj9={}
        obj9.timeId=item.timeId9
        obj9.status=item.state9
        this.requestList.push(obj9)
        let obj10={}
        obj10.timeId=item.timeId10
        obj10.status=item.state10
        this.requestList.push(obj10)
        let obj11={}
        obj11.timeId=item.timeId11
        obj11.status=item.state11
        this.requestList.push(obj11)
        let obj12={}
        obj12.timeId=item.timeId12
        obj12.status=item.state12
        this.requestList.push(obj12)
        let obj13={}
        obj13.timeId=item.timeId13
        obj13.status=item.state13
        this.requestList.push(obj13)
      })
      var obj={}
      obj.timeArrangeList=this.requestList
      // console.log(JSON.stringify(obj))
      // console.log(obj)
      updateStatusById(obj).then(res=>{
        // console.log(res)
        this.$message(res.msg)
      }).catch(e => {
        console.log(e)
      })
    }
  }
}
</script>

<style scoped lang="less">
/deep/ * {
  overflow: hidden;
}

/deep/.cell {
  text-align: center;
  cursor: pointer;
}
/deep/.el-input{
  text-align: center;
}
/deep/.el-input__inner{
  border: 0;
}
</style>