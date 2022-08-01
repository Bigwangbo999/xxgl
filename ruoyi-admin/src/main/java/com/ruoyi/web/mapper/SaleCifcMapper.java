package com.ruoyi.web.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.entity.SaleCifc;
import com.ruoyi.web.entity.UserInfoSelect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SaleCifcMapper extends BaseMapper<SaleCifc> {
    /**
     * 查询
     * @param userNames
     * @return
     */
    List<UserInfoSelect> selectUserInfo(@Param(value = "userNames")List<String> userNames);

    /**
     * 匹配后批量修改主体购房信息
     * @param updateList 修改的主体信息集合
     */
    void updateInfo(@Param(value = "list") List<SaleCifc> updateList);

    /**
     *  查询匹配失败的发票主体
     * @return 发票主题list
     */
    List<SaleCifc> selectMatch();

    /**
     *  查询发票主体信息通过主体ID
     * @param ids 主体ID集合
     * @return 发票主体List
     */
    List<SaleCifc> selectByIds(@Param(value = "ids")List<Long> ids);

    /**
     * 根据银行流水查询主体信息
     * @param documentNo 银行流水
     * @return 发票主题集合
     */
    List<SaleCifc> selectByBank(String documentNo);

    /**
     * 批量插入发票主体
     * @param cuxScjsSaleCifcs 发票主体信息
     */
    void insertAllUninvo(@Param(value = "list") List<SaleCifc> cuxScjsSaleCifcs);

}
