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
        component:()=>import('../views/equipmentManagement'),
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
