<template>
<!-- 查询最新公告 -->
    <div style="height:800px">
        <div class="form">
            <h2>查询最新公告</h2>
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
                    <el-button type="primary" @click="queryNewAnn">查 询</el-button>
                </el-form-item>
                
                <el-form-item label="查询的最新公告内容：" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model.trim="content" autocomplete="off" disabled></el-input>
                </el-form-item>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { QueryNewAnn,QueryAnnType } from "@/request/api";
export default {
    data () {
        return {
            content:"",
            type:"",
            formLabelWidth: '200px',
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
        queryNewAnn(){
            QueryNewAnn({type:this.type}).then(res=>{
                console.log(res);
                this.content = res.data[0].content
            }).catch(err=>{
                // console.log("err:",err.response.data);
                this.$message.error(err.response.data.data+"，请重新登录")
            })
        }
    }
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
        /deep/.el-textarea__inner{
            width: 600px;
            height: 300px;
            font-size: 20px;
        }
        .el-button--primary{
            margin-left: 50px;
            transform: translateY(-15px);
        }
    }
</style>