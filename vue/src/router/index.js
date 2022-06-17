import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login'
import { GetNewToken } from "@/request/api";

Vue.use(VueRouter)

const routes = [
  {
    path:'/',
    redirect:"/login"
  },
  {
    path:'/login',
    name:'login',
    component:Login
  },
  {
    path: '/home',
    name: 'home',
    component: Home,
    children:[
      {
        //器材管理路由
        path: '/home/equipmentManagement',
        name: 'equipmentManagement',
        component:()=>import('../views/equipment/equipmentManagement'),
      },
      {
        //赛事管理路由
        path: '/home/comManagement',
        name: 'eventManagement',
        component:()=>import('../views/competition/comManagement'),
        children:[
          {
            //赛事创建路由
            path:'/home/comManagement/competitionCreate',
            name:'competitionCreate',
            component:()=>import('../views/competition/competitionCreate.vue')
          },
          {
            //赛事审核路由
            path:'/home/comManagement/competitionReview',
            name:'competitionReview',
            component:()=>import('../views/competition/competitionReview.vue')
          },
          {
            //赛事取消路由
            path:'/home/comManagement/competitionCancel',
            name:'competitionCancel',
            component:()=>import('../views/competition/competitionCancel.vue')
          },
          {
            //赛事查询路由
            path:'/home/comManagement/competitionQuery',
            name:'competitionQuery',
            component:()=>import('../views/competition/competitionQuery.vue')
          },
          {
            //比赛场地安排路由
            path:'/home/comManagement/comFieldArrange',
            name:'comFieldArrange',
            component:()=>import('../views/competition/comFieldArrange.vue')
          },
          {
            //赛事器材安排路由
            path:'/home/comManagement/comEquipArrange',
            name:'comEquipArrange',
            component:()=>import('../views/competition/comEquipArrange.vue')
          },
          {
            //裁判简介公告路由
            path:'/home/comManagement/refereeAnnouncement',
            name:'refereeAnnouncement',
            component:()=>import('../views/competition/refereeAnnouncement.vue')
          },
          {
            //查询裁判公告路由
            path:'/home/comManagement/queryRefereeAnn',
            name:'queryRefereeAnn',
            component:()=>import('../views/competition/queryRefereeAnn.vue')
          },

        ],
      },
      {
        //场地管理路由
        path: '/home/siteManagement',
        name: 'siteManagement',
        component:()=>import('../views/site/siteManagement'),
        children:[
          {
            //场地安排路由
            path:'/home/siteManagement/siteArrange',
            name:'siteArrange',
            component:()=>import('../views/site/siteArrange')
          },
           {
            //场地信息路由
            path:'/home/siteManagement/siteMessage',
            name:'siteMessage',
            component:()=>import('../views/site/siteMessage')
          },
          {
            //场地类型管理路由
            path:'/home/siteManagement/siteTypeManagement',
            name:'siteTypeManagement',
            component:()=>import('../views/site/siteTypeManagement')
          },
          {
            //预约审核路由
            path:'/home/siteManagement/appointmentManagement',
            name:'appointmentManagement',
            component:()=>import('../views/site/appointmentManagement')
          },
          {
            //场地预约路由
            path:'/home/siteManagement/siteAppointment',
            name:'siteAppointment',
            component:()=>import('../views/site/siteAppointment')
          },
          {
            //通知路由
            path:'/home/siteManagement/noticeManagement',
            name:'noticeManagement',
            component:()=>import('../views/site/noticeManagement')
          },
          {
            //学生付费路由
            path:'/home/siteManagement/appointPay',
            name:'appointPay',
            component:()=>import('../views/site/appointPay')
          },
        ],
      },
      {
        //用户管理路由
        path: '/home/userManagement',
        name: 'userManagement',
        component:()=>import('../views/user/userManagement'),
        children:[
          {
            //添加管理员路由
            path:'/home/userManagement/addManager',
            name:'addManager',
            component:()=>import('../views/user/addManager.vue')
          },
          {
            //修改密码路由
            path:'/home/userManagement/updatePassword',
            name:'updatePassword',
            component:()=>import('../views/user/updatePassword.vue')
          },
          {
            //修改密码（强制）路由
            path:'/home/userManagement/updatePwdForce',
            name:'updatePwdForce',
            component:()=>import('../views/user/updatePwdForce.vue')
          },
          {
            //导入信息路由
            path:'/home/userManagement/importInfo',
            name:'importInfo',
            component:()=>import('../views/user/importInfo.vue')
          },
          {
            //查询用户个人信息路由
            path:'/home/userManagement/queryUserInfo',
            name:'queryUserInfo',
            component:()=>import('../views/user/queryUserInfo.vue')
          },
          {
            //查询管理员信息路由
            path:'/home/userManagement/queryManagerInfo',
            name:'queryManagerInfo',
            component:()=>import('../views/user/queryManagerInfo.vue')
          }
        ]
      },
      {
        //公告管理路由
        path: '/home/annManagement',
        name: 'annManagerment',
        component:()=>import('../views/announcement/annManagerment.vue'),
        children:[
          {
            //创建公告路由
            path:'/home/annManagement/createAnnouncement',
            name:'createAnnouncement',
            component:()=>import('../views/announcement/createAnnouncement.vue')
          },
          {
            //修改公告路由
            path:'/home/annManagement/updateAnnouncement',
            name:'updateAnnouncement',
            component:()=>import('../views/announcement/updateAnnouncement.vue')
          },
          {
            //查询公告历史记录路由
            path:'/home/annManagement/queryAnnLogs',
            name:'queryAnnLogs',
            component:()=>import('../views/announcement/queryAnnLogs.vue')
          },
          {
            //修改公告历史记录路由
            path:'/home/annManagement/updateAnnLogs',
            name:'updateAnnLogs',
            component:()=>import('../views/announcement/updateAnnLogs.vue')
          },
          {
            //查询最新公告路由路由
            path:'/home/annManagement/queryNewAnn',
            name:'queryNewAnn',
            component:()=>import('../views/announcement/queryNewAnn.vue')
          },
        ]
      },
      {
        //新增器材路由
        path: '/home/addEquipment',
        name: 'addEquipment',
        component:()=>import('../views/equipment/addEquipment'),
      },
      {
        //删除器材路由
        path: '/home/deleteEquipment',
        name: 'deleteEquipment',
        component:()=>import('../views/equipment/deleteEquipment'),
      },
      {
        //维修器材路由
        path: '/home/fixEquipment',
        name: 'fixEquipment',
        component:()=>import('../views/equipment/fixEquipment'),
      },
      {
        //回收器材路由
        path: '/home/recycleEquipment',
        name: 'recycleEquipment',
        component:()=>import('../views/equipment/recycleEquipment'),
      },
      {
        //租借器材路由
        path: '/home/rentEquipment',
        name: 'rentEquipment',
        component:()=>import('../views/equipment/rentEquipment'),
      },
      {
        //租借费用路由
        path: '/home/price',
        name: 'price',
        component:()=>import('../views/equipment/price'),
      },
      {
        //归还器材路由
        path: '/home/returnEquipment',
        name: 'returnEquipment',
        component:()=>import('../views/equipment/returnEquipment'),
      },
    ]
  },
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior () {
    return { x: 0, y: 0 }
  }

})
const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to) {
  return VueRouterPush.call(this, to).catch(err => err)
}

router.beforeEach((to,from,next)=>{
  let token = localStorage.getItem("token");
  if(to.path == "/login"){
    next()
  }else{
    if(token){
      next()
      if(from.path != "/login"){
        GetNewToken().then(res=>{
          if(res.data.new_token!=undefined)
            localStorage.removeItem("token")
            localStorage.setItem("token",res.data.new_token)
        })
      }
    }else{
      Vue.prototype.$message.error("未登录，无法访问，请先登录")
      setTimeout(()=>{
        next("/login")
      },500)
    }
  }
})
document.title = "海大体育馆管理系统"
export default router
