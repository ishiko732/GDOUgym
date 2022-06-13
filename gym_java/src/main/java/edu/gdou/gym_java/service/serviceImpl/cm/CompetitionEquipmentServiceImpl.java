package edu.gdou.gym_java.service.serviceImpl.cm;

import edu.gdou.gym_java.entity.model.CompetitionEquipment;
import edu.gdou.gym_java.mapper.CompetitionEquipmentMapper;
import edu.gdou.gym_java.service.cm.CompetitionEquipmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 赛事器材表 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CompetitionEquipmentServiceImpl extends ServiceImpl<CompetitionEquipmentMapper, CompetitionEquipment> implements CompetitionEquipmentService {
    @Override
    public Integer queryCompositionEquipmentCountByEid(Integer eid) {
        int count = 0;
        HashMap<String, Object> map = new HashMap<>();
        map.put("eid",eid);
        List<CompetitionEquipment> CompetitionEquipments = getBaseMapper().selectByMap(map);
        if (CompetitionEquipments.size()!=0){
            for (CompetitionEquipment compositionEquipment : CompetitionEquipments) {
                count+=compositionEquipment.getNumber();
            }
        }
        return count;
    }
}
