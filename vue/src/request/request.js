//axios封装
import axios from "axios";

//设置axios为form-data
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
axios.defaults.headers.get['Content-Type'] = 'application/x-www-form-urlencoded';
axios.defaults.transformRequest = [function (data) {
    let ret = ''
    for (let it in data) {
      ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
    }
    return ret
}]

const instance=axios.create({
    baseURL:'http://127.0.0.1:8000/',
    timeout:10000,           //超过10s 则超时
})

//请求拦截器
instance.interceptors.request.use(config=>{
    let token = localStorage.getItem("token")
    if(token){
        config.headers["Authorization"]=token
    }
    // console.log(config);
    return config
})

//响应拦截器
instance.interceptors.response.use(res=>{
    return res.data
})

export default instance


