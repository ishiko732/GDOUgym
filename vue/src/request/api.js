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

// 注销
export const Logout = () => request.get("/user/logout")

// 通过学工号查询用户
export const QueryUserInfoById = (params) => request.get("/user/queryUserInfoById",{params})

// 通过用户id查询用户
export const QueryUserInfoByUid = (params) => request.get("/user/queryUserInfo",{params})

// 查询全部用户
export const QueryUsers = (params) => request.get("/user/queryUsers",{params})

// 导入信息
export const ExportUser = (params) => request.post("/user/exportUser",params)

// 查询管理员信息
export const QueryManagerInfo = (params) => request.post("/user/queryManagerByName",params)

