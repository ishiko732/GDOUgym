package edu.gdou.gym_java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.gdou.gym_java.entity.model.Equipment;
import edu.gdou.gym_java.entity.model.EquipmentRentStandard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * <p>
 * mapper接口
 * </p>
 *
 * @author lzh
 * @since 2022-06-02
 */
@Mapper
public interface EquipmentRentStandardMapper extends BaseMapper<EquipmentRentStandard> {
    //查询器材租用标准
    List<EquipmentRentStandard> queryEquipmentRentStandard();

    //根据器材id查询器材租用标准
    EquipmentRentStandard queryEquipmentRentStandardByEid(Integer eid);

}
