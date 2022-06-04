//axios封装
import axios from "axios";

const instance=axios.create({
    baseURL:'1.15.187.187:8080'
})

//请求拦截器
instance.interceptors.request.use(config=>{
    config.headers.Authorization=window.location.getItem("token") 
    return config
})

//响应拦截器
instance.interceptors.response.use(res=>{
    return res
})

export default instance
