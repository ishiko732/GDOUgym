<template>
  <div class="container" style="height: 1000px;">
    <div class="title">删除器材</div>
    <div class="type_div">
      <span class="type">器材种类</span>
      <el-input v-model="type" placeholder="请输入器材种类"></el-input>
    </div>
    <div class="name_div">
      <span class="name">器材名称</span>
      <el-input v-model="name" placeholder="请输入器材名称"></el-input>
    </div>
    <div class="number_div">
      <span class="number">器材数量</span>
      <el-input v-model="number" placeholder="请输入器材数量"></el-input>
    </div>
    <div class="btn">
      <el-button type="success" plain @click="del">删除</el-button>
      <el-button type="success" plain @click="cancel">取消</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "addEquipment",
  data(){
    return{
      type:"",
      number:"",
      name:"",
    }
  },
  methods:{
    cancel(){
      this.$router.go(-1)
    },
    del(){},
  }
}
</script>

<style scoped lang="less">
.container{
  margin: 0 auto;
  width: 600px;
  height: 500px;
  margin-top: 5%;
  .title{
    font-size: 30px;
    text-align: center;
    margin-top: 40px;
  }
  .type_div,.name_div,.number_div{
    margin-top: 60px;
    font-size: 18px;
    margin-left: 30%;
    width: 60%;
    display: flex;
    .el-input{
      width: 50%;
      margin-left: 20px;
    }
    .type,.number,name{
      line-height: 37px;
    }
  }
  .btn{
    margin-top: 40px;
    margin-left: 19%;
    display: flex;
    .el-button{
      margin-left: 20%;
    }
  }

}
</style>