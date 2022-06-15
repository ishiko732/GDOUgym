<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import { GetNewToken } from "@/request/api";
export default {
  mounted(){
    GetNewToken().then(res=>{
      localStorage.setItem("token",res.data.new_token)
    }).catch(err=>{
      if(err.response.data.msg=="无权访问(Unauthorized)"){
        localStorage.clear()
        location.reload()
      }
    })
  }
}
</script>
<style lang="less">
*{
  margin: 0;
  padding: 0;
  overflow: auto;
}


</style>
