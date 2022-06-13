package edu.gdou.gym_java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.gdou.gym_java.entity.model.RentEquipment;

import java.util.List;

public interface RentEquipmentService extends IService<RentEquipment> {
    //添加器材租用记录
    Boolean addRentEquipment(RentEquipment rentEquipment);

    //根据rid查询器材租用记录
    RentEquipment queryRentEquipmentByEid(Integer eid);

    //多条件查询器材租用记录
    List<RentEquipment> queryRentEquipment(Integer rid, Integer eid, String eName, Integer uid,
                                           String username, Integer rentTime, Integer number,Integer status);

    //根据月份查询收入
    Double generateEquipmentIncome(String year,String month);

    //归还器材
    Boolean redeemEquipment(int rid);

    //根据器材id和status查询正在被租用的器材数量
    Integer queryRentEquipmentAvailableCount(Integer id);

    List<RentEquipment> queryRentEquipmentByUid(Integer id);
}
