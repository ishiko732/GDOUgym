<!-- 学生预约 -->
<template>
  <div class="arrangeContainer">
    <el-tabs tab-position="top" @tab-click="changeName">
      <el-tab-pane :label="name" v-for="(name,index) in nameList">
        <el-tabs tab-position="top" @tab-click="changeType">
          <el-tab-pane :label="data" v-for="(data,dataindex) in typeList">
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
                    <el-table-column prop="state5" label="11:00-12:00" width="115">
                    </el-table-column>
                    <el-table-column prop="state6" label="12:00-13:00" width="115">
                    </el-table-column>
                    <el-table-column prop="state7" label="13:00-14:00" width="115">
                    </el-table-column>
                    <el-table-column prop="state8" label="14:00-15:00" width="115">
                    </el-table-column>
                    <el-table-column prop="state9" label="15:00-16:00" width="115">
                    </el-table-column>
                    <el-table-column prop="state10" label="16:00-17:00" width="115">
                    </el-table-column>
                    <el-table-column prop="state11" label="17:00-18:00" width="115">
                    </el-table-column>
                    <el-table-column prop="state12" label="18:00-19:00" width="115">
                    </el-table-column>
                    <el-table-column prop="state13" label="19:00-20:00" width="115">
                    </el-table-column>
                    <el-table-column prop="state4" label="20:00-21:00">
                    </el-table-column>
                  </el-table>
                </template>
              </el-tab-pane>
            </el-tabs>
          </el-tab-pane>
        </el-tabs>
      </el-tab-pane>
    </el-tabs>
    <div style="display:flex;margin-top: 3%;justify-content: space-evenly;">
      <span style="margin-top:6px;">你选中的是:</span>
      <el-input v-model="row" style="width:10%;" disabled></el-input>
      <el-input v-model="col" style="width:10%;" disabled></el-input>
      <el-button @click="submit">提交</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "siteAppointment",
  data(){
    return {
      row: "",
      col: "",
      nameList:["篮球","羽毛球"],
      typeList:[],
      dateList:[],
      msgData: [
        {
          name: '羽1',
          state1: "空闲1",
          state2: "空闲2",
          state3: "空闲3",
          state4: "空闲4",
          state5: "空闲5",
          state6: "空闲6",
          state7: "空闲7",
          state8: "空闲8",
          state9: "空闲9",
          state10: "空闲10",
          state11: "空闲11",
          state12: "空闲12",
          state13: "空闲13",
        },
        {
          name: '羽2',
          state1: "空闲1",
          state2: "空闲2",
          state3: "空闲3",
          state4: "空闲4",
          state5: "空闲5",
          state6: "空闲6",
          state7: "空闲7",
          state8: "空闲8",
          state9: "空闲9",
          state10: "空闲10",
          state11: "空闲11",
          state12: "空闲12",
          state13: "空闲13",
        },
        {
          name: '羽3',
          state1: "空闲1",
          state2: "空闲2",
          state3: "空闲3",
          state4: "空闲4",
          state5: "空闲5",
          state6: "空闲6",
          state7: "空闲7",
          state8: "空闲8",
          state9: "空闲9",
          state10: "空闲10",
          state11: "空闲11",
          state12: "空闲12",
          state13: "空闲13",
        }],
    }
  },
  created() {

  },
  methods:{
    changeType(tab,event){
      if(this.dateList.length==0){
        this.dateList.push("12.01","12.02");
      }
    },
    changeDate(tab,event){
      console.log(tab.label)
    },
    changeName(tab,event){
      if(this.typeList.length==0){
        this.typeList.push("有灯光","无灯光");
      }
    },
    tableCellClassName ({ row, column, rowIndex, columnIndex }) {
      row.index = rowIndex;
      column.index = columnIndex;
    },
    cellClick (row, column, cell, event) {
      if (column.index > 0) {
        this.row = this.msgData[row.index].name;
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