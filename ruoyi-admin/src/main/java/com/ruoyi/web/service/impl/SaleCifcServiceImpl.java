package com.ruoyi.web.service.impl;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.web.entity.InvoiceResult;
import com.ruoyi.web.entity.SaleCifc;
import com.ruoyi.web.entity.SaleCifcs;
import com.ruoyi.web.service.SaleCifcService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class SaleCifcServiceImpl implements SaleCifcService {

    @Override
    public List<SaleCifc> selectPageDocumentByParams(SaleCifcs saleCifcs) {
        return null;
    }

    @Override
    public InvoiceResult importExcel(MultipartFile multipartFile) {
        return null;
    }

    @Override
    public AjaxResult insertSeller(SaleCifcs saleCifcs) {
        return null;
    }
}
