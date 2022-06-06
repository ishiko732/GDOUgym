<template>
  <div class="arrangeContainer">
      <el-tabs tab-position="top" @tab-click="changeType">
        <el-tab-pane :label="data" v-for="(data,dataindex) in typeList">
          <el-tabs tab-position="top" style="height: 100%;" @tab-click="changeDate">
            <el-tab-pane :label="date" v-for="(date,dateindex) in dateList">
              <template>
                <el-table
                    :data="msgData"
                    style="width: 100%;height: 500px;">
                  <el-table-column
                      prop="name"
                      label=""
                      width="115">
                  </el-table-column>
                  <el-table-column
                      prop="state1"
                      label="8:00-9:00"
                      width="115">
                  </el-table-column>
                  <el-table-column
                      prop="state2"
                      label="9:00-10:00"
                      width="115">
                  </el-table-column>
                  <el-table-column
                      prop="state3"
                      label="10:00-11:00"
                      width="115">
                  </el-table-column>
                  <el-table-column
                      prop="state5"
                      label="11:00-12:00"
                      width="115">
                  </el-table-column>
                  <el-table-column
                      prop="state6"
                      label="12:00-13:00"
                      width="115">
                  </el-table-column>
                  <el-table-column
                      prop="state7"
                      label="13:00-14:00"
                      width="115">
                  </el-table-column>
                  <el-table-column
                      prop="state8"
                      label="14:00-15:00"
                      width="115">
                  </el-table-column>
                  <el-table-column
                      prop="state9"
                      label="15:00-16:00"
                      width="115">
                  </el-table-column>
                  <el-table-column
                      prop="state10"
                      label="16:00-17:00"
                      width="115">
                  </el-table-column>
                  <el-table-column
                      prop="state11"
                      label="17:00-18:00"
                      width="115">
                  </el-table-column>
                  <el-table-column
                      prop="state12"
                      label="18:00-19:00"
                      width="115">
                  </el-table-column>
                  <el-table-column
                      prop="state13"
                      label="19:00-20:00"
                      width="115"
                      >
                  </el-table-column>
                  <el-table-column
                      prop="state4"
                      label="20:00-21:00"
                      >
                  </el-table-column>
                </el-table>
              </template>
            </el-tab-pane>
          </el-tabs>
        </el-tab-pane>
      </el-tabs>
  </div>
</template>

<script>
export default {
  name: "siteArrange",
  data(){
    return{
      typeList:["羽毛球","篮球"],
      dateList:["12.01","12.02"],
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
    console.log(this.$route.query.id)
  },
  methods:{
    changeType(tab,event){
      console.log(tab.label)
    },
    changeDate(tab,event){
      console.log(tab.label)
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
}
</style>