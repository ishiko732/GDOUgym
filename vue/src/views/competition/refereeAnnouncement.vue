<template>
<!-- 裁判简介公告 -->
    <div style="height:800px">
        <div class="form">
            <h2>裁判简介公告(赛事绑定裁判)</h2>
            <el-form>
                <el-form-item label="场地id：" :label-width="formLabelWidth">
                    <el-select v-model="cfid" placeholder="请选择">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="用户id：" :label-width="formLabelWidth">
                    <el-input v-model.trim="uid" autocomplete="off" placeholder="请输入用户id"></el-input>
                </el-form-item>
                <el-form-item label="裁判公告：" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model.trim="context" autocomplete="off"></el-input>
                </el-form-item>
                
                <div class="button">
                    <el-button @click="clear">清 空</el-button>
                    <el-button type="primary" @click="arrange">确 定</el-button>
                </div>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { ArrangeComReferee,QueryComField } from "@/request/api";
export default {
    data () {
        return {
            context:"",
            uid:"",
            cfid:"",
            formLabelWidth: '150px',
            options:[]
        }
    },
    created(){
        QueryComField().then(res=>{
            for(let i in res.data){
                this.options.push({
                    value:res.data[i].id,
                    label:res.data[i].id
                })
            }
        })
    },
    methods:{
        arrange(){
            if(this.cfid==""||this.uid==""||this.context==""){
                this.$message.warning("有内容为空")
            }else{
                ArrangeComReferee({
                    cfId:this.cfid,
                    uid:this.uid,
                    context:this.context
                }).then(res=>{
                    console.log(res);
                    this.$message.success(res.msg)
                    this.clear()
                }).catch(err=>{
                    this.$message.error("输入的用户id不存在")
                })
            }
        },
        clear(){
            this.uid=""
            this.cfid=""
            this.context=""
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
        /deep/.el-form-item__label{
            width: 150px;
        }
        /deep/.el-input__inner,.el-input{
            width: 200px;
        }
    }
</style>