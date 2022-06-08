<template>
  <div class="messageContainer" style="height:1000px">
    <el-button type="text" @click="dialogVisible=true">新增场地</el-button>
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
            <i @click="edit(scope.$index, msgData)" type="text"  class="el-icon-edit" size="small">
            </i>
          </template>
        </el-table-column>
        <el-table-column label="删除">
          <template slot-scope="scope">
            <i @click="del(scope.$index, msgData)" class="el-icon-delete" type="text" size="small">
            </i>
          </template>
        </el-table-column>
      </el-table>
    </template>
    <el-dialog
        title="新增场地"
        :visible.sync="dialogVisible"
        width="30%">
      <div class="input">
        <span style="margin-top: 10px;">场地类型:&nbsp;&nbsp;</span>
        <el-input v-model="description" maxlength="10" style="width: 60%"></el-input>
      </div>
      <div class="input">
        <span style="margin-top: 10px;">场地金额:&nbsp;&nbsp;</span>
        <el-input v-model="money" style="width: 60%"></el-input>
      </div>
      <div class="input">
        <span style="margin-top: 10px;">场地数量:&nbsp;&nbsp;</span>
        <el-input v-model="num" style="width: 60%"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="addfield">确定</el-button>
              </span>
    </el-dialog>
    <el-dialog
        title="编辑场地"
        :visible.sync="edit_dialogVisible"
        width="30%">
      <div class="input">
        <span style="margin-top: 10px;">场地类型:&nbsp;&nbsp;</span>
        <el-input v-model="edit_description" maxlength="10" style="width: 60%" disabled></el-input>
      </div>
      <div class="input">
        <span style="margin-top: 10px;">场地金额:&nbsp;&nbsp;</span>
        <el-input v-model="edit_money" style="width: 60%"></el-input>
      </div>
      <div class="input">
        <span style="margin-top: 10px;">场地数量:&nbsp;&nbsp;</span>
        <el-input v-model="edit_num" style="width: 60%"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="updatefield">确定</el-button>
              </span>
    </el-dialog>
  </div>
</template>

<script>
import { queryFieldByType ,addField,updateField,deleteField} from '@/request/api'
export default {
  data () { 
    return {
      tid: 0,
      dialogVisible:false,
      edit_dialogVisible:false,
      msgData:[],
      money:0,
      num:0,
      description:"",
      edit_money:0,
      edit_num:0,
      edit_description:"",
      fid:0,
    }
  },
  created () { 
    this.tid = this.$route.query.tid
    console.log(this.tid);
    queryFieldByType({ tid: this.tid }).then(res => {
      console.log(res);
      this.msgData=res.data
    })
  },
  methods: {
    del (a,b) {
      this.fid=b[a].fid
      deleteField({fid:this.fid}).then(res=>{
        this.$message(res.msg)
      })
    },
    edit (a,b) {
      this.edit_dialogVisible=true
      this.fid=b[a].fid
      this.edit_description=b[a].description
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    addfield(){
      console.log(this.tid,this.money,this.description,this.num)
      addField({tid:this.tid,money:this.money,description:this.description,num:this.num})
      .then(res=>{
        this.$message(res.msg)
      })
      this.dialogVisible=false
    },
    updatefield(){
      updateField({
        money:this.edit_money,
        description:this.edit_description,
        num:this.edit_num,
        fid:this.fid
      }).then(res=>{
        console.log(res)
      })
      this.edit_dialogVisible=false
    }
  },
}
</script>

<style scoped lang="less">
.messageContainer{
  width: 80%;
  margin: 0 auto;
  margin-top: 3%;
}
.input{
  display: flex;
  margin-bottom: 10px;
  margin-left: 50px;
}
i{
  cursor: pointer;
}
</style>