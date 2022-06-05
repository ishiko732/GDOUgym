package edu.gdou.gym_java.mapper;

import edu.gdou.gym_java.entity.VO.Init_Competition;
import edu.gdou.gym_java.entity.model.Competition;
import edu.gdou.gym_java.entity.model.CompetitionCheck;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    @Select({
            "<script>",
            "select *",
            "from Competition_check",
            "<where>",
            "<if test='status !=null'>",
            "status like #{status}",
            "</if>",
            "</where>",
            "</script>"
    })
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "cid",column = "cid"),
            @Result(property = "uid",column = "uid"),
            @Result(property = "competition",column = "cid",javaType= Competition.class,
                    one = @One(select = "edu.gdou.gym_java.mapper.CompetitionMapper.queryCompetitionByCid")),
    })
    List<CompetitionCheck> queryList(@Param("status") String status);

    @Select({
            "<script>",
            "select Competition_check.* from Competition_check",
            "left join Competition C on C.id = Competition_check.cid",
            "<where>",
            "<if test='uid !=null'>",
            "C.uid=#{uid}",
            "</if>",
            "<if test='status !=null'>",
            "and status like #{status}",
            "</if>",
            "</where>",
            "</script>"
    })
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "cid",column = "cid"),
            @Result(property = "uid",column = "uid"),
            @Result(property = "competition",column = "cid",javaType= Competition.class,
                    one = @One(select = "edu.gdou.gym_java.mapper.CompetitionMapper.queryCompetitionByCid")),
    })
    List<CompetitionCheck> queryListByUid(@Param("status") String status,@Param("uid") int uid);
}
