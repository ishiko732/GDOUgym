package edu.gdou.gym_java.mapper;

import edu.gdou.gym_java.entity.VO.RefereeAnnouncement;
import edu.gdou.gym_java.entity.VO.TimeLimit;
import edu.gdou.gym_java.entity.model.Announcement;
import edu.gdou.gym_java.entity.model.Competition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.gdou.gym_java.entity.model.CompetitionField;
import org.apache.ibatis.annotations.*;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 赛事表 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-04
 */
@Mapper
public interface CompetitionMapper extends BaseMapper<Competition> {
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into Competition(uid, name, competition_time, event_length, introduction, money) " +
            "values (#{uid}, #{name}, #{competitionTime}, #{eventLength}, #{introduction}, #{money} )")
    Boolean insert_competition(Competition competition);


    @Select({
            "<script>",
            "select Competition.*,Ccheck.status as 'isCheck'," ,
            "exists(select id from Competition_cancel where cid=Competition.id) as 'isCancel'",
            "from Competition",
            "left join Competition_check Ccheck on Ccheck.cid=Competition.id",
            "<where>",
            "<if test='cid !=null'>",
            "Competition.id = #{cid}",
            "</if>",
            "<if test='name !=null'>",
            "and Competition.name like concat('%',#{name},'%')",
            "</if>",
            "<if test='uid !=null'>",
            "and Competition.uid = #{uid}",
            "</if>",
            "<if test='time !=null'>",
            "and (Competition.competition_time between #{time.start} and #{time.end}) ",
            "and (timestampadd(minute ,60,Competition.competition_time) between #{time.start} and #{time.end})",
            "</if>",
            "</where>",
            "order by create_time,uid,id,name",
            "</script>"
    })
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "isCheck",column = "isCheck"),
            @Result(property = "isCancel",column = "isCancel"),
    })
    Set<Competition> queryCompetition(@Param("cid") @Nullable Integer cid, @Param("name")@Nullable String name, @Param("uid") Integer uid,@Nullable TimeLimit time);


    @Select({
            "<script>",
            "select *",
            "from referee_announcements",
            "<where>",
            "<if test='cid !=null'>",
            "cid=#{cid}",
            "</if>",
            "</where>",
            "</script>"
    })
    List<RefereeAnnouncement> queryRefereeAnnouncements(@Param("cid") Integer cid);

}
