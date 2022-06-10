package edu.gdou.gym_java.service.serviceImpl.cm;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.gson.Gson;
import edu.gdou.gym_java.entity.VO.RefereeAnnouncement;
import edu.gdou.gym_java.entity.VO.TimeLimit;
import edu.gdou.gym_java.entity.enums.CheckStatus;
import edu.gdou.gym_java.entity.model.*;
import edu.gdou.gym_java.mapper.CompetitionMapper;

import edu.gdou.gym_java.service.UserService;
import edu.gdou.gym_java.service.cm.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gdou.gym_java.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

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
@Transactional(rollbackFor = Exception.class)
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition> implements CompetitionService {
    private final CompetitionCheckService checkService;
    private final CompetitionCancelService cancelService;
    private final CompetitionFieldService fieldService;
    private final CompetitionEquipmentService equipmentService;
    private final UserService userService;
    private final Gson gson;

    public CompetitionServiceImpl(CompetitionCheckService checkService, CompetitionCancelService cancelService, UserService userService, Environment environment, CompetitionFieldService fieldService, CompetitionEquipmentService equipmentService, Gson gson) {
        this.checkService = checkService;
        this.cancelService = cancelService;
        this.userService = userService;
        this.fieldService = fieldService;
        this.equipmentService = equipmentService;
        this.gson = gson;
    }

    /**
     * 创建赛事
     * @param uid         uid
     * @param name        赛事名称
     * @param timestamp   赛事时间
     * @param eventLength 赛事长度
     * @param context     赛事内容
     * @return 赛事id
     */
    @Override
    public Map<String, Integer> createEvent(int uid, String name, Timestamp timestamp, int eventLength, Double money, String context) {
        val competition = new Competition(null, uid, name, TimeUtils.TimeStampToString(timestamp), eventLength, context, money, null, null, null, null);
        val insert = getBaseMapper().insert_competition(competition);
        if (insert) {
            val check_id = checkService.init_check(competition.getId());
            val map = new HashMap<String, Integer>();
            map.put("cid", competition.getId());
            map.put("check_id",check_id);
            return map;
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

    /**
     * 绑定场地
     * @param cid 赛事id
     * @param fcIds 场地审核id
     * @return 成功数
     */
    @Override
    public List<Integer> eventSetFields(Integer cid, List<Integer> fcIds) {
        val integers = new ArrayList<Integer>();
        for (Integer fcId : fcIds) {
            val competitionField = new CompetitionField();
            competitionField.setCid(cid);
            competitionField.setFcId(fcId);
            val selectMap = new HashMap<String, Object>();
            selectMap.put("cid",cid);
            selectMap.put("fcId",fcId);
            val field = fieldService.getBaseMapper().selectByMap(selectMap);
            if(field==null||field.isEmpty()){
                val insert = fieldService.getBaseMapper().insert(competitionField);
                if(insert!=0){
                    integers.add(competitionField.getId());
                }
            }
        }
        return integers;
    }


    /**
     * 场地绑定裁判
     * @param cfId 赛事场地id
     * @param uid 用户id
     * @param context 简介
     * @return CompetitionField
     */
    @Override
    public CompetitionField fieldUserLinkEvent(int cfId, int uid, String context) {
        val updateWrapper = new UpdateWrapper<CompetitionField>();
        updateWrapper.eq("id",cfId).set("uid",uid).set("introduction",context);
        fieldService.getBaseMapper().update(null,updateWrapper);
        return fieldService.getById(cfId);
    }

    @Override
    public boolean updateUserEvent(int cfId, int uid, String context) {
        val competitionField = fieldUserLinkEvent(cfId, uid, context);
        return competitionField.getIntroduction().equals(context);
    }

    @Override
    public List<RefereeAnnouncement> queryRefereeAnnouncements(Integer cid) {
        return getBaseMapper().queryRefereeAnnouncements(cid);
    }

    /**
     * 场地绑定器材
     * @param cfid 赛事场地id
     * @param competitionEquipments 器材-1对多
     * @return Set<CompetitionEquipment> 正确插入的值
     */
    @Override
    public List<CompetitionEquipment> fieldEquipmentLinkEvent(int cfid,List<CompetitionEquipment> competitionEquipments) {
        for (CompetitionEquipment competitionEquipment : competitionEquipments) {
            competitionEquipment.setId(null);
            competitionEquipment.setCfid(cfid);
            if (competitionEquipment.getNumber()>0){
                val map = new HashMap<String, Object>();
                map.put("cfid",cfid);
                map.put("eid",competitionEquipment.getEid());
                val select = equipmentService.getBaseMapper().selectByMap(map);
                if (select!=null&&select.size()>0){
                    val equipment = select.iterator().next();
                    val updateWrapper = new UpdateWrapper<CompetitionEquipment>();
                    val num = competitionEquipment.getNumber();
                    updateWrapper.eq("id",equipment.getId()).set("number", num);
                    if(equipmentService.getBaseMapper().update(null,updateWrapper)!=0){
                        competitionEquipment.setId(equipment.getId());
                        competitionEquipment.setNumber(num);
                    }
                }else{
                    equipmentService.getBaseMapper().insert(competitionEquipment);
                }
            }
        }
        log.info(competitionEquipments.toString());
        competitionEquipments.removeIf(competitionEquipment -> competitionEquipment.getId()==null);
        return competitionEquipments;
    }

    @Override
    public Set<CompetitionField> queryCompetitionFieldByCid(Integer cid) {
        return getBaseMapper().queryCompetitionFieldByCid(cid);
    }

    @Override
    public Set<CompetitionEquipment> queryCompetitionEquipmentByCfid(Integer cfId) {
        return getBaseMapper().queryCompetitionEquipmentByCfid(cfId);
    }
}
