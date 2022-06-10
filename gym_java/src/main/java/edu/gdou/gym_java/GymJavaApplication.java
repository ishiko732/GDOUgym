package edu.gdou.gym_java;

import edu.gdou.gym_java.utils.SpringContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("edu.gdou.gym_java.mapper")
public class GymJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymJavaApplication.class, args);
    }

    @Bean
    public SpringContextHolder springContextHolder(){
        return new SpringContextHolder();
    }
}
