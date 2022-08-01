package com.ruoyi.job.service;

import com.ruoyi.job.domain.SyUserPo;
import com.ruoyi.job.repository.SsheDataSourceRepository;
import com.ruoyi.job.repository.XxglDataSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SyUserSyncService {

    private final SsheDataSourceRepository ssheDataSourceRepository;

    private final XxglDataSourceRepository xxglDataSourceRepository;


    @Autowired
    public SyUserSyncService(SsheDataSourceRepository ssheDataSourceRepository,XxglDataSourceRepository xxglDataSourceRepository){
        this.ssheDataSourceRepository=ssheDataSourceRepository;
        this.xxglDataSourceRepository=xxglDataSourceRepository;
    }

    /**
     * 员工信息同步操作
     */
    public void syUserSync(){
        //1.清空表xxgl库中syuser表中的数据
        this.xxglDataSourceRepository.deleteAllBack();

        //2.查询sshe库中syuser表中的数据
        List<SyUserPo> ssheSyUserPoList=this.ssheDataSourceRepository.findAllUserIncludeAdmin();

        //3.将sshe库中的数据插入xxgl库中
        for (SyUserPo ssheSyUserPo : ssheSyUserPoList){
            this.xxglDataSourceRepository.saveSyUser(ssheSyUserPo);
        }
    }
}