package com.ruoyi.job.service;

import com.ruoyi.job.domain.SyOrganizationPo;
import com.ruoyi.job.domain.SyUserPo;
import com.ruoyi.job.repository.SsheDataSourceRepository;
import com.ruoyi.job.repository.XxglDataSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SyOrganizationSyncService {

    private final SsheDataSourceRepository ssheDataSourceRepository;

    private final XxglDataSourceRepository xxglDataSourceRepository;

    @Autowired
    public SyOrganizationSyncService(SsheDataSourceRepository ssheDataSourceRepository, XxglDataSourceRepository xxglDataSourceRepository) {
        this.ssheDataSourceRepository = ssheDataSourceRepository;
        this.xxglDataSourceRepository = xxglDataSourceRepository;
    }

    /**
     * 同步操作
     */
    public void syOrganizationSync() {

        //1.清空表xxgl库中syorganization表中的数据
        this.xxglDataSourceRepository.deleteAllOrganizationBack();

        //2.查询sshe库中syorganization表中的数据
        List<SyOrganizationPo> ssheSyOrganizationPoList = this.ssheDataSourceRepository.findAllOrganizationIncludeAdmin();

        //3.将sshe库中的数据插入xxgl库中
        for (SyOrganizationPo syOrganizationPo : ssheSyOrganizationPoList) {
            this.xxglDataSourceRepository.saveSyorganization(syOrganizationPo);
        }
    }
}