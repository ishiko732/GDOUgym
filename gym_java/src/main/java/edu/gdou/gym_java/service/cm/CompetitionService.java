package edu.gdou.gym_java.service.cm;

import edu.gdou.gym_java.entity.model.*;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.List;
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
    Integer createEvent(int uid, String name, Timestamp timestamp,int eventLength,Double money,String context);
    Boolean cancelEvent(int cid,int uid,String context);
    Set<Competition> queryEvents(@Nullable Integer cid);
    Integer eventSetField(Set<Integer> fcIds);
    CompetitionField FieldUserLinkEvent(int cid, FieldCheck field, int uid,@Nullable String content);
    Boolean FieldEquipmentLinkEvent(Set<CompetitionEquipment> competitionEquipments);

    // TODO 裁判简介公告 收入支出查询


}
