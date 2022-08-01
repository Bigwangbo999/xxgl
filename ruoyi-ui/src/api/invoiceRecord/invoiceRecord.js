import request from '@/utils/request'

export function selectInvoiceRecordByPage(data) {
  return request({
    url: '/invoiceRecord/selectInvoiceRecordByPage',
    method: 'post',
    data: data
  })
}

export function reopen(data) {
  return request({
    url: '/invoiceRecord/reopen',
    method: 'post',
    data: data
  })
}


export function updateSelect() {
  return request({
    url: '/schedule/updateSelect',
    method: 'get'
  })
}
export function downPDF(query) {
  return request({
    url: 'invoiceRecord/downLoadPDF?' + "url=" + query,   //后端接口的地址
    method: 'get',
    responseType: "blob", //这行必写
    headers: { "Content-Type": "multipart/form-data" } //这行必写
  })
}
export function downLOadPDFToZIP(query) {
  return request({
    url: 'invoiceRecord/downLOadPDFToZIP',   //后端接口的地址
    method: 'post',
    data: query,
    responseType: "blob", //这行必写
    headers: { 'Content-Type': 'application/json; application/octet-stream' } //这行必写
  })
}
export function updateTask(query) {
  return request({
    url: 'schedule/updateTask',   //后端接口的地址
    method: 'get',
  })
}
//确认交付
export function deliver(data) {
  return request({
    url: '/invoiceRecord/deliver',
    method: 'post',
    data
  })
}
