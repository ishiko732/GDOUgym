package edu.gdou.gym_java.service.serviceImpl.cm;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import edu.gdou.gym_java.entity.VO.Init_Competition;
import edu.gdou.gym_java.entity.model.CompetitionCheck;
import edu.gdou.gym_java.mapper.CompetitionCheckMapper;
import edu.gdou.gym_java.service.cm.CompetitionCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 赛事审核表 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-04
 */
@Service
public class CompetitionCheckServiceImpl extends ServiceImpl<CompetitionCheckMapper, CompetitionCheck> implements CompetitionCheckService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer init_check(int cid) {
        val initCompetition = new Init_Competition(cid);
        val initCheck = getBaseMapper().init_check(initCompetition);
        return initCompetition.getId();
    }

    @Override
    public CompetitionCheck queryByCid(int cid) {
        return getBaseMapper().queryByCid(cid);
    }

    @Override
    public List<CompetitionCheck> queryList(String status) {
        return getBaseMapper().queryList(status);
    }

    @Override
    public List<CompetitionCheck> queryListByUid(String status,int uid) {
        return getBaseMapper().queryListByUid(status,uid);
    }

    @Override
    public Boolean checkStatus(Integer id, Integer uid,String status, String reason) {
        val updateWrapper = new UpdateWrapper<CompetitionCheck>();
        updateWrapper.eq("id",id).set("status",status).set("uid",uid).set("reason",reason);
        return getBaseMapper().update(null, updateWrapper)!=0;
    }
}
