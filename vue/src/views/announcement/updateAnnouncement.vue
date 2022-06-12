<template>
<!-- 修改公告 -->
    <div style="height:800px">
        <div class="form">
            <h2>修改公告</h2>
            <el-form>
                <el-form-item label="公告类型：" :label-width="formLabelWidth">
                    <el-select v-model="type" placeholder="请选择">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="修改后的公告内容：" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model.trim="content" autocomplete="off"></el-input>
                </el-form-item>
                
                <div class="button">
                    <el-button @click="clear">清空</el-button>
                    <el-button type="primary" @click="updateAnn">确 定</el-button>
                </div>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { UpdateAnnouncement,QueryAnnType } from "@/request/api";
export default {
    data () {
        return {
            content:"",
            type:"",
            formLabelWidth: '150px',
            options: [],
 
        }
    },
    created(){
        QueryAnnType().then(res=>{
            for(var i in res.data){
                this.options.push({
                    value:res.data[i],
                    label:res.data[i]
                })
            }
        }).catch(err=>{
            // console.log("err:",err.response.data);
            this.$message.error(err.response.data.data+"，请重新登录")
        })
    },
    methods:{
        updateAnn(){
            if(this.content==""){
                this.$message.warning("当前公告内容为空")
                this.clear()
            }else if(this.type==""){
                this.$message.warning("请选择公告类型")
            }else{
                UpdateAnnouncement({
                    content:this.content,
                    type:this.type
                }).then(res=>{
                    console.log(res);
                    if(res.code=="200"){
                        this.$message.success(res.msg)
                    }else if(res.code=="401"){
                        this.$message.error(res.msg)
                    }
                    this.clear()
                }).catch(err=>{
                    // console.log("err:",err.response.data);
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
            }
        },
        clear(){
            this.content = ""
            this.type = ""
        }
    },
    
}
</script>
 
<style lang = "less" scoped>
    .form{
        width: 800px;
        margin: 50px 300px;
        h2{
            text-align: center;
            margin-bottom: 50px;
            margin-left: 80px;
        }
        .button{
            padding-left: 50px;
            text-align: center;
            .el-button{
                margin: 0 40px;
            }
        }
        /deep/.el-textarea__inner{
            width: 600px;
            height: 300px;
            font-size: 20px;
        }
        /deep/.el-form-item__label{
            width: 150px;
        }
    }
</style>