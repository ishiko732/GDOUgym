<template>
<!-- 修改公告历史记录 -->
    <div style="height:800px">
        <div class="form">
            <h2>修改公告历史记录</h2>
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
                    <el-button type="primary" @click="queryAnnLogs">查 询</el-button>
                    <el-button type="primary" @click="updateAnn">修 改</el-button>
                </el-form-item>
                
                <el-form-item label="查询的公告历史记录：" :label-width="formLabelWidth">
                    <el-table
                        :data="AnnLogsList"
                        height="200"
                        border
                        style="width: 100%">
                        <el-table-column
                        prop="aid"
                        label="id"
                        width="50">
                        </el-table-column>
                        <el-table-column
                        prop="type"
                        label="公告类型"
                        width="100">
                        </el-table-column>
                        <el-table-column
                        prop="createDate"
                        label="创建时间"
                        width="180">
                        </el-table-column>
                        <el-table-column
                        prop="content"
                        label="公告内容">
                        </el-table-column>
                    </el-table>
                </el-form-item>
                <el-form-item label="需要修改的公告的id：" :label-width="formLabelWidth">
                    <el-input v-model.trim="id" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="修改后的公告内容：" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model.trim="content" autocomplete="off"></el-input>
                </el-form-item>
                
            </el-form>
        </div>
    </div>
</template>

<script>

import { QueryAnnLogs,QueryAnnType,UpdateAnnouncement } from "@/request/api";
export default {
    data () {
        return {
            id:"",
            content:"",
            AnnLogsList:[],
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
        queryAnnLogs(){
            QueryAnnLogs({type:this.type}).then(res=>{
                // console.log(res);
                if(res.code==200){
                    this.AnnLogsList = res.data
                }else{
                    this.$message.warning(res.msg)
                }
            }).catch(err=>{
                // console.log("err:",err.response.data);
                this.$message.error(err.response.data.data+"，请重新登录")
            })
        },
        updateAnn(){
            if(this.id=="" || this.content==""){
                this.$message.warning("公告id或者公告内容为空")
            }else{
                UpdateAnnouncement({
                    aid:this.id,
                    content:this.content
                }).then(res=>{
                    // console.log(res);
                    if(res.code=="200"){
                        if(res.msg=="修改公告成功"){
                            this.$message.success(res.msg)
                        }else{
                            this.$message.warning("所输入的公告ID 不存在")
                        }
                        
                        this.queryAnnLogs()
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
            this.id=""
            this.content=""
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
            height: 100px;
            font-size: 20px;
        }
        .el-button--primary{
            margin-left: 50px;
            transform: translateY(-15px);
        }
    }
</style>