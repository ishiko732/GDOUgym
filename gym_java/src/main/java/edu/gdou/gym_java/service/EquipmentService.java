package edu.gdou.gym_java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.gdou.gym_java.entity.model.Equipment;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lzh
 * @since 2022-06-02
 */
public interface EquipmentService extends IService<Equipment> {
    //根据name、types、number查询器材
    List<Equipment> queryEquipment(String name, String types, Integer number);

    //新添器材
    Boolean addEquipment(Equipment equipment);

    //器材可使用数量
    Integer availableEquipmentCount(Integer eid);

    //根据eid查询器材
    Equipment queryEquipmentByEid(Integer eid);

    //修改器材
    Boolean updateEquipment(Equipment equipment);
}
