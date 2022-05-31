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
    public void testDel() {
        val delete = userService.getBaseMapper().deleteById(3);
        System.out.println(delete);
    }

    @Test
    public void testMap() {
        val map = userService.selectInfoByUid(1);
        System.out.println(map.keySet());
        /*
          ==>  Preparing: select * from UserInfo where uid=?
          ==> Parameters: 1(Integer)
          <==    Columns: uid, name, id, phone
          <==        Row: 1, superAdmin2, 001, null
          <==      Total: 1
          [uid, name, id]
         */
    }

}