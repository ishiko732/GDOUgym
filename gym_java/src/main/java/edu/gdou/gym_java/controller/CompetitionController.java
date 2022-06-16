package edu.gdou.gym_java.controller;


import com.google.gson.Gson;
import edu.gdou.gym_java.entity.VO.RefereeAnnouncement;
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
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @RequiresPermissions("赛事创建(预告)")
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
        val map = competitionService.createEvent(uid_int, name, timeStamp, length, Double.valueOf(money), context);
        return new ResponseBean(200, "赛事审核id信息", map);
    }

    @RequiresPermissions("赛事取消")
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
    @RequiresPermissions("赛事查询")
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
    @RequiresPermissions("比赛场地安排")
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
    @RequiresPermissions(logical = Logical.OR,value = {"比赛场地安排","裁判简介公告"})
    public ResponseBean FieldUserLinkEvent(@RequestParam(value = "cfId")String cfId,
                                           @RequestParam(value = "uid")String uid,
                                           @RequestParam(value = "context") String context) {
        val cfid_int=Integer.parseInt(cfId);
        val competitionField = competitionService.fieldUserLinkEvent(
                cfid_int, Integer.parseInt(uid), context);
        return new ResponseBean(200,competitionField.getUid().equals(Integer.parseInt(uid))?"绑定成功":"绑定失败",competitionField);
    }

    @RequestMapping(value = "/updateUserEvent",method = RequestMethod.POST)
    @RequiresPermissions(logical = Logical.OR,value = {"比赛场地安排","裁判简介公告"})
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
    @RequiresPermissions("查询最新公告")
    public ResponseBean queryRefereeAnnouncements(@RequestParam(value = "cid",required = false)String cid) {
        List<RefereeAnnouncement> refereeAnnouncements = competitionService.queryRefereeAnnouncements(cid==null?null:Integer.parseInt(cid));
        return new ResponseBean(200,"查询裁判公告信息", refereeAnnouncements);
    }

    /**
     * 场地绑定器材
     * @param map RequestBody
     * @return Set<CompetitionEquipment> 正确插入的值
     */
    @RequestMapping(value = "/equipmentLinkField",method = RequestMethod.POST)
    @RequiresPermissions(logical = Logical.OR,value = {"比赛场地安排","赛事器材申请"})
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
    @RequestMapping(value = "/queryCheck",method = RequestMethod.GET)
    @RequiresPermissions(logical = Logical.AND,value = {"查询赛事审核"})
    public ResponseBean queryCheck(@RequestParam(value = "id",required = false)String id,
                                   @RequestParam(value = "uid",required = false)String uid,
                                   @RequestParam(value = "status",required = false)String status){
        if(id!=null){
            return new ResponseBean(200,"查询到审核数据",checkService.getById(id));
        }else if(uid==null){
            return new ResponseBean(200,"查询到审核数据",checkService.queryList(status));
        }else{
            return new ResponseBean(200,"查询到审核数据",checkService.queryListByUid(status,Integer.parseInt(uid)));
        }
    }

    @RequestMapping(value = "/check",method = RequestMethod.POST)
    @RequiresPermissions("赛事审核")
    public ResponseBean checkStatus(@RequestParam(value = "check_id") String id, @RequestParam(value = "status")String status,@RequestParam("reason") String reason){
        val user = userService.currentUser();
        val uid = user.getId();
        val aBoolean = checkService.checkStatus(Integer.parseInt(id), uid, status, reason);
        return new ResponseBean(200,"审核处理结果",aBoolean);
    }

    // 查询赛事场地
    @RequestMapping(value = "/queryCompetitionField",method = RequestMethod.GET)
    @RequiresPermissions("赛事查询")
    public ResponseBean queryCompetitionFieldByCid(@RequestParam(value = "cid",required = false) String cid){

        return new ResponseBean(200,"赛事场地列表",competitionService.queryCompetitionFieldByCid(cid==null?null:Integer.parseInt(cid)));
    }

    // 查询赛事场地-器材
    @RequestMapping(value = "/queryCompetitionEquipment",method = RequestMethod.GET)
    @RequiresPermissions("赛事查询")
    public ResponseBean queryCompetitionEquipmentByCfid(@RequestParam(value = "cfid",required = false) String cfid){
        return new ResponseBean(200,"查询赛事-场地器材列表",competitionService.queryCompetitionEquipmentByCfid(cfid==null?null:Integer.parseInt(cfid)));
    }
}
