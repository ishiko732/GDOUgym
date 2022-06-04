package edu.gdou.gym_java.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gdou.gym_java.entity.model.FixEquipment;
import edu.gdou.gym_java.service.FixEquipmentService;
import edu.gdou.gym_java.mapper.FixEquipmentMapper;
import org.springframework.stereotype.Service;

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
                return delete==1;
            }else if(queryFixEquipment.getNumber()>fixEquipment.getNumber()){
                queryFixEquipment.setNumber(queryFixEquipment.getNumber()-fixEquipment.getNumber());
                int update = getBaseMapper().updateById(queryFixEquipment);
                return update==1;
            }else{
                return false;
            }
        }
    }

    @Override
    public Integer queryFixEquipmentCountByFid(Integer fid) {
        int count = 0;
        HashMap<String, Object> map = new HashMap<>();
        map.put("fid",fid);
        List<FixEquipment> fixEquipments = getBaseMapper().selectByMap(map);
        for (FixEquipment fixEquipment : fixEquipments) {
            count+=fixEquipment.getNumber();
        }
        return count;
    }
}
