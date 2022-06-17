<template>
<div style="width: 1650px;">
  <div style="height: 2000px">
    <div class="first_container">
      <div class="title">体育馆器材查询</div>
      <div class="equipment_type">
        <span class="type">器材种类</span>
        <el-input v-model="equipment_type" placeholder="请输入器材种类"></el-input>
      </div>
      <div class="equipment_name">
        <span class="name">器材名称</span>
        <el-input v-model="equipment_name" placeholder="请输入器材名称"></el-input>
      </div>
      <el-button type="primary" @click="search" class="search">查询</el-button>
    </div>
    <div class="second_container">
      <div>
        <el-table
            :data="equipment_data"
            border
            style="width: 100%;">
          <el-table-column
              prop="type"
              label="器材种类"
              width="180">
          </el-table-column>
          <el-table-column
              prop="name"
              label="器材名称"
              width="180">
          </el-table-column>
          <el-table-column
              prop="number"
              label="器材数量"
              width="180">
          </el-table-column>
          <el-table-column
              prop="money"
              label="每小时器材租用费用(元)"
              width="180">
          </el-table-column>
          <el-table-column
              prop="time"
              label="器材可租用时间(小时)"
              width="150"
          >
          </el-table-column>
          <el-table-column
              v-if="roleId==1||roleId==5?true:false"
              label="收费编辑"
              width="150">
            <template slot-scope="scope">
              <i class="el-icon-edit" @click="edit(scope.row,scope.$index)"></i>
            </template>
          </el-table-column>
          <el-table-column
              v-if="roleId==1||roleId==5?true:false"
              label="器材报废"
              width="150">
            <template slot-scope="scope">
              <i class="el-icon-delete" @click="del(scope.row,scope.$index)"></i>
            </template>
          </el-table-column>
          <el-table-column
              label="器材回收"
              width="150">
            <template slot-scope="scope">
              <i class="el-icon-takeaway-box" @click="recycle(scope.row,scope.$index)"></i>
            </template>
          </el-table-column>
          <el-table-column
              label="器材维修"
              width="150" >
            <template slot-scope="scope">
              <i class="el-icon-setting" @click="fix(scope.row,scope.$index)"></i>
            </template>
          </el-table-column>
          <el-table-column
              prop="available_number"
              label="可租用器材数量"
          >
          </el-table-column>
        </el-table>
      </div>
      <div class="btns">
        <el-button type="primary"  round @click="recycleEquipment" v-show="roleId==1||roleId==5?true:false">回收审核</el-button>
        <el-button type="primary"  round @click="addEquipment" v-show="roleId==1||roleId==5?true:false">新增器材</el-button>
        <el-button type="primary"  round @click="fixEquipment" v-show="roleId==1||roleId==5?true:false">维修审核</el-button>
        <el-button type="primary"  round @click="rentEquipment">租用器材</el-button>
        <el-button type="primary"  round @click="returnEquipment">归还器材</el-button>
      </div>
    </div>
    <el-dialog
        title="提示"
        :visible.sync="del_dialogVisible"
        width="30%"
    >
      <div style="display: flex;">
        <span style="margin-top: 10px">器材报废:&nbsp;&nbsp;&nbsp;</span>
        <el-input v-model="del_number" style="width: 50%"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="del_dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="del_equip">确 定</el-button>
  </span>
    </el-dialog>
    <el-dialog
        title="器材回收"
        :visible.sync="recycle_dialogVisible"
        width="30%"
    >
      <div style="display: flex;">
        <span style="margin-top: 10px">器材回收:&nbsp;&nbsp;&nbsp;</span>
        <el-input v-model="recycle_number" style="width: 50%"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="recycle_dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="recycle_equip">确 定</el-button>
  </span>
    </el-dialog>
    <el-dialog
        title="器材维修"
        :visible.sync="fix_dialogVisible"
        width="30%"
    >
      <div style="display: flex;">
        <span style="margin-top: 10px">器材维修:&nbsp;&nbsp;&nbsp;</span>
        <el-input v-model="fix_number" style="width: 50%"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="fix_dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="fix_equip">确 定</el-button>
  </span>
    </el-dialog>
    <el-dialog
        title="更改收费标准"
        :visible.sync="edit_dialogVisible"
        width="30%"
    >
      <div style="display: flex;margin-bottom: 10px;margin-left: 100px;">
        <span style="margin-top: 10px">租用价格:&nbsp;&nbsp;&nbsp;</span>
        <el-input v-model="edit_price" style="width: 50%"></el-input>
      </div>
      <div style="display: flex;margin-bottom: 10px;margin-left: 100px;">
        <span style="margin-top: 10px">租用时间:&nbsp;&nbsp;&nbsp;</span>
        <el-input v-model="maxRentTime" style="width: 50%"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="edit_dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="edit_pricebtn">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</div>
</template>

<script>
import {applyRecycleEquipment, availableEquipmentCount, queryEquipment, reduceEquipmentCount
,applyFixEquipment,updateEquipment} from "@/request/api";

