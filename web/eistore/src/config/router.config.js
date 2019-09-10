// eslint-disable-next-line
import { UserLayout, BasicLayout, RouteView, BlankLayout, PageView } from '@/layouts'

export const asyncRouterMap = [

  {
    path: '/',
    name: 'index',
    component: BasicLayout,
    meta: { title: '首页' },
    redirect: '/model/userCenter',
    children: [
      // 模型中心
      {
        path: '/model',
        name: 'model',
        redirect: '/model/userCenter',
        component: RouteView,
        meta: { title: '模型中心', keepAlive: false, icon: 'code-sandbox' },
        children: [
          {
            path: '/model/userCenter',
            name: 'userCenter',
            component: () => import('@/views/model/UserCenter'),
            meta: { title: '用户中心', keepAlive: false }
          }
        ]
      }
    ]
  },
  {
    path: '*', redirect: '/404', hidden: true
  }
]

/**
 * 基础路由
 * @type { *[] }
 */
export const constantRouterMap = [
  {
    path: '/user',
    component: UserLayout,
    redirect: '/user/login',
    hidden: true,
    children: [
      {
        path: 'login',
        name: 'login',
        component: () => import(/* webpackChunkName: "user" */ '@/views/system/user/Login')
      },
      {
        path: 'register',
        name: 'register',
        component: () => import(/* webpackChunkName: "user" */ '@/views/system/user/Register')
      },
      {
        path: 'register-result',
        name: 'registerResult',
        component: () => import(/* webpackChunkName: "user" */ '@/views/system/user/RegisterResult')
      },
      {
        path: 'recover',
        name: 'recover',
        component: () => import(/* webpackChunkName: "user" */ '@/views/system/user/Recover')
      }
    ]
  },

  {
    path: '/404',
    component: () => import(/* webpackChunkName: "fail" */ '@/views/system/exception/404')
  }

]
