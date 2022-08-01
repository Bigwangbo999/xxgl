import request from '@/utils/request'

export function importFile(url,data) {
  return request({
    url: url,
    method: 'post',
    data: data,
    timeout: 20000
  })
}
