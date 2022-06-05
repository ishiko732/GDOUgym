package edu.gdou.gym_java.service.cm;

import edu.gdou.gym_java.entity.model.CompetitionEquipment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 赛事器材表 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-04
 */
public interface CompetitionEquipmentService extends IService<CompetitionEquipment> {
    //根据器材id查询所有的赛事器材租用数量
    Integer queryCompositionEquipmentCountByEid(Integer eid);
}
