package edu.gdou.gym_java.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gdou.gym_java.entity.model.FixEquipmentBill;
import edu.gdou.gym_java.mapper.FixEquipmentBillMapper;

import edu.gdou.gym_java.service.FixEquipmentBillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class FixEquipmentBillServiceImpl extends ServiceImpl<FixEquipmentBillMapper, FixEquipmentBill> implements FixEquipmentBillService {
    @Override
    public Boolean addFixEquipmentBill(FixEquipmentBill fixEquipmentBill) {
        return getBaseMapper().insert(fixEquipmentBill)==1;
    }

    @Override
    public Double generateEquipmentOutcome(String year, String month) {
        return getBaseMapper().generateEquipmentOutcome(year,month);
    }
}
