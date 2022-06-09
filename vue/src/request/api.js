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

// 获取场地类型
export const queryType = () => request.get("field/queryType")

//按照类型查询场地
export const queryFieldByType = (params) => request.get("field/queryFieldByType", { params })

//查询场地安排表
export const listTimeByDate = (params) => request.post("/field/listTimeByDate", params)

//获取场地一周信息安排
export const queryDate = () => request.get("field/queryDate")

//预约场地
export const orderField = (params) => request.post("/field/orderField", params)

//查询场地预约
export const queryCheck = () => request.get("field/queryCheck")

//场地预约审核
export const checkOrder = (params) => request.post("/field/checkOrder", params)

//用户查询场地审核列表
export const queryCheckByUid = () => request.post("field/queryCheckByUid")

//取消场地预约
export const cancelCheckById = (params) => request.post("/field/cancelCheckById", params)

//查询场地费用
export const queryMoneyByTimeId = (params) => request.get("/field/queryMoneyByTimeId", { params })

//新增场地类型
export const addType = (params) => request.post("/field/addType", params)

//添加场地
export const addField = (params) => request.post("/field/addField", params)

//编辑场地
export const updateField = (params) => request.post("/field/updateField", params)

//用户付费
export const pay = (params) => request.post("/field/pay", params)

//删除场地类型
export const deleteType = (params) => request.post("/field/deleteType", params)

//删除场地类型
export const deleteField = (params) => request.post("/field/deleteField", params)

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

//查询器材
export const queryEquipment = (params) => request.get("/equipment/queryEquipment",{params})

//查询器材可用数量
export const availableEquipmentCount = (params) => request.get("/equipment/availableEquipmentCount",{params})

//查询器材可用数量
export const queryEquipmentRentStandard = () => request.get("/equipment/queryEquipmentRentStandard")

//器材报废
export const reduceEquipmentCount = (params) => request.delete("/equipment/reduceEquipmentCount",{params})

//申请回收器材
export const applyRecycleEquipment = (params) => request.post("/equipment/applyRecycleEquipment",params)

//查询回收器材
export const queryRecycleEquipment = () => request.get("/equipment/queryRecycleEquipment")

//确认回收器材
export const confirmRecycleEquipment = (params) => request.post("/equipment/confirmRecycleEquipment",params)


//申请维护器材
export const applyFixEquipment = (params) => request.post("/equipment/applyFixEquipment",params)

//查询维护器材
export const queryFixEquipment = () => request.get("/equipment/queryFixEquipment")

//确认器材维护
export const confirmFixEquipment = (params) => request.post("/equipment/confirmFixEquipment",params)

// 赛事创建
export const createCompetition = (params) => request.post("/competition/create",params)
