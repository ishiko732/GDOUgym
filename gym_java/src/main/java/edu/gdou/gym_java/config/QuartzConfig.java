package edu.gdou.gym_java.config;
import edu.gdou.gym_java.utils.MysqlQuartz;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail mysqlQuartz(){
        return JobBuilder.newJob(MysqlQuartz.class).withIdentity("mysqlQuartz").storeDurably().build();
    }

    @Bean
    public Trigger mysqlQuartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(2) //每2个小时备份一次
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(mysqlQuartz())
                .withIdentity("mysqlQuartz")
                .withSchedule(scheduleBuilder)
                .build();
    }

}