<template>
<!-- 赛事取消 -->
    <div style="height:800px">
        <div class="form">
            <h2>取消赛事</h2>
            <el-form>
                <el-form-item label="赛事：" :label-width="formLabelWidth"  style="margin-top:20px">
                    <el-select v-model="cid" placeholder="请选择赛事" filterable>
                    <el-option
                        v-for="item in com_options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="取消原因：" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model.trim="context" autocomplete="off" placeholder="请输入取消原因"></el-input>
                </el-form-item>
                <div class="button">
                    <el-button @click="clear">清空</el-button>
                    <el-button type="primary" @click="cancel">确 定</el-button>
                </div>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { CancelEvent,QueryEvent } from "@/request/api";
export default {
    data () {
        return {
            formLabelWidth: '150px',
            cid:"",
            context:"",
            com_options:[]
        }
    },
    created(){
        QueryEvent().then(res=>{
            res.data.forEach(item=>{
                if(item.isCancel==false && (item.isCheck=="审核通过"||item.isCheck=="待审核")){
                    this.com_options.push({
                        value:item.id,
                        label:item.name+" "+item.competitionTime
                    })
                }
            })
        })
    },
    methods:{
        cancel(){
            if(this.cid==""||this.context==""){
                this.$message.warning("未选择赛事或者取消原因为空")
            }else{
                CancelEvent({
                    cid:this.cid,
                    context:this.context
                }).then(res=>{
                    // console.log(res);
                    if(res.msg=="取消成功"){
                        this.$message.success(res.msg)
                        this.clear()
                    }else if(res.msg=="取消失败"){
                        this.$message.warning(res.msg+"，该赛事已经取消")
                    }else{
                        this.$message.warning(res.msg)
                    }
                }).catch(err=>{
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
            }
        },
        clear(){
            this.cid=""
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
            width: 300px;
        }
    }
</style>