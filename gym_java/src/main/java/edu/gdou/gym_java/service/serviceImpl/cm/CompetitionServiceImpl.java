package edu.gdou.gym_java.service.serviceImpl.cm;

import edu.gdou.gym_java.entity.model.Competition;
import edu.gdou.gym_java.entity.model.CompetitionEquipment;
import edu.gdou.gym_java.entity.model.CompetitionField;
import edu.gdou.gym_java.entity.model.FieldCheck;
import edu.gdou.gym_java.mapper.CompetitionMapper;
import edu.gdou.gym_java.service.cm.CompetitionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
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
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition> implements CompetitionService {
    @Override
    public Integer createEvent(int uid, String name, Timestamp timestamp, int eventLength, String content) {
        return null;
    }

    @Override
    public Integer cancelEvent(int cid, int uid, String content) {
        return null;
    }

    @Override
    public List<Competition> queryEvents(Integer cid) {
        return null;
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