export default {
  name: "equipmentManagement",
  data(){
    return{
      edit_price:"",
      money:"",
      maxRentTime:"",
      eid:0,
      del_number:0,
      recycle_number:0,
      fix_number:0,
      edit_dialogVisible:false,
      fix_dialogVisible:false,
      recycle_dialogVisible:false,
      del_dialogVisible:false,
      roleId:0,
      equipment_type:"",
      equipment_name:"",
      equipment_number:"",
      wholeData:[],
      equipment_data: [],
    }
  },
  methods:{
    returnEquipment(){
      this.$router.push({path:"/home/returnEquipment"})
    },
    recycleEquipment(){
      this.$router.push({path:"/home/recycleEquipment"})
    },
    rentEquipment(){
      this.$router.push({path:"/home/rentEquipment"})
    },
    addEquipment(){
      this.$router.push({path:"/home/addEquipment"})
    },
    fixEquipment(){
      this.$router.push({path:"/home/fixEquipment"})
    },
    search(){
      this.equipment_data=[]
      queryEquipment({name:this.equipment_name,types:this.equipment_type,number:""}).then(res=>{
        res.data[0].forEach((item,index)=>{
          var obj={}
          obj.id=item.id
          obj.available_number=res.data[1][index]
          obj.type=item.types
          obj.number=item.number
          obj.name=item.name
          obj.time=item.maxRentTime
          obj.money=item.rentPrice
          this.equipment_data.push(obj)
        })
        // console.log(this.equipment_data)
      })
    },
    fix(a){
      if(a.number!=0){
        this.fix_dialogVisible=true
        this.eid=a.id
      }else{
        this.$message("数量为0，不能进行此操作")
      }
    },
    del(a){
      if(a.number!=0){
        this.del_dialogVisible=true
        this.eid=a.id
      }else{
        this.$message("数量为0，不能进行此操作")
      }

    },
    recycle(a){
      this.recycle_dialogVisible=true
      this.eid=a.id
    },
    del_equip(){
      reduceEquipmentCount({eid:this.eid,number:this.del_number}).then(res=>{
        // console.log(res)
        this.$message(res.msg)
        setTimeout(()=>{
          location.reload();
        },1000)
      })
      this.del_dialogVisible=false
    },
    recycle_equip(){
      applyRecycleEquipment({eid:this.eid,number:parseInt(this.recycle_number)}).then(res=>{
        // console.log(res)
        this.$message(res.msg)
        setTimeout(()=>{
          location.reload();
        },1000)
      })
      this.recycle_dialogVisible=false
    },
    fix_equip(){
      applyFixEquipment({eid:this.eid,number:this.fix_number}).then(res=>{
        // console.log(res)
        this.$message(res.msg)
        setTimeout(()=>{
          location.reload();
        },1000)
      })
      this.fix_dialogVisible=false
    },
    edit(a,b){
      if(a.number!=0){
        this.edit_dialogVisible=true
        this.eid=a.id
      }else{
        this.$message("数量为0，不能进行此操作")
      }
    },
    edit_pricebtn(){
      console.log(this.eid,this.edit_price);
        updateEquipment({eid:this.eid,rentPrice:this.edit_price,maxRentTime:this.maxRentTime}).then(res=>{
          this.$message(res.msg)
          setTimeout(()=>{
          location.reload();
        },1000)
      })
    }
  },
  created() {
    this.roleId=localStorage.getItem("roleId")
      queryEquipment({name:"",types:"",number:""}).then(res=>{
      this.wholeData=res.data[0]
      // console.log(this.wholeData)
      res.data[0].forEach((item,index)=>{
        var obj={}
        obj.id=item.id
        obj.available_number=res.data[1][index]
        obj.type=item.types
        obj.number=item.number
        obj.name=item.name
        obj.time=item.maxRentTime
        obj.money=item.rentPrice
        this.equipment_data.push(obj)

      })
        // console.log(this.equipment_data)
    })
  }
}
</script>

<style scoped lang="less">
.first_container{
  width: 50%;
  margin: 0 auto;
  margin-top: 2%;
  .title{
    font-size: 30px;
    text-align: center;
  }
  .equipment_type,.equipment_name{
    margin-top: 4%;
    margin-bottom: 2%;
    font-size: 18px;
    margin-left: 35%;
    width: 60%;
    display: flex;
    .el-input{
      width: 40%;
      margin-left: 20px;
    }
    .type,.name{
      line-height: 37px;
    }
  }
  .search{
    margin-top: 15px;
    margin-bottom: 15px;
    margin-left: 37%;
    width: 30%;
  }
}
.second_container{
  width: 95%;
  margin-left: 230px;
  margin: 0 auto;
  margin-top: 10px;
  /deep/.cell{
    text-align: center;
  }
  .btns{
    margin-top: 15px;
    margin-bottom: 10px;
    margin-left: 60%;
    display: flex;
  }
}
i{
  cursor: pointer;
  margin-left: 17px;
}
</style>
