package edu.gdou.gym_java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.gdou.gym_java.entity.model.FixEquipmentBill;

public interface FixEquipmentBillService extends IService<FixEquipmentBill> {
    //新增器材维修账单
    Boolean addFixEquipmentBill(FixEquipmentBill fixEquipmentBill);

    //根据年月份查询支出
    Double generateEquipmentOutcome(String year,String month);
}
