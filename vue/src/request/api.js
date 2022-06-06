import request from "./request"

// 登录请求
export const Login = (params) => request.post("/user/login",params)

// 注册请求
export const Register = (params) => request.post("/user/register",params)

// 获取当前登录用户信息
export const SelLoginUserInfo = () => request.get("/user/currentUser")

// 添加管理员
export const AddManager = (params) => request.post("/user/addManager",params)

// 删除管理员
export const DelManager = (params) => request.post("/user/deleteManager",params)

// 更新管理员
export const UpdateManager = (params) => request.post("/user/changeRole",params)

// 公告请求接口
// 查询公告历史记录
export const QueryAnnLogs = (params) => request.get("/announcement/queryAnnouncementLogs",{params})

// 创建公告
export const CreateAnnouncement = (params) => request.post("/announcement/insertAnnouncement",params)

// 修改公告
export const UpdateAnnouncement = (params) => request.post("/announcement/updateAnnouncement",params)

// 查询最新公告
export const QueryNewAnn = (params) => request.get("/announcement/queryNewAnnouncement",{params})

// 查询公告类型种类
export const QueryAnnType = () => request.get("/announcement/type")

// 修改密码
export const UpdatePassword = (params) => request.post("/user/changePassword",params)
