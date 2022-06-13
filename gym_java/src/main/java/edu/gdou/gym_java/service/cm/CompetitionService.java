package edu.gdou.gym_java.service.cm;

import edu.gdou.gym_java.entity.VO.RefereeAnnouncement;
import edu.gdou.gym_java.entity.VO.TimeLimit;
import edu.gdou.gym_java.entity.model.*;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 赛事表 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-04
 */
public interface CompetitionService extends IService<Competition> {
    Map<String,Integer> createEvent(int uid, String name, Timestamp timestamp, int eventLength, Double money, String context);
    Boolean cancelEvent(int cid,int uid,String context);
    Set<Competition> queryEvents(@Nullable Integer cid, @Nullable String name, @Nullable String uname,@Nullable TimeLimit time);
    List<Integer> eventSetFields(Integer cid,List<Integer> fcIds);
    CompetitionField fieldUserLinkEvent(int cfId, int uid,String context);
    List<CompetitionEquipment> fieldEquipmentLinkEvent(int cfid,List<CompetitionEquipment> competitionEquipments);

    // TODO 收入支出查询
    boolean updateUserEvent(int cfId, int uid,String context);
    List<RefereeAnnouncement> queryRefereeAnnouncements(@Nullable Integer cid);

    Set<CompetitionField> queryCompetitionFieldByCid(Integer cid);
    Set<CompetitionEquipment> queryCompetitionEquipmentByCfid( Integer cfId);
}
