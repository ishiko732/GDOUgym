package edu.gdou.gym_java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.gdou.gym_java.entity.model.RecycleEquipment;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lzh
 * @since 2022-06-04
 */
public interface RecycleEquipmentService extends IService<RecycleEquipment> {
    //申请器材回收
    Boolean applyRecycleEquipment(RecycleEquipment recycleEquipment);

    //确认器材回收
    Boolean confirmRecycleEquipment(RecycleEquipment recycleEquipment);

    //根据id查询器材回收记录
    RecycleEquipment queryRecycleEquipment(Integer reid);

    //根据器材回收eid删除器材回收记录
    Boolean deleteRecycleEquipmentByReid(Integer reid);

    //多条件查询器材回收记录
    List<RecycleEquipment> queryRecycleEquipment(Integer reid,String name,Integer number,String type);
}
