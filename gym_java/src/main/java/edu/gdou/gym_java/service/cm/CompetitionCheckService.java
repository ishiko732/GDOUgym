package edu.gdou.gym_java.service.cm;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import edu.gdou.gym_java.entity.model.CompetitionCheck;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    List<CompetitionCheck> queryList(String status);
    List<CompetitionCheck> queryListByUid(String status,int uid);
    Boolean checkStatus(Integer id, Integer uid,String status, String reason);
}
