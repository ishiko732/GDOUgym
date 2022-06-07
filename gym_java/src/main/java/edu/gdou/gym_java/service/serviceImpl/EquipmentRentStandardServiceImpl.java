package edu.gdou.gym_java.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gdou.gym_java.entity.model.Equipment;
import edu.gdou.gym_java.entity.model.EquipmentRentStandard;
import edu.gdou.gym_java.mapper.EquipmentMapper;
import edu.gdou.gym_java.mapper.EquipmentRentStandardMapper;
import edu.gdou.gym_java.service.EquipmentRentStandardService;
import edu.gdou.gym_java.service.EquipmentService;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lzh
 * @since 2022-06-02
 */
@Service
public class EquipmentRentStandardServiceImpl extends ServiceImpl<EquipmentRentStandardMapper, EquipmentRentStandard> implements EquipmentRentStandardService {
    @Override
    public List<EquipmentRentStandard> queryEquipmentRentStandard() {
        return getBaseMapper().queryEquipmentRentStandard();
    }

    @Override
    public EquipmentRentStandard queryEquipmentRentStandardByEid(Integer eid) {
        return getBaseMapper().queryEquipmentRentStandardByEid(eid);
    }

    @Override
    public Boolean addEquipmentRentStandard(EquipmentRentStandard equipmentRentStandard) {
        return getBaseMapper().insert(equipmentRentStandard)==1;
    }
}
