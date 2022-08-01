package com.ruoyi.web.entity;

import lombok.Data;

/**
 * 错误信息类
 */
@Data
public class ErrResult {
    /**
     * 错误订单号
     */
    private String errDocumentNo;
    /**
     * 错误信息
     */
    private String errMsg;
}
