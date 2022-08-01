package com.ruoyi.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 开票查询实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleCifcs {

    /**
     * 税号或酒店编号
     */
    private String cust_code;

    /***
     * 订单类型
     */
    private String order_type;

    /**
     * 价目表
     */
    private String price_list;

    /**
     * 销售员
     */
    private String saler;

    /**
     * 销售类型
     */
    private String sale_type;

    /**
     * 发票类型
     */
    private String invoice_type;

    /**
     * 发票代码
     */
    private String invoice_code;

    /**
     * 发票号码
     */
    private String invoice_value;

    /**
     * 开票状态
     */
    private Number status;
}
