package edu.gdou.gym_java.mapper;

import edu.gdou.gym_java.entity.model.Competition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

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
}
