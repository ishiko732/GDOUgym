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

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lzh
 * @since 2022-06-04
 */
@Service
public class RecycleEquipmentServiceImpl extends ServiceImpl<RecycleEquipmentMapper, RecycleEquipment> implements RecycleEquipmentService {
    @Autowired
    private EquipmentService equipmentService;

    @Override
    public Boolean applyRecycleEquipment(RecycleEquipment recycleEquipment) {
        return getBaseMapper().insert(recycleEquipment)==1;
    }

    @Override
    public Boolean confirmRecycleEquipment(RecycleEquipment recycleEquipment) {
        RecycleEquipment recycle = queryRecycleEquipment(recycleEquipment.getReid());
        Equipment equipment = equipmentService.queryEquipmentByEid(recycleEquipment.getReid());
        if (recycle==null){
            return false;
        }else{
            if(recycle.getNumber()==recycleEquipment.getNumber()){
                int delete = getBaseMapper().deleteById(recycle.getReid());
                if (delete==1){
                    equipment.setNumber(equipment.getNumber()+recycleEquipment.getNumber());
                    Boolean flag = equipmentService.updateEquipmentCount(equipment);
                    return flag;
                }else{
                    return false;
                }
            }else if(recycle.getNumber()>recycleEquipment.getNumber()){
                recycle.setNumber(recycle.getNumber()-recycleEquipment.getNumber());
                int update = getBaseMapper().updateById(recycle);
                if(update==1){
                    equipment.setNumber(equipment.getNumber()+recycleEquipment.getNumber());
                    Boolean flag = equipmentService.updateEquipmentCount(equipment);
                    return flag;
                }else{
                    return false;
                }
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
}
