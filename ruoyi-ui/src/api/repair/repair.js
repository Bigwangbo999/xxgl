import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/repair/list',
    method: 'post',
    data: data
  })
}

// 查询销方详细
export function getSeller(Id) {
  return request({
    url: '/repair/selectSeller/' + Id,
    method: 'get'
  })
}

// 修改销方详细
export function updateSeller(data) {
  return request({
    url: '/repair/updateSeller',
    method: 'post',
    data: data
  })
}



// 修改销方详细
export function insertSeller(data) {
  return request({
    url: '/repair/insertSeller',
    method: 'post',
    data: data
  })
}

//批量删除
export function deleteInfo(data) {
  return request({
    url: '/repair/deleteInfo',
    method: 'post',
    data:data
  })
}
