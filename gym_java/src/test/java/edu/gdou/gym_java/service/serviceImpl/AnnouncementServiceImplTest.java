package edu.gdou.gym_java.service.serviceImpl;

import edu.gdou.gym_java.GymJavaApplication;
import edu.gdou.gym_java.service.AnnouncementService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GymJavaApplication.class)
@Slf4j
class AnnouncementServiceImplTest {
//    @Autowired
//    AnnouncementService announcementService;
//    @Test
//    void insertAnnouncement() {
////        for (int i = 1; i <= 5; i++) {
////            val ret=announcementService.insertAnnouncement("馆内罚款","1","测试公告"+i);
////            log.info("test"+i+":"+ret);
////        }
//        val ret=announcementService.insertAnnouncement("馆内罚款",1,"测试公告");
//    }
//
//    @Test
//    void updateAnnouncement() {
//        for (int i = 1; i <= 5; i++) {
//            val ret=announcementService.updateAnnouncement(i,1,"(new)测试公告"+i);
//            log.info("test"+i+":"+ret);
//        }
//    }
//
//    @Test
//    void queryNewAnnouncement() {
////        System.out.println(announcementService.queryNewAnnouncement(null));
//        System.out.println(announcementService.queryNewAnnouncement("馆内罚款"));
//    }
//
//    @Test
//    void queryAnnouncementLogs() {
//        System.out.println(announcementService.queryAnnouncementLogs("馆内罚款"));
//    }
}