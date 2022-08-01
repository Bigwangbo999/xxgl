import request from '@/utils/request'

// 查询客户信息编码库列表
export function listInfo(query) {
  return request({
    url: '/userInfo/list',
    method: 'get',
    params: query
  })
}
// 查询客户信息编码库列表
export function saveInfo(query) {
  return request({
    url: '/insert',
    headers:{
      isToken:false
    },
    method: 'post',
    data: query
  })
}
// 发送手机验证码
export function sendMessage(query) {
  return request({
    url: '/sendMsg/'+query,
    headers:{
      isToken:false
    },
    method: 'get',
    timeout: 20000
  })
}
export function selectAll() {
  return request({
    url: '/userInfo/selectAll',
    headers:{
      isToken:false
    },
    method: 'get',
  })
}
