package edu.gdou.gym_java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.gdou.gym_java.entity.model.FixEquipment;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lzh
 * @since 2022-06-04
 */
public interface FixEquipmentService  extends IService<FixEquipment> {
    //根据fid查询维修器材
    FixEquipment queryFixEquipmentByFid(Integer fid);

    //申请器材报修
    Boolean applyFixEquipment(FixEquipment fixEquipment);

    //确认器材保修完毕
    Boolean confirmFixEquipment(FixEquipment fixEquipment);

    //根据器材id查询所有的器材维修数量
    Integer queryFixEquipmentCountByFid(Integer fid);

    //多条件查询器材维修记录
    List<FixEquipment> queryFixEquipment(Integer fid,String name,Integer number,String type);

    //根据fid删除器材回收记录
    Boolean deleteFixEquipmentByFid(Integer fid);
}
