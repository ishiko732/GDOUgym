<template>
<!-- 导入信息 -->
    <div>
        导入信息
        <button @click="add">导入</button>
    </div>
</template>

<script>
import { ExportUser } from "@/request/api";
export default {
    data () {
        return {

 
        }
    },
    methods:{
        add(){
            ExportUser({
                id:"201911211418",
                sex:"男"
            }).then(res=>{
                console.log(res);
            })
        }
    }
    
}
</script>
 
<style lang = "less" scoped>
    
</style>