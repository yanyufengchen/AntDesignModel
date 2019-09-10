import { axios } from '@/utils/request'

/**
 * 用户登录
 *
 * @param {登录参数} parameter
 */
export function login (parameter) {
  return axios({
    url: '/authc/login',
    method: 'post',
    data: parameter
  })
}

/**
 * 查询登录用户信息
 */
export function getUserInfo () {
  return axios({
    url: '/authc/userinfo',
    method: 'get'
  })
}

/**
 * 退出登录
 */
export function logout () {
  return axios({
    url: '/authc/logout',
    method: 'post'
  })
}

/**
 * 发送手机验证码
 */
export function getSmsCaptcha (mobile) {
  return axios({
    url: `/authc/smsCode/${mobile}`,
    method: 'get'
  })
}
