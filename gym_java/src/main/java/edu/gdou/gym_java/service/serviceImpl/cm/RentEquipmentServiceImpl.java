package edu.gdou.gym_java.service.serviceImpl.cm;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gdou.gym_java.entity.model.RentEquipment;
import edu.gdou.gym_java.mapper.RentEquipmentMapper;
import edu.gdou.gym_java.service.RentEquipmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentEquipmentServiceImpl extends ServiceImpl<RentEquipmentMapper, RentEquipment> implements RentEquipmentService {
    @Override
    public Boolean addRentEquipment(RentEquipment rentEquipment) {
        return getBaseMapper().insert(rentEquipment)==1;
    }

    @Override
    public RentEquipment queryRentEquipmentByEid(Integer eid) {
        return getBaseMapper().selectById(eid);
    }

    @Override
    public List<RentEquipment> queryRentEquipment(Integer rid, Integer eid, String eName, Integer uid, String username, Integer rentTime, Integer number) {
        return getBaseMapper().queryRentEquipment(rid,eid,eName,uid,username,rentTime,number);
    }

    @Override
    public Double generateEquipmentIncome(String year, String month) {
        return getBaseMapper().generateEquipmentIncome(year,month);
    }
}
