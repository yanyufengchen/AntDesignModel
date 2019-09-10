import Vue from 'vue'
import axios from 'axios'
import store from '@/store'
import router from '@/router'
import {
  VueAxios
} from './axios'
import notification from 'ant-design-vue/es/notification'
import {
  ACCESS_TOKEN
} from '@/store/mutation-types'

// 创建 axios 实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_URL, // api base_url
  withCredentials: true, // 传递cookie
  timeout: 6000 // 请求超时时间
})

const err = (error) => {
  if (error.message && error.message.indexOf('timeout') > -1) {
    notification.error({
      message: '错误',
      description: '请求超时, 请稍候再试'
    })
  } else if (error.response) {
    const { data, status } = error.response
    const token = Vue.ls.get(ACCESS_TOKEN)
    if (status === 403) {
      notification.error({
        message: '错误',
        description: data.errorMessage || '没有权限访问'
      })
    } else if (status === 401) {
      notification.error({
        message: '错误',
        description: data.errorMessage || '没有登录或者是登录失败'
      })
      if (token) {
        store.dispatch('Logout').then(() => {
          setTimeout(() => {
            window.location.reload()
          }, 1500)
        })
      }
    } else {
      notification.error({
        message: '错误',
        description: data.errorMessage
      })
    }
  }
  return Promise.reject(error)
}

// request interceptor
service.interceptors.request.use(config => {
  const token = Vue.ls.get(ACCESS_TOKEN)
  if (token) {
    config.headers['Access-Token'] = token // 让每个请求携带自定义 token 请根据实际情况自行修改
  }
  return config
}, err)

// response interceptor
service.interceptors.response.use((response) => {
  const { data } = response
  if (data.errorCode === '00000004') {
    router
      .dispatch('Logout')
      .then(res => {
        window.location.reload()
      })
  } else if (!data.success) {
    // 业务请求失败, 统一处理业务错误信息
    if (data.errorMessage) {
      notification.error({
        message: '错误',
        description: data.errorMessage
      })
    }
  }
  return data
}, err)

const installer = {
  vm: {},
  install (Vue) {
    Vue.use(VueAxios, service)
  }
}

export {
  installer as VueAxios,
  service as axios
}
