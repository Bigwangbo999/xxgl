package com.ruoyi.job.task;

import com.ruoyi.job.service.SyOrganizationSyncService;
import com.ruoyi.job.service.SyUserSyncService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class timedTaskJob {

    private static Logger logger=LoggerFactory.getLogger(timedTaskJob.class);

    private final SyUserSyncService syUserSyncService;

    private final SyOrganizationSyncService syOrganizationSyncService;

    @Autowired
    public timedTaskJob(SyUserSyncService syUserSyncService, SyOrganizationSyncService syOrganizationSyncService){
        this.syUserSyncService=syUserSyncService;
        this.syOrganizationSyncService=syOrganizationSyncService;
    }

    @Scheduled(cron = "0 0 1 ? * L") //每周日凌晨一点执行
    public synchronized void doJob1(){
        logger.info("开始执行定时任务,每周日凌晨一点同步syuser表数据");
        syUserSyncService.syUserSync();
        logger.info("定时任务执行完成");

    }
    @Scheduled(cron = "0 0 1 ? * L") //每周日凌晨一点执行
    public synchronized void doJob2(){
        logger.info("开始执行定时任务,每周日凌晨一点同步syorganization表数据");
        syOrganizationSyncService.syOrganizationSync();
        logger.info("定时任务执行完成");

    }
}
