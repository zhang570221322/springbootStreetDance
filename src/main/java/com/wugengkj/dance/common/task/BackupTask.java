package com.wugengkj.dance.common.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 备份任务
 *
 * @author leaf
 * <p>date: 2018-05-07 18:52</p>
 * <p>version: 1.0</p>
 */
@Component
@Slf4j
public class BackupTask {

    @Scheduled(cron = "0 24 * * * *")
    public void backup() {
        log.debug("开始备份任务...");
    }
}
