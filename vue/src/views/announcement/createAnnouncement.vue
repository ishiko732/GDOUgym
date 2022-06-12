<template>
<!-- 创建公告 -->
    <div style="height:800px">
        <div class="form">
            <h2>创建公告</h2>
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
                <el-form-item label="公告内容：" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model.trim="content" autocomplete="off"></el-input>
                </el-form-item>
                
                <div class="button">
                    <el-button @click="clear">清空</el-button>
                    <el-button type="primary" @click="createAnn">确 定</el-button>
                </div>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { CreateAnnouncement } from "@/request/api";
export default {
    data () {
        return {
            content:"",
            type:"",
            formLabelWidth: '150px',
            options: [{
                value: '馆内罚款',
                label: '馆内罚款'
            },{
                value: '场馆',
                label: '场馆'
            },{
                value: '场地',
                label: '场地'
            }],
 
        }
    },
    methods:{
        createAnn(){
            if(this.content==""){
                this.$message.warning("当前公告内容为空")
                this.clear()
            }else if(this.type==""){
                this.$message.warning("请选择公告类型")
            }else{
                CreateAnnouncement({
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
    }
</style>