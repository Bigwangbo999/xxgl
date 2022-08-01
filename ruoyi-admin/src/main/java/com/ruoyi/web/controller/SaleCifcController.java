package com.ruoyi.web.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.model.LoginUser;
import com.ruoyi.web.entity.InvoiceResult;
import com.ruoyi.web.entity.SaleCifc;
import com.ruoyi.web.entity.SaleCifcs;
import com.ruoyi.web.mapper.InvoiceRecordMapper;
import com.ruoyi.web.service.SaleCifcService;
import com.ruoyi.web.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/invoice")
@Slf4j
public class SaleCifcController extends BaseController {


    @Autowired
    private SaleCifcService saleCifcService;

    @Autowired
    private InvoiceRecordMapper invoiceRecordMapper;

    /**
     * 查询未开票列表
     *
     * @param saleCifcs 查询条件
     * @return 分页发票主体集合
     * @since 2022年7月22日15:06:06
     * @author wangbo
     */
    @PostMapping("/list")
    @PreAuthorize("@ss.hasPermi('*:*:*')")
    public TableDataInfo getDocumentLists(@RequestBody SaleCifcs saleCifcs){
        LoginUser loginUser= SecurityUtils.getLoginUser();
        String cust_code=invoiceRecordMapper.selectUserCustCodeById(loginUser.getUserid());
        startPage();
        saleCifcs.setCust_code(cust_code);
        List<SaleCifc> list=saleCifcService.selectPageDocumentByParams(saleCifcs);
        return getDataTable(list);
    }

    /**
     * 下载开票Excel文件
     */
    @RequestMapping(value = "/downloadExcel",method = RequestMethod.GET)
    @PreAuthorize("@ss.hasPermi('*:*:*')")
    @Log(title = "下载订单文件",businessType = BusinessType.OTHER)
    public void downloadExcel(HttpServletResponse response){
      String relativePath="file";
      String fileName="document1.xlsx";
        OutputStream outputStream =null;
        InputStream inputStream=null;
        try {
            inputStream = FileUtil.getInputStream(relativePath,fileName);
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
            outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            response.flushBuffer();
        } catch (IOException e) {
            log.error("文件下载错误:",e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }





    /**
     * 导入Excel
     * @param multipartFile 文件
     * @return 操作成功
     */
    @PostMapping("/importExcel")
    @Log(title = "导入Excel",businessType = BusinessType.IMPORT)
    public AjaxResult importExcel(@RequestParam("file") MultipartFile multipartFile) {
        try {
            InvoiceResult invoiceResult = saleCifcService.importExcel(multipartFile);
            return AjaxResult.success(invoiceResult);
        } catch (Exception e) {
            log.error("导入异常：{}", e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除发票
     * @param Ids 发票主题主体集合
     * @return 操作成功
     */
    @Log(title = "删除发票",businessType = BusinessType.DELETE)
    @PostMapping("/deleteInfo")
    public AjaxResult deleteInfo(@RequestBody List<Long> Ids) {
        invoiceRecordMapper.deleteInfo(Ids);
        return AjaxResult.success();
    }

    /**
     * 新增发票信息  <br>
     * @version: 1.0 <br>
     * @since: 2022/5/31 21:55 <br>
     * @author: duyuhao <br>
     *
     * @param
     * @return
     */
    @PostMapping("/insertSeller")
    @Log(title = "新增发票信息",businessType = BusinessType.INSERT)
    public AjaxResult insertSaleCifc(@RequestBody SaleCifcs saleCifcs) {

        try {
            saleCifcService.insertSeller(saleCifcs);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    /**
     * 修改商品信息
     *
     * @param saleCifcs 商品信息
     * @return 操作成功
     */
    @PostMapping("/updateSaller")
    @Log(title = "修改商品信息",businessType = BusinessType.UPDATE)
    public AjaxResult updateGoods(@RequestBody SaleCifcs saleCifcs) {
        try {
            return saleCifcService.updateSaller(saleCifcs);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AjaxResult.error();
        }
    }
}
