import request from "./request"

// 登录请求
export const GoLogin = (password,username) => request.post("/user/login?password="+password+"&username="+username)
export const Login = (params) => request.post("/user/login",params)

// 注册请求
export const GoRegister = (id,password,role,username) => request.post("/user/register?id="+id+"&password="+password+"&role="+role+"&username="+username)
export const Register = (params) => request.post("/user/register",params)
