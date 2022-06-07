<template>
<!-- 导入信息 -->
    <div>
        导入信息
        <input type="file" name="myfile" id="myfile" ref="file" accept=".xlsx">
        <button @click="add">导入</button>
    </div>
</template>

<script>
import { ExportUser } from "@/request/api";
export default {
    data () {
        return {
            file:""
 
        }
    },
    methods:{
        add(){
            ExportUser({
                file:this.file
                
            }).then(res=>{
                console.log(res);
            })
        },
        
    }
    
}
</script>
 
<style lang = "less" scoped>
    
</style>