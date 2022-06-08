<template>
<!-- 导入信息 -->
    <div style="height:800px">
        <div class="form">
            <h2>导入信息</h2>
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
            <!-- <button @click="addOne">导入单条</button> -->
        </div>
    </div>
</template>

<script>
import { ExportUser } from "@/request/api";
export default {
    data () {
        return {
            one:'{"id":"123","sex":"男"}',
            fileList:[],
 
        }
    },
    methods:{
        addOne(){
            let dd = new FormData()
            dd.append('map','{"id":"123","sex":"男"}')
            ExportUser(dd).then(res=>{
                // console.log(res);
            })
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
    }
</style>