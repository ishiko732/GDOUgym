import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login'

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
        path: '/home/eventManagement',
        name: 'eventManagement',
        component:()=>import('../views/event/eventManagement'),
      },
      {
        //场地管理路由
        path: '/home/siteManagement',
        name: 'siteManagement',
        component:()=>import('../views/site/siteManagement'),
      },
      {
        //用户管理路由
        path: '/home/userManagement',
        name: 'userManagement',
        component:()=>import('../views/user/userManagement'),
      },
      {
        //新增器材路由
        path: '/home/addEquipment',
        name: 'addEquipment',
        component:()=>import('../views/equipment/addEquipment'),
      },
    ]
  },
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to) {
  return VueRouterPush.call(this, to).catch(err => err)
}

export default router
