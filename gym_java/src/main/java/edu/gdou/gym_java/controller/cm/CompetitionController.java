package edu.gdou.gym_java.controller.cm;


import com.google.gson.Gson;
import edu.gdou.gym_java.entity.VO.TimeLimit;
import edu.gdou.gym_java.entity.bean.ResponseBean;
import edu.gdou.gym_java.entity.model.CompetitionEquipment;
import edu.gdou.gym_java.service.FieldService;
import edu.gdou.gym_java.service.UserService;
import edu.gdou.gym_java.service.cm.CompetitionCheckService;
import edu.gdou.gym_java.service.cm.CompetitionService;
import edu.gdou.gym_java.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * <p>
 * 赛事表 前端控制器
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-04
 */
@RestController
@Slf4j
@RequestMapping("/competition")
public class CompetitionController {
    private final UserService userService;
    private final CompetitionService competitionService;
    private final CompetitionCheckService checkService;
    private final FieldService fieldService;
    private final Gson gson;



    public CompetitionController(UserService userService, CompetitionService competitionService, CompetitionCheckService checkService, FieldService fieldService, Gson gson) {
        this.userService = userService;
        this.competitionService = competitionService;
        this.checkService = checkService;
        this.fieldService = fieldService;
        this.gson = gson;
    }



    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseBean createEvent(@RequestParam(value = "uid", required = false) String uid,
                                    @Valid @RequestParam("event_name") String name,
                                    @Valid @RequestParam(value = "event_time") String time,
                                    @Valid @RequestParam("event_length") Integer length,
                                    @Valid @RequestParam("money") String money,
                                    @RequestParam("context") String context) {
        val user = userService.currentUser();
        int uid_int;
        if ((("AM").equals(user.getRole().getInfo()) || ("SM").equals(user.getRole().getInfo()))&&uid!=null) {
            uid_int = Integer.parseInt(uid);
        } else {
            uid_int = user.getId();
        }
        val timeStamp = TimeUtils.StringToTimeStamp(time);
        val cid = competitionService.createEvent(uid_int, name, timeStamp, length, Double.valueOf(money), context);
        return new ResponseBean(200, "赛事审核id信息", cid);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseBean cancelEvent(@RequestParam("cid") String cid,
                                    @RequestParam(value = "uid", required = false) String uid,
                                    @RequestParam("context") String context) {
        val user = userService.currentUser();
        int uid_int;
        if ((("AM").equals(user.getRole().getInfo()) || ("SM").equals(user.getRole().getInfo()))&&uid!=null) {
            uid_int = Integer.parseInt(uid);
        } else {
            uid_int = user.getId();
        }
        int cid_int = Integer.parseInt(cid);
        val cancelEvent = competitionService.cancelEvent(cid_int, uid_int, context);
        if (cancelEvent==null){
            return new ResponseBean(200, "赛事信息不存在", null);
        }
        return new ResponseBean(200, cancelEvent?"取消成功":"取消失败", cancelEvent);
    }

    @RequestMapping(value = "/queryEvent", method = RequestMethod.GET)
    public ResponseBean queryEvents(@RequestParam(value = "cid",required = false) Integer cid,
                                    @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "uname",required = false) String uname,
                                    @RequestParam(value = "start_time",required = false) String start,
                                    @RequestParam(value = "end_time",required = false) String end){
        // TODO 缺少赛事地点
        TimeLimit timeLimit =null;
        if(start!=null && end!=null){
            timeLimit = new TimeLimit(TimeUtils.StringToTimeStamp(start), TimeUtils.StringToTimeStamp(end));
        }
        val competitions = competitionService.queryEvents(cid, name, uname, timeLimit);
        return new ResponseBean(200,competitions!=null?"获取到数据":"未获取到数据",competitions);
    }

    /**
     * 绑定场地
     * @param map RequestBody
     * @return 成功数信息
     */
    @RequestMapping(value = "/eventSetFields",method = RequestMethod.POST)
    public ResponseBean eventSetFields(@RequestBody Map<String,Object> map){
        val cid = Integer.parseInt(String.valueOf(map.get("cid")));
        val ids = (List<Integer>)map.get("ids"); //这个是在审核中的场地信息
        val integers = competitionService.eventSetFields(cid, ids);
        return new ResponseBean(200,"返回成功绑定的赛事场地id信息",integers);
    }

    /**
     * 场地绑定裁判
     * @param cfId 赛事场地id
     * @param uid 用户id
     * @param context 简介
     * @return CompetitionField
     */
    @RequestMapping(value = "/userLinkField",method = RequestMethod.POST)
    public ResponseBean FieldUserLinkEvent(@RequestParam(value = "cfId")String cfId,
                                           @RequestParam(value = "uid")String uid,
                                           @RequestParam(value = "context") String context) {
        val cfid_int=Integer.parseInt(cfId);
        val competitionField = competitionService.fieldUserLinkEvent(
                cfid_int, Integer.parseInt(uid), context);
        return new ResponseBean(200,competitionField.getUid().equals(cfid_int)?"绑定成功":"绑定失败",competitionField);
    }

    @RequestMapping(value = "/updateUserEvent",method = RequestMethod.POST)
    public ResponseBean updateUserEvent(@RequestParam(value = "cfId")String cfId,
                                           @RequestParam(value = "uid")String uid,
                                           @RequestParam(value = "context") String context) {
        val cfid_int=Integer.parseInt(cfId);
        val isBoolean = competitionService.updateUserEvent(cfid_int, Integer.parseInt(uid), context);
        return new ResponseBean(200,isBoolean?"绑定成功":"绑定失败",isBoolean);
    }

    /**
     * 查询裁判公告
     * @param cid 赛事id
     * @return ResponseBean
     */
    @RequestMapping(value = "/queryRefereeAnnouncements",method = RequestMethod.GET)
    public ResponseBean queryRefereeAnnouncements(@RequestParam(value = "cid",required = false)String cid) {
        if(cid==null){
            return new ResponseBean(200,"查询裁判公告信息", competitionService.queryRefereeAnnouncements(null));
        }else{
            return new ResponseBean(200,"查询裁判公告信息", competitionService.queryRefereeAnnouncements(Integer.parseInt(cid)));
        }
    }

    /**
     * 场地绑定器材
     * @param map RequestBody
     * @return Set<CompetitionEquipment> 正确插入的值
     */
    @RequestMapping(value = "/equipmentLinkField",method = RequestMethod.POST)
    public ResponseBean fieldEquipmentLinkEvent(@RequestBody Map<String,Object> map) {
        val cfid = Integer.parseInt(String.valueOf(map.get("cfId")));
        val objectList =gson.fromJson(gson.toJson(map.get("equipment")),List.class);
        val competitionEquipments = new ArrayList<CompetitionEquipment>();
        for (Object obj : objectList) {
            competitionEquipments.add(gson.fromJson(gson.toJson(obj), CompetitionEquipment.class));
        }

        val equipments = competitionService.fieldEquipmentLinkEvent(cfid, competitionEquipments);
        return new ResponseBean(200,"绑定器材数"+competitionEquipments.size(),equipments);
    }

    // 审核部分
    @RequestMapping("/queryCheck")
    public ResponseBean queryCheck(@RequestParam(value = "uid",required = false)String uid,
                                   @RequestParam(value = "status",required = false)String status){
        if(uid==null){
            return new ResponseBean(200,"查询到审核数据",checkService.queryList(status));
        }else{
            return new ResponseBean(200,"查询到审核数据",checkService.queryListByUid(status,Integer.parseInt(uid)));
        }
    }

}
