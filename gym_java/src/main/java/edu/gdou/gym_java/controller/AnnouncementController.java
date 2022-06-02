package edu.gdou.gym_java.controller;


import edu.gdou.gym_java.entity.bean.ResponseBean;
import edu.gdou.gym_java.service.AnnouncementService;
import edu.gdou.gym_java.service.UserService;
import lombok.val;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * <p>
 * 公告表 前端控制器
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final UserService userService;

    public AnnouncementController(AnnouncementService announcementService, UserService userService) {
        this.announcementService = announcementService;
        this.userService = userService;
    }

    /**
     * 查询公告类型种类
     * @return ResponseBean
     */
    @RequestMapping(value = "/type", method = {RequestMethod.GET})
    public ResponseBean announcementType() {
        val types = announcementService.queryAnnouncementType();
        return new ResponseBean(200, "公告种类名称", types);
    }

    /**
     * 创建新公告
     * @param type 公告类型
     * @param content 公告内容
     * @return ResponseBean
     */
    @RequestMapping(value = "/insertAnnouncement", method = {RequestMethod.POST})
    public ResponseBean insertAnnouncement(@RequestParam("type") String type,
                                           @RequestParam("content") String content) {
        val uid = userService.currentUser().getId();
        val ret = announcementService.insertAnnouncement(type, uid, content);
        return new ResponseBean(200, ret ? "创建公告成功": "创建公告失败", null);
    }

    /**
     * 修改公告
     * @param aid 公告ID——可不指定（id，type必须指定一个）
     * @param type 公告类型——可不指定 （id，type必须指定一个）
     * @param content 公告内容
     * @return ResponseBean
     */
    @RequestMapping(value = "/updateAnnouncement", method = {RequestMethod.POST})
    public ResponseBean updateAnnouncement(@RequestParam(value = "aid",required = false) String aid,
                                           @RequestParam(value = "type",required = false) String type,
                                           @RequestParam("content") String content) {
        if(aid==null && type == null){
            return new ResponseBean(200, "未指定公告", null);
        }
        val uid = userService.currentUser().getId();
        boolean ret= false;
        if (aid!=null){
            ret =announcementService.updateAnnouncement(Integer.parseInt(aid),uid,content);
        }else{
            val announcements = announcementService.queryNewAnnouncement(type);
            if(!announcements.isEmpty()){
                val first = announcements.iterator().next();
                ret = announcementService.updateAnnouncement(first.getAid(),uid,content);
            }
        }
        return new ResponseBean(200, ret ? "修改公告成功": "修改公告失败", null);
    }

    /**
     * 查询最新的公告
     * @param type 公告类型
     * @return ResponseBean
     */
    @RequestMapping(value = "/queryNewAnnouncement", method = {RequestMethod.GET})
    public ResponseBean queryNewAnnouncement(@RequestParam(value = "type",required = false) String type) {
        val dates = announcementService.queryNewAnnouncement(type);
        return new ResponseBean(200, "最新公告", dates);
    }

    /**
     * 查询某公告的历史记录
     * @param type 公告类型
     * @return ResponseBean
     */
    @RequestMapping(value = "/queryAnnouncementLogs", method = {RequestMethod.GET})
    public ResponseBean queryAnnouncementLogs(@RequestParam(value = "type",required = false) String type) {
        if(Objects.isNull(type)){
            return new ResponseBean(200, "未指定公告", null);
        }
        val dates = announcementService.queryAnnouncementLogs(type);
        return new ResponseBean(200, "公告历史", dates);
    }

}
