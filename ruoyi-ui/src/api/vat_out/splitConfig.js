import request from '@/utils/request'
// 查询消息列表
export function getSplitConfig() {
  return request({
    url: '/splitConfig/getInfo',
    method: 'get',
  })
}

// 保存拆分配置
export function saveOrUpdate(data) {
  return request({
    url: '/splitConfig/saveOrUpdate',
    method: 'post',
    data
  })
}
