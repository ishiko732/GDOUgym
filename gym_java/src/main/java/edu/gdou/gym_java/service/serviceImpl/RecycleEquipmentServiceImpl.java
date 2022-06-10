package edu.gdou.gym_java.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gdou.gym_java.entity.model.Equipment;
import edu.gdou.gym_java.entity.model.FixEquipment;
import edu.gdou.gym_java.entity.model.RecycleEquipment;
import edu.gdou.gym_java.mapper.RecycleEquipmentMapper;
import edu.gdou.gym_java.service.EquipmentService;
import edu.gdou.gym_java.service.RecycleEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(rollbackFor = Exception.class)
public class RecycleEquipmentServiceImpl extends ServiceImpl<RecycleEquipmentMapper, RecycleEquipment> implements RecycleEquipmentService {
    @Autowired
    private EquipmentService equipmentService;

    @Override
    public Boolean applyRecycleEquipment(RecycleEquipment recycleEquipment) {
        RecycleEquipment recycle = queryRecycleEquipment(recycleEquipment.getReid());
        if (recycle==null){
            return getBaseMapper().insert(recycleEquipment)==1;
        }else{
            recycle.setNumber(recycle.getNumber()+recycleEquipment.getNumber());
            return getBaseMapper().updateById(recycle)==1;
        }
    }

    @Override
    public Boolean confirmRecycleEquipment(RecycleEquipment recycleEquipment) {
        //删除器材回收，增加原本器材数量
        Equipment equipment = equipmentService.queryEquipmentByEid(recycleEquipment.getReid());
        if (recycleEquipment==null){
            return false;
        }else{
            int delete = getBaseMapper().deleteById(recycleEquipment.getReid());
            if (delete==1){
                equipment.setNumber(equipment.getNumber()+recycleEquipment.getNumber());
                Boolean flag = equipmentService.updateEquipment(equipment);
                return flag;
            }else{
                return false;
            }
        }
    }

    @Override
    public RecycleEquipment queryRecycleEquipment(Integer reid) {
        return getBaseMapper().selectById(reid);
    }

    @Override
    public Boolean deleteRecycleEquipmentByReid(Integer reid) {
        return getBaseMapper().deleteById(reid)==1;
    }

    @Override
    public List<RecycleEquipment> queryRecycleEquipment(Integer reid, String name, Integer number, String type) {
        return getBaseMapper().queryRecycleEquipment(reid,name,number,type);
    }
}
