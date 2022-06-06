import request from "./request"

// 登录请求
export const Login = (params) => request.post("/user/login",params)

// 注册请求
export const Register = (params) => request.post("/user/register",params)
