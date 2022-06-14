<template>
<!-- 导入信息 -->
    <div style="height:900px">
        <div class="form">
            <h2>导入信息</h2>
            <h3>通过文件导入：</h3>
            <span>上传文件的格式例子(第一行的字段必须完全相同才能插入成功)：</span>
            <img src="../../assets/image/importInfo.png" alt="">
            <el-upload
                drag
                multiple
                ref="upload"
                action="string"
                :http-request="UploadImage"
                :file-list="fileList">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">ps:只能上传.xlsx文件</div>
            </el-upload>
            <h3>导入单条数据：</h3>
            <el-form>
                <p>带*号的为必填</p>
                <el-form-item label="学工号*：" :label-width="formLabelWidth">
                    <el-input v-model.trim="id" autocomplete="off" placeholder="请输入学工号"></el-input>
                </el-form-item>
                <el-form-item label="姓名*：" :label-width="formLabelWidth">
                    <el-input v-model.trim="truename" autocomplete="off" placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item label="班级：" :label-width="formLabelWidth">
                    <el-input v-model.trim="class1" autocomplete="off" placeholder="请输入班级"></el-input>
                </el-form-item>
                <div class="button">
                    <el-button @click="clear">清空</el-button>
                    <el-button type="primary" @click="addOne">确 定</el-button>
                </div>
                
            </el-form>
        </div>
    </div>
</template>

<script>
import { ExportUser } from "@/request/api";
export default {
    data () {
        return {
            oneMap:'',
            fileList:[],
            id:"",
            truename:"",
            class1:"",
            formLabelWidth: '150px',
        }
    },
    methods:{
        addOne(){
            if(this.id==""||this.truename==""){
                this.$message.warning("学工号或姓名为空")
            }else{
                if(this.class1==''){
                    this.oneMap='{"id":"'+this.id+'","truename":"'+this.truename+'"}'
                }else{
                    this.oneMap='{"id":"'+this.id+'","truename":"'+this.truename+'","class":"'+this.class1+'"}'
                }
                ExportUser({map:this.oneMap}).then(res=>{
                    // console.log(res);
                    if(res.msg=="导入信息"){
                        this.$message.success(res.msg)
                    }else{
                        this.$message.warning(res.msg)
                    }
                }).catch(err=>{
                    this.$message.error(err.response.data.data+"，请重新登录")
                })
                this.clear()
                this.oneMap=''
            }
            
        },
        UploadImage (param) {
            let fd = new FormData()
            // console.log('param.file',param.file)
            fd.append('file', param.file) // 要提交给后台的文件
            ExportUser(fd).then(res=>{
                // console.log(res);
                if(res.msg=="导入信息"){
                    this.$message.success(res.msg)
                }else{
                    this.$message.warning(res.msg)
                }
            }).catch(err=>{
                // console.log("err:",err.response.data);
                this.$message.error(err.response.data.data+"，请重新登录")
            })
        },
        clear(){
            this.id=""
            this.truename=""
            this.class1=""
        }
    }
}
</script>
 
<style lang = "less" scoped>
    .form{
        width: 600px;
        margin: 50px 300px;
        h2{
            text-align: center;
            margin-bottom: 50px;
            margin-left: 80px;
        }
        /deep/.el-upload{
            margin-left: 150px;
            color: red;
            text-align: center;
        }
        .el-upload__tip{
            color: red;
            margin-left: 240px;
            font-size: 20px;
        }
        span{
            margin-left: 60px;
            color:red;
            font-size:18px;
        }
        img{
            margin: 20px 120px;
        }
        h3{
            margin: 20px 0;
        }
        .button{
            padding-left: 50px;
            text-align: center;
            .el-button{
                margin: 0 40px;
            }
        }
        /deep/.el-input__inner{
            width: 400px;
        }
        p{
            color: red;
            text-align: center;
        }
    }
</style>