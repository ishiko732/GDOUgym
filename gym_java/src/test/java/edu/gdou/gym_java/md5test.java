package edu.gdou.gym_java;

import edu.gdou.gym_java.utils.MD5;
import edu.gdou.gym_java.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class md5test {
    @Test
    public void md5_Create(){
        MD5 md5 = SpringContextHolder.getBean(MD5.class);
        log.info(md5.md5("gdougdou"));
    }
}
