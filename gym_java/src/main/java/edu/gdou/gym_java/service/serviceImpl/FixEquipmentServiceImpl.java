package edu.gdou.gym_java.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gdou.gym_java.entity.model.Equipment;
import edu.gdou.gym_java.entity.model.FixEquipment;
import edu.gdou.gym_java.entity.model.FixEquipmentBill;
import edu.gdou.gym_java.service.EquipmentService;
import edu.gdou.gym_java.service.FixEquipmentBillService;
import edu.gdou.gym_java.service.FixEquipmentService;
import edu.gdou.gym_java.mapper.FixEquipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lzh
 * @since 2022-06-04
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class FixEquipmentServiceImpl  extends ServiceImpl<FixEquipmentMapper, FixEquipment> implements FixEquipmentService {

    @Override
    public FixEquipment queryFixEquipmentByFid(Integer fid) {
        return getBaseMapper().selectById(fid);
    }

    @Override
    public Boolean applyFixEquipment(FixEquipment fixEquipment) {
        //先判断器材维修表里有没有该器材的维修记录
        FixEquipment queryFixEquipment = queryFixEquipmentByFid(fixEquipment.getFid());
        //为空，创建一个器材维修记录,不为空，则在原来的器材维修记录添加数量
        if (queryFixEquipment==null){
            int insert = getBaseMapper().insert(fixEquipment);
            return insert==1;
        }else{
            queryFixEquipment.setNumber(queryFixEquipment.getNumber()+fixEquipment.getNumber());
            int update = getBaseMapper().updateById(queryFixEquipment);
            return update==1;
        }
    }

    @Override
    public Boolean confirmFixEquipment(FixEquipment fixEquipment) {
        //判断确认维修的数量是否小于原数量，若等于，则删除维修记录；若小于，则减去对应的器材维修的数量
        FixEquipment queryFixEquipment = queryFixEquipmentByFid(fixEquipment.getFid());
        if (queryFixEquipment==null){
            return false;
        }else{
            if(queryFixEquipment.getNumber()==fixEquipment.getNumber()){
                int delete = getBaseMapper().deleteById(fixEquipment.getFid());
                return (delete==1);
            }else if(queryFixEquipment.getNumber()>fixEquipment.getNumber()){
                queryFixEquipment.setNumber(queryFixEquipment.getNumber()-fixEquipment.getNumber());
                int update = getBaseMapper().updateById(queryFixEquipment);
                return (update==1);
            }else{
                return false;
            }
        }
    }

    @Override
    public Integer queryFixEquipmentCountByFid(Integer fid) {
        FixEquipment fixEquipment = getBaseMapper().selectById(fid);
        return fixEquipment!=null?fixEquipment.getNumber():0;
    }

    @Override
    public List<FixEquipment> queryFixEquipment(Integer fid, String name, Integer number, String type) {
        return getBaseMapper().queryFixEquipment(fid,name,number,type);
    }

    @Override
    public Boolean deleteFixEquipmentByFid(Integer fid) {
        return getBaseMapper().deleteById(fid)==1;
    }
}
