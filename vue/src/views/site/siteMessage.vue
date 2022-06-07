<template>
  <div class="messageContainer">
    <template>
      <el-table :data="msgData" border style="width: 100%">
        <el-table-column prop="fid" label="ID" width="180">
        </el-table-column>
        <el-table-column prop="description" label="场地名称" width="220">
        </el-table-column>
        <el-table-column prop="num" label="数量" width="180">
        </el-table-column>
        <el-table-column prop="money" label="费用/小时" width="220">
        </el-table-column>
        <el-table-column label="编辑" width="180">
          <template slot-scope="scope">
            <el-button @clickt="edit(scope.$index, tableData)" type="text" size="small">
              <i class="el-icon-edit"></i>
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="删除">
          <template slot-scope="scope">
            <el-button @clickt="del(scope.$index, tableData)" type="text" size="small">
              <i class="el-icon-delete"></i>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>
  </div>
</template>

<script>
import {queryFieldType} from '@/request/api'
export default {
  data () { 
    return {
      tid: 0,
      msgData:[]
    }
  },
  created () { 
    this.tid = this.$route.query.tid
    queryFieldType({ tid: this.tid }).then(res => {
      this.msgData=res.data
    })
  },
  methods: {
    del () { 

    },
    edit () {
      
    },
  },
}
</script>

<style scoped lang="less">
.messageContainer{
  width: 80%;
  margin: 0 auto;
  margin-top: 3%;
}
</style>