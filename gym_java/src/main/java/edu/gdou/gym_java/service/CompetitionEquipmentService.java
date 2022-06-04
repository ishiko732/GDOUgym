package edu.gdou.gym_java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.gdou.gym_java.entity.model.CompetitionEquipment;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lzh
 * @since 2022-06-04
 */
public interface CompetitionEquipmentService extends IService<CompetitionEquipment> {
    //根据器材id查询所有的赛事器材租用数量
    Integer queryCompositionEquipmentCountByEid(Integer eid);
}
