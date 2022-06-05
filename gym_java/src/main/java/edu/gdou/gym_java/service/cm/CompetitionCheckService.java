package edu.gdou.gym_java.service.cm;

import edu.gdou.gym_java.entity.model.CompetitionCheck;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 赛事审核表 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-04
 */
public interface CompetitionCheckService extends IService<CompetitionCheck> {
    Integer init_check(int cid);
    CompetitionCheck queryByCid(int cid);
}