<template>
    <div>
        <div class="form">
            <h2>更新管理员</h2>
            <el-form>
                <el-form-item label="用户id：" :label-width="formLabelWidth">
                    <el-input v-model.trim="id" autocomplete="off" placeholder="请输入用户id ( 不是学工号 )"></el-input>
                </el-form-item>
                <el-form-item label="角色：" :label-width="formLabelWidth">
                    <el-select v-model="rid" placeholder="请选择">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                    </el-select>
                </el-form-item>
                <div class="button">
                    <el-button @click="clear">重置</el-button>
                    <el-button type="primary" @click="updateManager">确 定</el-button>
                </div>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { UpdateManager } from "@/request/api";

export default {
    data () {
        return {
            id:"",          // 用户id
            rid:"",         // 权限id
            formLabelWidth: '150px',
            options: [{
                value: '1',
                label: '超级管理员'
            },{
                value: '2',
                label: '用户管理员'
            },{
                value: '3',
                label: '场地管理员'
            },{
                value: '4',
                label: '赛事管理员'
            },{
                value: '5',
                label: '器材管理员'
            },{
                value: '6',
                label: '学生'
            },{
                value: '7',
                label: '教职工'
            }],
        }
    },
    methods:{
        updateManager(){
            if(this.id==""){
                this.$message.warning("用户id为空")
                this.clear()
            }else if(this.rid==""){
                this.$message.warning("未选择需要变更的角色")
                this.clear()
            }else{
                UpdateManager({
                    ID:this.id,
                    RID:this.rid
                }).then(res=>{
                    console.log(res);
                    if(res.code=="200"){
                        this.$message.success(res.msg)
                    }else if(res.code=="401"){
                        this.$message.error(res.msg)
                    }else{
                        this.$message.warning("发生未知错误")
                    }
                    this.clear()
                })
            }
        },
        clear(){
            this.id = ""
            this.rid = ""
        }
    }
}
</script>
 
<style lang = "less" scoped>
    .form{
        width: 450px;
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
    }
</style>