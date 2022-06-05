package edu.gdou.gym_java.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GsonConfig {
    @Bean
    public Gson gson(){
        return new Gson();
    }
}
