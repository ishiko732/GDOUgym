package edu.gdou.gym_java.controller.cm;


import edu.gdou.gym_java.entity.bean.ResponseBean;
import edu.gdou.gym_java.service.UserService;
import edu.gdou.gym_java.service.cm.CompetitionCheckService;
import edu.gdou.gym_java.service.cm.CompetitionService;
import edu.gdou.gym_java.utils.StringTimeStampUtils;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 赛事表 前端控制器
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-04
 */
@RestController
@RequestMapping("/competition")
public class CompetitionController {
    private final UserService userService;
    private final CompetitionService competitionService;

    public CompetitionController(UserService userService, CompetitionService competitionService) {
        this.userService = userService;
        this.competitionService = competitionService;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseBean createEvent(@RequestParam(value = "uid",required = false) String uid,
                                    @Valid @RequestParam("event_name")String name,
                                    @Valid @RequestParam(value = "event_time")String time,
                                    @Valid @RequestParam("event_length")  Integer length,
                                    @Valid @RequestParam("money") String money,
                                    @RequestParam("context") String context){
        val user = userService.currentUser();
        int uid_int ;
        if (("AM").equals(user.getRole().getInfo())||("SM").equals(user.getRole().getInfo())){
            uid_int = Integer.parseInt(uid);
        }else{
            uid_int = user.getId();
        }
        val timeStamp = StringTimeStampUtils.StringToTimeStamp(time);
        val cid = competitionService.createEvent(uid_int, name, timeStamp, length, Double.valueOf(money), context);
        return new ResponseBean(200,"赛事审核id信息",cid);
    }

}
