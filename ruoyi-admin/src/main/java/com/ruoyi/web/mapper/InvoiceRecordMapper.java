package com.ruoyi.web.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvoiceRecordMapper {

    /**
     * 查询税号或酒店编号
     * @param userid
     * @return
     */
    String selectUserCustCodeById(Long userid);

    /**
     * 批量删除发票主体
     * @param ids
     */
    void deleteInfo(@Param(value = "list") List<Long> ids);

}
