import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/goods/list',
    method: 'post',
    data: data
  })
}
export function insertGoods(data) {
  return request({
    url: '/goods/insertGoods',
    method: 'post',
    data: data
  })
}
export function updateGoods(data) {
  return request({
    url: '/goods/updateGoods',
    method: 'post',
    data: data
  })
}
export function deleteGoods(data) {
  return request({
    url: '/goods//deleteGoods',
    method: 'post',
    data: data
  })
}
