package edu.gdou.gym_java.service;

import edu.gdou.gym_java.GymJavaApplication;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = GymJavaApplication.class)
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Test
    public void testDel(){
        val delete = userService.getBaseMapper().deleteById(3);
        System.out.println(delete);
    }

}