package edu.gdou.gym_java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.gdou.gym_java.entity.model.FixEquipment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 器材维修表 Mapper 接口
 * </p>
 *
 * @author lzh
 * @since 2022-06-04
 */
@Mapper
public interface FixEquipmentMapper  extends BaseMapper<FixEquipment> {
    //多条件查询器材维修记录
    List<FixEquipment> queryFixEquipment(Integer fid,String name,Integer number,String type);
}
