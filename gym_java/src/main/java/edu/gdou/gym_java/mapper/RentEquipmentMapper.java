package edu.gdou.gym_java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.gdou.gym_java.entity.model.RentEquipment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RentEquipmentMapper extends BaseMapper<RentEquipment> {
    //多条件查询器材租用记录
    List<RentEquipment> queryRentEquipment(Integer rid,Integer eid,String eName,Integer uid,
                                           String username,Integer rentTime,Integer number,Integer status);

    //根据月份查询收入
    Double generateEquipmentIncome(String year,String month);
}
