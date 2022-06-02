import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path:'/',
    redirect:"/home"
  },
  {
    path: '/home',
    name: 'home',
    component: Home,
    children:[
      {
        path: '/home/equipmentManagement',
        name: 'equipmentManagement',
        component:()=>import('../views/equipment/equipmentManagement'),
      },
      {
        path: '/home/eventManagement',
        name: 'eventManagement',
        component:()=>import('../views/event/eventManagement'),
      },
      {
        path: '/home/siteManagement',
        name: 'siteManagement',
        component:()=>import('../views/site/siteManagement'),
      },
      {
        path: '/home/userManagement',
        name: 'userManagement',
        component:()=>import('../views/user/userManagement'),
      }
    ]
  },
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
