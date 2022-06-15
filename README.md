# 软件工程实践

> 2022年广东海洋大学软件工程实践课程设计

[toc]

# 操作环境

前端

| 框架 | 解释   |
| ---- | ------ |
| vue  | 渲染层 |
| element-ui| ui|
|nginx| http服务|

后端

| 框架 | 解释   |
| ---- | ------ |
| java  | 编程语言 |
| springBoot| MVC框架|
|shiro| 权限控制|
|mybatisPlus| 数据持久层|
|redis| token缓存|

----

# 运行后台

生成jar包: `mvn install`
运行: `cd /home/ubuntu/gym/ && nohup java -Dfile.encoding=utf-8 -jar gym_java-0.0.1-SNAPSHOT.jar >temp.txt &`

查看pid: `ps -ef|grep gym_java-0.0.1-SNAPSHOT.jar`
关闭进程：` kill -s 9 pid`

----

# 运行前台

安装项目：`npm install`

开发模式：`npm run serve`

编译为3套件:`npm run build`

------

# API接口文档

swagger-ui：用于扫描API接口

采用Postman写文档：[API接口文档](https://documenter.getpostman.com/view/18348648/Uz5GobTh)

------

#  文件结构

## vue

```
.
├── README.md 帮助文档
├── babel.config.js 包配置文件
├── jsconfig.json 包配置文件
├── package.json 包配置文件
├── public
│   ├── favicon.ico 网站图标
│   └── index.html 默认主页
├── src
│   ├── App.vue Vue的入口文件
│   ├── assets  
│   │   └── image
│   │       └── importInfo.png 导入信息提示
│   ├── main.js Vue的入口JS文件
│   ├── request
│   │   ├── api.js 接口文件
│   │   └── request.js 处理axios请求封装
│   ├── router
│   │   └── index.js 路由管理文件
│   ├── store
│   │   └── index.js Vuex配置文件
│   └── views 视图层
│       ├── Home.vue 系统主页面
│       ├── Login.vue 系统登录注册页面
│       ├── announcement 公告模块
│       │   ├── annManagerment.vue 公告管理首页
│       │   ├── createAnnouncement.vue 创建公告页面
│       │   ├── queryAnnLogs.vue 查询公告历史记录页面
│       │   ├── queryNewAnn.vue 查询最新公告页面
│       │   ├── updateAnnLogs.vue 修改公告历史记录页面
│       │   └── updateAnnouncement.vue 修改公告记录页面
│       ├── competition 赛事模块
│       │   ├── comEquipArrange.vue 赛事器材安排页面
│       │   ├── comFieldArrange.vue 赛事场地安排页面
│       │   ├── comManagement.vue 赛事管理首页
│       │   ├── competitionCancel.vue 赛事取消页面
│       │   ├── competitionCreate.vue 赛事创建页面
│       │   ├── competitionQuery.vue 赛事查询页面
│       │   ├── competitionReview.vue 赛事审核页面
│       │   ├── queryRefereeAnn.vue 查询裁判公告页面
│       │   └── refereeAnnouncement.vue 设置裁判简介公告页面
│       ├── equipment 器材模块
│       │   ├── addEquipment.vue 新增器材页面
│       │   ├── deleteEquipment.vue 删除器材页面
│       │   ├── equipmentManagement.vue 器材管理首页
│       │   ├── fixEquipment.vue 维修器材页面
│       │   ├── price.vue 租用价格页面
│       │   ├── recycleEquipment.vue 回收器材页面
│       │   ├── rentEquipment.vue 租借器材页面
│       │   └── returnEquipment.vue 归还器材页面
│       ├── site 场地模块
│       │   ├── appointPay.vue 场地付费入口页面
│       │   ├── appointmentManagement.vue	预约审核页面
│       │   ├── noticeManagement.vue 通知公告页面
│       │   ├── siteAppointment.vue 场地预约页面
│       │   ├── siteArrange.vue 场地安排页面
│       │   ├── siteManagement.vue 场地管理首页
│       │   ├── siteMessage.vue 场地信息页面
│       │   └── siteTypeManagement.vue 场地类型选择页面
│       └── user 用户模块
│           ├── addManager.vue 添加管理员页面
│           ├── delManager.vue 删除管理员页面
│           ├── importInfo.vue 导入信息页面
│           ├── queryManagerInfo.vue 查询管理员页面
│           ├── queryUserInfo.vue 查询用户信息页面
│           ├── updateManager.vue 修改管理员角色页面
│           ├── updatePassword.vue 修改密码页面
│           ├── updatePwdForce.vue 强制修改密码页面
│           └── userManagement.vue 用户管理首页
└── vue.config.js Vue配置文件

13 directories, 56 files
```
## java

```
.
├── HELP.md 帮助文档
├── pom.xml maven配置文件
└── src
    └── main
        ├── java
        │   └── edu
        │       └── gdou
        │           └── gym_java
        │               ├── GymJavaApplication.java SpringBoot主程序
        │               ├── config 配置文件夹
        │               │   ├── CodeGenerator.java MybatisPlus代码生成器
        │               │   ├── GsonConfig.java Google的Gson配置(单例模式)
        │               │   ├── JedisConfig.java Redis配置文件
        │               │   ├── LogFilter.java 日志系统（过滤器）
        │               │   ├── MybatisPlusConfig.java MybatisPlus配置文件
        │               │   ├── ShiroConfig.java 角色权限配置shiro配置文件
        │               │   └── Swagger2Configuration.java Swagger文档配置文件
        │               ├── controller 控制层
        │               │   ├── AnnouncementController.java 公告控制层
        │               │   ├── CompetitionController.java 赛事控制层
        │               │   ├── EquipmentController.java 器材控制层
        │               │   ├── FieldController.java 场地控制层
        │               │   ├── RoleController.java 角色控制层
        │               │   └── UserController.java 用户控制层
        │               ├── entity 实体
        │               │   ├── VO
        │               │   │   ├── FieldCheckVo.java 场地审核VO对象
        │               │   │   ├── Init_Competition.java 初始化赛事创建VO对象
        │               │   │   ├── QueryTimeVo.java
        │               │   │   ├── RefereeAnnouncement.java 赛事裁判公告信息VO对象
        │               │   │   └── TimeLimit.java 时间范围VO对象
        │               │   ├── bean
        │               │   │   └── ResponseBean.java Result对象
        │               │   ├── enums
        │               │   │   ├── CheckStatus.java 检查审核枚举类
        │               │   │   └── RoleEnums.java 角色枚举类
        │               │   └── model
        │               │       ├── Announcement.java 公告bean对象
        │               │       ├── Competition.java 赛事bean对象
        │               │       ├── CompetitionCancel.java 赛事取消bean对象
        │               │       ├── CompetitionCheck.java 赛事审核bean对象
        │               │       ├── CompetitionEquipment.java 赛事器材bean对象
        │               │       ├── CompetitionField.java 赛事场地bean对象
        │               │       ├── Equipment.java 器材bean对象
        │               │       ├── Field.java 场地bean对象
        │               │       ├── FieldCheck.java 场地审核bean对象
        │               │       ├── FieldDate.java 场地时间bean对象
        │               │       ├── FieldType.java 场地类型bean对象
        │               │       ├── FixEquipment.java 维护器材bean对象
        │               │       ├── FixEquipmentBill.java 维护器材账单bean对象
        │               │       ├── MyPage.java 自定义分页bena对象
        │               │       ├── OrderItem.java 订单bean对象
        │               │       ├── RecycleEquipment.java 回收器材bean对象
        │               │       ├── RentEquipment.java 租用器材bean对象
        │               │       ├── Role.java 角色bean对象
        │               │       ├── TimeArrange.java 场地时间范围bena对象
        │               │       └── User.java 用户bena对象
        │               ├── exception 异常处理
        │               │   ├── CustomException.java  shiro异常处理
        │               │   ├── CustomUnauthorizedException.java shiro401权限异常处理
        │               │   ├── ExceptionAdvice.java 运行时异常处理
        │               │   └── GlobalException.java 全局异常处理
        │               ├── mapper 数据访问层
        │               │   ├── AnnouncementMapper.java 公告dao接口
        │               │   ├── CompetitionCancelMapper.java 赛事取消dao接口
        │               │   ├── CompetitionCheckMapper.java 赛事审核dao接口
        │               │   ├── CompetitionEquipmentMapper.java 赛事器材dao接口
        │               │   ├── CompetitionFieldMapper.java 赛事场地dao接口
        │               │   ├── CompetitionMapper.java 赛事dao接口
        │               │   ├── EquipmentMapper.java 器材dao接口
        │               │   ├── FieldMapper.java 场地dao接口
        │               │   ├── FixEquipmentBillMapper.java 维护器材账单dao接口
        │               │   ├── FixEquipmentMapper.java  维护器材dao接口
        │               │   ├── RecycleEquipmentMapper.java 回收器材dao接口
        │               │   ├── RentEquipmentMapper.java 租用器材dao接口
        │               │   ├── RoleMapper.java 角色dao接口
        │               │   └── UserMapper.java 用户dao接口
        │               ├── service 服务层
        │               │   ├── AnnouncementService.java 公告业务接口
        │               │   ├── EquipmentService.java 器材业务接口
        │               │   ├── FieldService.java 场地业务接口
        │               │   ├── FixEquipmentBillService.java 维护器材账单业务接口
        │               │   ├── FixEquipmentService.java 维护器材业务接口
        │               │   ├── RecycleEquipmentService.java 回收器材业务接口
        │               │   ├── RentEquipmentService.java 租用业务接口
        │               │   ├── RoleService.java 角色业务接口
        │               │   ├── UserService.java 用户业务接口
        │               │   ├── cm
        │               │   │   ├── CompetitionCancelService.java 赛事取消业务接口
        │               │   │   ├── CompetitionCheckService.java 赛事审核业务接口
        │               │   │   ├── CompetitionEquipmentService.java 赛事器材业务接口
        │               │   │   ├── CompetitionFieldService.java 赛事场地业务接口
        │               │   │   └── CompetitionService.java 赛事业务接口
        │               │   └── serviceImpl 服务实现层
        │               │       ├── AnnouncementServiceImpl.java 公告业务实现
        │               │       ├── EquipmentServiceImpl.java 器材业务实现
        │               │       ├── FieldServiceImpl.java 场地业务实现
        │               │       ├── FixEquipmentBillServiceImpl.java 维护器材账单业务实现
        │               │       ├── FixEquipmentServiceImpl.java 维护器材业务实现
        │               │       ├── RecycleEquipmentServiceImpl.java 回收器材业务实现
        │               │       ├── RoleServiceImpl.java 角色业务实现
        │               │       ├── UserServiceImpl.java 用户业务实现
        │               │       └── cm
        │               │           ├── CompetitionCancelServiceImpl.java 赛事取消业务实现
        │               │           ├── CompetitionCheckServiceImpl.java 赛事审核业务实现
        │               │           ├── CompetitionEquipmentServiceImpl.java 赛事器材业务实现
        │               │           ├── CompetitionFieldServiceImpl.java 赛事场地业务实现
        │               │           ├── CompetitionServiceImpl.java 赛事业务实现
        │               │           └── RentEquipmentServiceImpl.java 租用器材业务实现
        │               ├── shiro 角色权限包
        │               │   ├── MyRealm.java 自定义realm
        │               │   ├── cache 
        │               │   │   ├── CustomCache.java redis缓存控制
        │               │   │   └── CustomCacheManager.java redis缓存管理器
        │               │   ├── jwt
        │               │   │   ├── JWTFilter.java JWT过滤器
        │               │   │   ├── JWTToken.java authorizationBean对象
        │               │   │   └── JWTUtil.java  JWT工具类
        │               │   └── redis
        │               │       ├── Constant.java redis常量类
        │               │       └── JedisUtil.java redis工具类
        │               └── utils 工具包
        │                   ├── MD5.java md5工具类
        │                   ├── SerializableUtil.java 序列化工具类
        │                   ├── SpringContextHolder.java 获取Spring单例工具类
        │                   ├── StringUtil.java 字符串工具类
        │                   └── TimeUtils.java 时间工具类
        └── resources
            ├── application.yml SpringBoot配置文件（基本配置）
            ├── config
            │   └── application.yml SpringBoot配置文件(涉及数据库连接)
            ├── mapper 数据访问层配置文件夹
            │   ├── AnnouncementMapper.xml 公告表dao配置文件
            │   ├── CompetitionCancelMapper.xml 赛事取消表dao配置文件
            │   ├── CompetitionCheckMapper.xml 赛事审核表dao配置文件
            │   ├── CompetitionEquipmentMapper.xml 赛事器材表dao配置文件
            │   ├── CompetitionFieldMapper.xml 赛事场地表dao配置文件
            │   ├── CompetitionMapper.xml 赛事表dao配置文件
            │   ├── EquipmentMapper.xml 器材表dao配置文件
            │   ├── FieldMapper.xml 场地表dao配置文件
            │   ├── FixEquipmentBillMapper.xml 维护器材账单表dao配置文件
            │   ├── FixEquipmentMapper.xml 维护器材表dao配置文件
            │   ├── RecycleEquipmentMapper.xml 回收器材表dao配置文件
            │   ├── RentEquipmentMapper.xml 租用表dao配置文件
            │   ├── RoleMapper.xml 角色表dao配置文件
            │   └── UserMapper.xml 用户表dao配置文件
            ├── opt.sql 测试sql语句
            └── spy.properties spy劫持mybatis日志系统配置文件

27 directories, 121 files
```

-----

# 团队协作

## Git合作开发信息
```bash
brew install gource
```

```bash
gource -1280x720 -o - | ffmpeg -y -r 30 -f image2pipe -vcodec ppm -i - -vcodec libx264 -preset ultrafast -pix_fmt yuv420p -crf 1 -threads 0 -bf 0 gource.mp4
```

## 画图工具

| 工具                                                         | 解释                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [drawio](https://drawio-app.com/)                            | ER图(概念模型)，表与表关系，[SQL语句插入](https://desk.draw.io/support/solutions/articles/16000082007) |
| [astash.uml](https://astah.change-vision.com/ja/product/astah-uml.html) | 时序图，活动图，[泳道图](https://astah.net/support/astah-pro/user-guide/activity-diagram/)，调用关系图，类图 |
| [SequencePlugin](https://vanco.github.io/SequencePlugin/)    | 根据代码生成时序图                                           |
| [tree](https://qiita.com/kanuma1984/items/c158162adfeb6b217973) | 生成文件列表                                                 |
| [sql table](https://segmentfault.com/a/1190000023825452)     | 生成数据库表信息                                             |



# MIT License

- MIT是和BSD一样宽松的许可协议，作者保留版权,而无任何其他了限制。

- 你必须在你的发行版里包含原许可协议的声明，无论你是以二进制发布的还是以源代码发布的。
- 你可以使用，复制和修改软件
- 你可以免费使用软件或出售
- 唯一的限制是，它是必须附有MIT授权协议
