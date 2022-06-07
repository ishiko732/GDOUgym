package edu.gdou.gym_java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.gdou.gym_java.entity.model.FixEquipmentBill;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FixEquipmentBillMapper extends BaseMapper<FixEquipmentBill> {
    //根据年月份查询支出
    Double generateEquipmentOutcome(String year,String month);
}
