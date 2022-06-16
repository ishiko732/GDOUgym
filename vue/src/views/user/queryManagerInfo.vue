<template>
<!-- 查询管理员信息（普通用户可查） -->
    <div style="height:900px">
        <div class="form">
            <h2>查询管理员信息</h2>
            <el-form>
                <p><b>ps：用户名为空查询，即查询所有管理员</b></p>
                <el-form-item label="用户名：" :label-width="formLabelWidth">
                    <el-input v-model.trim="username" autocomplete="off" placeholder="请输入用户名"></el-input>
                    <el-button type="primary" @click="query">查 询</el-button>
                </el-form-item>
                    
                <el-form-item label="管理员信息：" :label-width="formLabelWidth">
                    <el-table
                        :data="ManagerInfoList"
                        height="500"
                        border
                        style="width: 100%">
                        <el-table-column
                        prop="id"
                        label="id"
                        width="60">
                        </el-table-column>
                        <el-table-column
                        prop="name"
                        label="用户名"
                        width="150">
                        </el-table-column>
                        <el-table-column
                        prop="role.role"
                        label="管理员角色"
                        width="150">
                        </el-table-column>
                        <el-table-column
                        label="更新的管理员角色">
                        <template slot-scope="scope">
                            <el-select v-model="ManagerInfoList[scope.$index].rid" placeholder="请选择" v-show="isSuperShow">
                            <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                            </el-select>
                        </template>
                        </el-table-column>
                        <el-table-column
                        label="操作"
                        width="150">
                        <template slot-scope="scope">
                            <el-button
                                v-show="isSuperShow"
                                @click="delManager(scope.row,scope.$index)"
                                type="text"
                                size="small">
                                删除
                            </el-button>
                            <el-button
                                v-show="isSuperShow"
                                @click="updateManager(scope.row,scope.$index)"
                                type="text"
                                size="small">
                                更新
                            </el-button>
                        </template>
                        </el-table-column>
                    </el-table>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import { QueryManagerInfo,DelManager,UpdateManager } from "@/request/api";
export default {
    data () {
        return {
            roleId:"",
            isSuperShow:false,
            username:"",
            ManagerInfoList:[],
            formLabelWidth: '100px',
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
        query(){
            this.ManagerInfoList=[]    //清空数组
            if(this.username!=""){
                QueryManagerInfo({username:this.username}).then(res=>{
                    // console.log(res);
                    if(res.code=="200"){
                        if(res.msg=="查询结果为空"){
                            this.$message.warning(res.msg)
                        }else{
                            this.ManagerInfoList = res.data
                            for (let i = 0; i < this.ManagerInfoList.length; i++) {
                                this.ManagerInfoList[i].rid = ""
                            } 
                        }
                    }
                }).catch(err=>{
                    // console.log("err:",err.response.data);
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
            }else{
                QueryManagerInfo().then(res=>{
                    // console.log(res);
                    this.ManagerInfoList = res.data
                }).catch(err=>{
                    // console.log("err:",err.response.data);
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
            }
        },
        delManager(row,index){
            DelManager({ID:row.id}).then(res=>{
                // console.log(res);
                if(res.code=="200"){
                    this.$message.success(res.msg)
                    this.query()
                }else if(res.code=="401"){
                    this.$message.error(res.msg)
                }else{
                    this.$message.warning("发生未知错误")
                }
            })
            .catch(err=>{
                // console.log("err:",err.response.data);
                this.$message.error(err.response.data.data+"，请重新登录")
            })
            
            
        },
        updateManager(row,index){
            let rid = this.ManagerInfoList[index].rid
            UpdateManager({
                ID:row.id,
                RID:rid
            }).then(res=>{
                // console.log(res);
                if(res.code=="200"){
                    this.$message.success(res.msg)
                    this.query()
                }else if(res.code=="401"){
                    this.$message.error(res.msg)
                }else{
                    this.$message.warning("发生未知错误")
                }
            }).catch(err=>{
                // console.log("err:",err.response.data);
                this.$message.error(err.response.data.data+"，请重新登录")
            })
        }
        
    },
    created(){
        this.query()
        this.roleId = localStorage.getItem("roleId")
        if(this.roleId == "1"){
            this.isSuperShow = true
        }else{
            this.isSuperShow = false
        }
    },
    
}
</script>
 
<style lang = "less" scoped>
    .form{
        width: 1000px;
        margin: 50px 300px;
        h2{
            text-align: center;
            margin-bottom: 50px;
            margin-left: 60px;
        }
        p{
            margin-left: 30px;
            color:red;
            padding-bottom: 20px;
        }
        .el-button--primary{
            margin-left:50px;
            transform: translateY(-15px);
        }
        /deep/.el-input__inner,.el-input{
            width: 300px;
        }
        a{
            font-size: 18px;
        }
    
    }
</style>