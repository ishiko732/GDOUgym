//axios封装
import axios from "axios";


const instance=axios.create({
    // baseURL:'http://127.0.0.1:8080',
    baseURL:'http://1.15.187.187:8080',
    timeout:10000,           //超过10s 则超时
})
 
//请求拦截器
instance.interceptors.request.use(config=>{
    let token = localStorage.getItem("token")
    if(token){
        config.headers["Authorization"]=token
    }
    console.log(config);
    return config
})

//响应拦截器
instance.interceptors.response.use(res=>{
    // console.log(res);
    localStorage.removeItem("token")
    localStorage.setItem("token",res.headers.authorization)
    return res.data
})

export default instance