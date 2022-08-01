package com.ruoyi.web.entity;

import lombok.Data;



@Data
public class UserInfoSelect {

    /**
     * 税号
     */
    private String cust_code;

    /**
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
     * 收款类型
     */
    private String receipt_type;

    /**
     * 发票类型
     */
    private String invoice_type;

    /**
     * 合同编号
     */
    private String contract;

    /**
     * 发票代码
     */
    private String invoice_code;

    /**
     * 发票号码
     */
    private String invoice_value;

    /**
     * 物料编码
     */
    private String invent_code;

    /**
     * 税率
     */
    private String rote;

    /**
     * 价格
     */
    private String price;

    /**
     * 备注
     */
    private String notes;

    /**
     * 状态
     */
    private String status;
}
