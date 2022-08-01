package com.ruoyi.web.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;

import java.sql.Blob;
import java.util.Date;

/**
 * 销售信息实体
 */
@Data
@TableName("CUX_SCJS_SALE_CIFC")
public class SaleCifc {
    @TableId(value = "id",type = IdType.AUTO)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Excel(name = "序号")
    private String id;
    private String org_code;
    private Number org_id;
    @Excel(name = "税号或酒店编号")
    private String cust_code;
    @Excel(name = "订单类型")
    private String order_type;
    @Excel(name = "价目表")
    private String price_list;
    @Excel(name = "销售员")
    private String saler;
    @Excel(name = "销售类型")
    private String sale_type;
    @Excel(name = "收款类型")
    private String receipt_type;
    @Excel(name = "发票类型")
    private String invoice_type;
    @Excel(name = "合同编号")
    private String contract;
    @Excel(name = "发票代码")
    private String invoice_code;
    @Excel(name = "发票号码")
    private String invoice_value;
    @Excel(name = "物料编码")
    private String invent_code;
    @Excel(name = "税率")
    private Number rote;
    @Excel(name = "价格")
    private Number price;
    private String card_id;
    @JsonFormat(pattern ="yyyy-MM-dd" )
    @Excel(name = "服务起始日",dateFormat = "yyyy-MM-dd")
    private Date sdate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "服务结束日",dateFormat = "yyyy-MM-dd")
    private Date ndate;
    @Excel(name = "大类")
    private String catory_one;
    @Excel(name = "中类")
    private String catory_two;
    @Excel(name = "小类")
    private String catory_three;
    @Excel(name = "状态")
    private Number status;
    private String cust_name;
    private String header_id;
    private String order_number;
    private String msg;
    private Number import_by;
    private Date import_date;
    private String contract_number;
    @Excel(name = "备注")
    private String notes;
    private Number txt_flag;
    private String main_id;
    private Number iskp;
    private String kpxx;
    private String userid;
    private String back_value;
    @Excel(name = "客户分机号")
    private Number fjh;
    private String data_source;
    private Date sync_in_date;
    private Date rksj;
    private String import_id;
    private Number kpcs;
    private String url;
    private Blob pdfbase64;
    private Number type;
    private Number zbhjse;
    private Number zbhjje;
}
