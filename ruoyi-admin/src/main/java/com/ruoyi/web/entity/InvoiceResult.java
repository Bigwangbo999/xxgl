package com.ruoyi.web.entity;

import lombok.Data;

import java.util.ArrayList;

@Data
public class InvoiceResult {

    /**
     * 成功总条数
     */
    private Integer sucTotal;

    /**
     * 失败总条数
     */
    private Integer errTotal;

    /**
     * 失败的详细信息
     */
    private ArrayList<ErrResult> errResults;
}
