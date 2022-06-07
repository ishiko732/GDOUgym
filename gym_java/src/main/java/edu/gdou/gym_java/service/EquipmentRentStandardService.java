package edu.gdou.gym_java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.gdou.gym_java.entity.model.EquipmentRentStandard;

import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lzh
 * @since 2022-06-02
 */
public interface EquipmentRentStandardService extends IService<EquipmentRentStandard> {
    //查询所有器材租用标准
    List<EquipmentRentStandard> queryEquipmentRentStandard();

    //根据器材id查询器材租用标准
    EquipmentRentStandard queryEquipmentRentStandardByEid(Integer eid);

    //新增器材租用标准
    Boolean addEquipmentRentStandard(EquipmentRentStandard equipmentRentStandard);
}
