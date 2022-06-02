package edu.gdou.gym_java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.gdou.gym_java.entity.model.Equipment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 器材表 Mapper 接口
 * </p>
 *
 * @author lzh
 * @since 2022-06-02
 */
@Mapper
public interface EquipmentMapper extends BaseMapper<Equipment> {
    //根据name、types、number查询器材
    List<Equipment> queryEquipment(String name,String types,Integer number);

}
