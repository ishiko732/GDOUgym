package edu.gdou.gym_java.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gdou.gym_java.entity.model.Equipment;
import edu.gdou.gym_java.entity.model.Role;
import edu.gdou.gym_java.mapper.EquipmentMapper;
import edu.gdou.gym_java.mapper.RoleMapper;
import edu.gdou.gym_java.service.EquipmentService;
import edu.gdou.gym_java.service.RoleService;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lzh
 * @since 2022-06-02
 */

@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment> implements EquipmentService {

    @Override
    public List<Equipment> queryEquipment(String name, String types, Integer number) {
        return getBaseMapper().queryEquipment(name,types,number);
    }

    @Override
    public Boolean addEquipment(Equipment equipment) {
        int insert = getBaseMapper().insert(equipment);
        return insert==1;
    }
}
