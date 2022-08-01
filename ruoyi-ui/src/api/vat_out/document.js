import request from '@/utils/request'
// 新查询当前用户部门信息
export function getDocumentLists(data) {
  return request({
    url: '/invoice/list?pageSize=' + data.pageSize + "&pageNum=" + data.pageNum,
    method: 'post',
    data
  })
}

// 修改发票详细
export function insertSeller(data) {
  return request({
    url: '/invoice/insertSeller',
    method: 'post',
    data: data
  })
}


//删除订单信息提交后台
export function deleteDocumentList(data) {
  return request({
    url: '/invoice/document/deleteDocumentList',
    method: 'post',
    data
  })
}

//流水信息提交后台匹配
export function openInvoice(data) {
  return request({
    url: '/uninvoice/match',
    method: 'post',
    data
  })
}

export function deleteInfo(data) {
  return request({
    url: '/invoice/deleteInfo',
    method: 'post',
    data: data
  })
}
export function openTicket(data) {
  return request({
    url: '/uninvoice/openTicket',
    method: 'post',
    data: data
  })
}
export function selectDetail(data) {
  return request({
    url: '/invoice/selectDetail',
    method: 'post',
    data: data
  })
}


export function redInvoice(data) {
  return request({
    url: '/uninvoice/redInvoice',
    method: 'post',
    data: data
  })
}
//批量重开
export function reopenByBatch(data) {
  return request({
    url: '/uninvoice/reopenByBatch',
    method: 'post',
    data: data
  })
}
//批量打印(纸质)
export function printPDF(data) {
  return request({
    url: '/invoiceRecord/printPDF',
    method: 'post',
    data: data
  })
}

export function insertSaleCifc(data){
  return request({
    url: '/invoice/insertSeller',
    method: 'post',
    data: data
  })
}

// export function updateSaleCifc(data){
//   return request({
//     url: '/invoice/updateSeller',
//     method: 'post',
//     data: data
//   })
// }
