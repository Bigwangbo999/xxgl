package com.ruoyi.web.service;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.web.entity.InvoiceResult;
import com.ruoyi.web.entity.SaleCifc;
import com.ruoyi.web.entity.SaleCifcs;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface SaleCifcService {
     List<SaleCifc> selectPageDocumentByParams(SaleCifcs saleCifcs);


     /**
      * 下载导入模板
      * @param response HttpServletResponse
      */
     void downloadExcel(HttpServletResponse response);
     /**
      * 导入文件
      * @param multipartFile
      * @return
      */
     InvoiceResult importExcel(MultipartFile multipartFile);

     /**
      * 新增发票信息
      * @param saleCifcs
      * @return
      */
     AjaxResult insertSeller(SaleCifcs saleCifcs);

     /**
      * 修改发票信息
      * @param saleCifcs
      * @return
      */
     AjaxResult updateSaller(SaleCifcs saleCifcs);
}
