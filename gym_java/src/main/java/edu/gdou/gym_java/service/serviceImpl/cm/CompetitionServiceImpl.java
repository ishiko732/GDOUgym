package edu.gdou.gym_java.service.serviceImpl.cm;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import edu.gdou.gym_java.entity.VO.TimeLimit;
import edu.gdou.gym_java.entity.enums.CheckStatus;
import edu.gdou.gym_java.entity.model.*;
import edu.gdou.gym_java.mapper.CompetitionMapper;

import edu.gdou.gym_java.service.UserService;
import edu.gdou.gym_java.service.cm.CompetitionCancelService;
import edu.gdou.gym_java.service.cm.CompetitionCheckService;
import edu.gdou.gym_java.service.cm.CompetitionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;

/**
 * <p>
 * 赛事表 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-04
 */
@Service
@Slf4j
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition> implements CompetitionService {
    private final CompetitionCheckService checkService;
    private final CompetitionCancelService cancelService;
    private final UserService userService;

    public CompetitionServiceImpl(CompetitionCheckService checkService, CompetitionCancelService cancelService, UserService userService) {
        this.checkService = checkService;
        this.cancelService = cancelService;
        this.userService = userService;
    }

    /**
     * 创建赛事
     *
     * @param uid         uid
     * @param name        赛事名称
     * @param timestamp   赛事时间
     * @param eventLength 赛事长度
     * @param context     赛事内容
     * @return 赛事id
     */
    @Override
    public Integer createEvent(int uid, String name, Timestamp timestamp, int eventLength, Double money, String context) {
        val competition = new Competition(null, uid, name, timestamp, eventLength, context, money, null, null, null, null);
        val insert = getBaseMapper().insert_competition(competition);
        if (insert) {
            return checkService.init_check(competition.getId());
        } else {
            return null;
        }
    }

    @Override
    public Boolean cancelEvent(int cid, int uid, String context) {
        val competitions = queryEvents(cid, null, null, null);
        if (competitions.size() == 0) {
            return null;
        }
        val competition = competitions.iterator().next();
        if (competition.getIsCancel()){
            return false;
        }
        val check = checkService.queryByCid(cid);
        if (check != null && !check.getStatus().equals(CheckStatus.PASSED.getStatus())) {
            UpdateWrapper<CompetitionCheck> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", check.getId()).set("status", CheckStatus.CANCELLED.getStatus());
            checkService.update(null, updateWrapper);
        }
        val cancel = new CompetitionCancel(null, cid, uid, context, null);
        return cancelService.getBaseMapper().insert(cancel) == 1;
    }

    @Override
    public Set<Competition> queryEvents(Integer cid, String name, String uname, TimeLimit time) {
        Integer uid =null;
        if(uname!=null){
            val user = userService.getUser(uname);
            if (user!=null){
                uid = user.getId();
            }
        }
        return getBaseMapper().queryCompetition(cid,name,uid,time);
    }

    @Override
    public Integer eventSetField(Set<Integer> fcIds) {
        return null;
    }

    @Override
    public CompetitionField FieldUserLinkEvent(int cid, FieldCheck field, int uid, String content) {
        return null;
    }

    @Override
    public Boolean FieldEquipmentLinkEvent(Set<CompetitionEquipment> competitionEquipments) {
        return null;
    }
}
