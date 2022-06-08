<template>
  <div style="height:1000px;">
    <el-card class=" box-card" style="width:50%;margin:0 auto;margin-top: 3%;">
    <div slot="header" class="clearfix">
      <span>{{time}}</span>
    </div>
    {{msg}}
    </el-card>
  </div>
</template>

<script>
import { QueryNewAnn } from '@/request/api'
export default {
  name: "noticeManagement",
  data () { 
    return {
      time: "",
      msg:"",
    }
  },
  created () { 
    QueryNewAnn({ type: "场地" }).then(res => {
      this.time = res.data[0].createDate
      this.msg=res.data[0].content
    })
  },
}
</script>

<style scoped>

</style>