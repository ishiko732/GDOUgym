package edu.gdou.gym_java.mapper;

import edu.gdou.gym_java.entity.VO.Init_Competition;
import edu.gdou.gym_java.entity.model.CompetitionCheck;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

/**
 * <p>
 * 赛事审核表 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-04
 */
@Mapper
public interface CompetitionCheckMapper extends BaseMapper<CompetitionCheck> {

    @Insert("insert into Competition_check(cid) values(#{cid})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int init_check(Init_Competition initCompetition);

    @Select("select * from Competition_check where cid=#{cid}")
    CompetitionCheck queryByCid(@Param("cid") int cid);
}
