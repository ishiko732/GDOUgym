package edu.gdou.gym_java.utils;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.File;

@Slf4j
public class MysqlQuartz extends QuartzJobBean {
    /**
     * 执行定时任务
     */
    @Value("${spring.datasource.username}")
    private String db;
    @Value("${quartz.ip}")
    private String ip;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Override
    protected void executeInternal(JobExecutionContext context)throws JobExecutionException {
        log.info("========执行定时任务(备份数据库)========");
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String filePath="sqlQuartz/";

        File uploadDir=new File(filePath);
        if(!uploadDir.exists()){
            uploadDir.mkdirs();
        }

        String cmd =  "mysqldump -h "+ip+" -u "+ username +" -p'"+password+"' --default-character-set=utf8 "+ db + " -r "
                + filePath+ db+TimeUtils.nowToTimeStamp().getTime()+ ".sql";
        try{
            Process process=Runtime.getRuntime().exec(cmd);
            log.info("数据库备份成功");
        }catch(Exception e){
            log.error("备份失败:{}",e.getMessage());
        }
        log.info("=======执行定时任务(备份数据库)结束======");
    }
}
