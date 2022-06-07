package edu.gdou.gym_java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.gdou.gym_java.entity.model.RecycleEquipment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecycleEquipmentMapper extends BaseMapper<RecycleEquipment> {
    //多条件查询器材回收记录
    List<RecycleEquipment> queryRecycleEquipment(Integer reid,String name,Integer number,String type);
}
