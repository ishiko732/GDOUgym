<template>
<div>
  <div class="first_container">
    <div class="title">体育馆器材查询>>></div>
    <div class="equipment_type">
      <span class="type">器材种类</span>
      <el-input v-model="equipment_type" placeholder="请输入内容"></el-input>
    </div>
    <div class="equipment_name">
      <span class="name">器材名称</span>
      <el-input v-model="equipment_name" placeholder="请输入内容"></el-input>
    </div>
    <div class="equipment_number">
      <span class="number">器材数量</span>
      <el-input v-model="equipment_number" placeholder="请输入内容"></el-input>
    </div>
  </div>
  <div class="second_container">
    <el-table
        :data="equipment_data"
        border
        style="width: 100%">
      <el-table-column
          prop="type"
          label="器材种类"
          width="240">
      </el-table-column>
      <el-table-column
          prop="name"
          label="器材名称"
          width="240">
      </el-table-column>
      <el-table-column
          prop="number"
          label="器材数量"
          width="240">
      </el-table-column>
      <el-table-column
          prop="available_number"
          label="可用器材数量"
          width="240">
      </el-table-column>
      <el-table-column
          prop="returned_number"
          label="已归还器材数量"
          width="240">
      </el-table-column>
      <el-table-column
          prop="noreturn_number"
          label="未归还器材数量"
          >
      </el-table-column>
    </el-table>
    <div class="btns">
      <el-button type="primary" round @click="price">租用收费</el-button>
      <el-button type="primary" round @click="recycleEquipment">回收器材</el-button>
      <el-button type="primary" round @click="addEquipment">新增器材</el-button>
      <el-button type="primary" round @click="fixEquipment">维修器材</el-button>
      <el-button type="primary" round @click="deleteEquipment">删除器材</el-button>
      <el-button type="primary" round @click="rentEquipment">租用器材</el-button>
    </div>
  </div>
</div>
</template>

<script>
export default {
  name: "equipmentManagement",
  data(){
    return{
      equipment_type:"",
      equipment_name:"",
      equipment_number:"",
      equipment_data: [{
        type: '2016-05-02',
        name: '王小虎',
        number: '上海市普陀区金沙江路 1518 弄',
        available_number:"1",
        returned_number:"2",
        noreturn_number:"3"
      }]
    }
  },
  methods:{
    recycleEquipment(){

    },
    rentEquipment(){

    },
    addEquipment(){

    },
    deleteEquipment(){

    },
    fixEquipment(){

    },
    price(){

    },
  }
}
</script>

<style scoped lang="less">
.first_container{
  border: 1px solid dodgerblue;
  width: 50%;
  margin: 0 auto;
  margin-top: 50px;
  .title{
    font-size: 30px;
    text-align: center;
  }
  .equipment_type,.equipment_name,.equipment_number{
    margin-top: 20px;
    font-size: 18px;
    margin-left: 35%;
    width: 60%;
    display: flex;
    .el-input{
      width: 30%;
      margin-left: 20px;
    }
    .type,.number,.name{
      line-height: 37px;
    }
  }
}
.second_container{
  border: 1px solid greenyellow;
  width: 80%;
  margin: 0 auto;
  .el-table-column,.el-table{
    text-align: center;
  }
  .btns{
    margin-left: 25%;
    margin-top: 10px;
    margin-bottom: 10px;
    display: flex;
  }
}

</style>