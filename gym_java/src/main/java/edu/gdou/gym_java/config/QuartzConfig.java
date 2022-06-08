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
                .withIntervalInSeconds(10)  //设置时间周期单位秒
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(mysqlQuartz())
                .withIdentity("mysqlQuartz")
                .withSchedule(scheduleBuilder)
                .build();
    }

}