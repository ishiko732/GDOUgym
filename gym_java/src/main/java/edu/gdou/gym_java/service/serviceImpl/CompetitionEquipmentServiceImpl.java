package edu.gdou.gym_java.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gdou.gym_java.entity.model.CompetitionEquipment;
import edu.gdou.gym_java.mapper.CompetitionEquipmentMapper;
import edu.gdou.gym_java.service.CompetitionEquipmentService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lzh
 * @since 2022-06-04
 */
@Service
public class CompetitionEquipmentServiceImpl extends ServiceImpl<CompetitionEquipmentMapper, CompetitionEquipment> implements CompetitionEquipmentService {

    @Override
    public Integer queryCompositionEquipmentCountByEid(Integer eid) {
        int count = 0;
        HashMap<String, Object> map = new HashMap<>();
        map.put("eid",eid);
        List<CompetitionEquipment> CompetitionEquipments = getBaseMapper().selectByMap(map);
        for (CompetitionEquipment compositionEquipment : CompetitionEquipments) {
            count+=compositionEquipment.getNumber();
        }
        return count;
    }
}
