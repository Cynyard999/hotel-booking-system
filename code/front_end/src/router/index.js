import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/login.vue'

Vue.use(VueRouter)
const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/',
    redirect: '/NJUSE'
  },
  {
    path: '/NJUSE',
    name: 'layout',
    component: ()=> import('@/views/layout'),
    children: [
      {
        path: '/hotel/hotelList',
        name: 'hotelList',
        component: () => import('@/views/hotel/hotelList')
      },
      {
        path: '/hotel/hotelDetail/:hotelId',
        name: 'hotelDetail',
        component: () => import('@/views/hotel/hotelDetail')
      },
      {
        path: '/user/info/:userId',
        name: 'userInfo',
        component: () => import('@/views/user/info')
      },

      {
        path: '/user/favList/:userId',
        name: 'favList',
        component: () => import('@/views/user/favList')
      },
      {
        path: '/hotelManager/manageHotel/:managerId',
        name: 'manageHotel',
        component: () => import('@/views/hotelManager/manageHotel')
      },
      {
        path: '/admin/manageHotel',
        name: 'adminManageHotel',
        component: () => import('@/views/admin/adminManageHotel')
      },

      {
        path: '/admin/manageUser',
        name: 'manageUser',
        component: () => import('@/views/admin/manageUser')
      },
      {
        path: '/admin/manageInfo/:userId',
        name: 'manageUserInfo',
        component: () => import('@/views/admin/manageUserInfo')
      },
      {
        path: '/saleMen/manageWebsite',
        name: 'manageWebsite',
        component: ()=> import('@/views/saleMen/manageWebsite')
      },
    ]
  },
]
const createRouter = () => new VueRouter({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes
})
const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
