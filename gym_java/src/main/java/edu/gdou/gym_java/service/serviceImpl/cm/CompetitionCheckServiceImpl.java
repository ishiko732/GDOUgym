package edu.gdou.gym_java.service.serviceImpl.cm;

import edu.gdou.gym_java.entity.VO.Init_Competition;
import edu.gdou.gym_java.entity.model.CompetitionCheck;
import edu.gdou.gym_java.mapper.CompetitionCheckMapper;
import edu.gdou.gym_java.service.cm.CompetitionCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.val;
import org.springframework.stereotype.Service;

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
    public Integer init_check(int cid) {
        val initCompetition = new Init_Competition(cid);
        val initCheck = getBaseMapper().init_check(initCompetition);
        return initCompetition.getId();
    }

    @Override
    public CompetitionCheck queryByCid(int cid) {
        return getBaseMapper().queryByCid(cid);
    }
}
