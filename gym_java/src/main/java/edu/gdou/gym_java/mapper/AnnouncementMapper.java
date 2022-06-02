package edu.gdou.gym_java.mapper;

import edu.gdou.gym_java.entity.model.Announcement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.NonNull;
import org.apache.ibatis.annotations.*;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 公告表 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-02
 */
@Mapper
public interface AnnouncementMapper extends BaseMapper<Announcement> {

    /**
     * Facility Fines Regulations
     */
    @Insert("insert into Announcement(uid, type,content) values (#{uid},#{type},#{text})")
    boolean insertAnnouncement(@Param("type") String type, @Param("uid") int uid, @Param("text") String content);

    @Update("update Announcement set content=#{text} , uid=#{uid} where aid=#{aid}")
    boolean updateAnnouncement(@Param("aid") int aid, @Param("uid") int uid, @Param("text") String content);

    @Select({
            "<script>",
            "select aid,uid,type,content,createDate,updateDate",
            "from Announcement",
            "right join (select max(aid)as base_id,max(createDate) as createtime from Announcement group by type) as max_tmp on max_tmp.base_id=aid and max_tmp.createtime=createDate",
            "<where>",
            "<if test='type !=null'>",
            "type like #{type} ",
            "</if>",
            "</where>",
            "order by createDate,updateDate,aid",
            "</script>"
    })
    Set<Announcement> queryNewAnnouncement(@Param("type") @Nullable String type);


    @Select("select * from Announcement where type like #{type} order by createDate desc ,updateDate desc")
    List<Announcement> queryAnnouncementLogs(@Param("type") @NonNull String type);

    @Select("select * from Announcement_type")
    Set<String> queryAnnouncementType();

    @Select("select exists(select aid from new_announcement where aid = #{aid}) as ret")
    boolean checkAnnouncementIsNew(@Param("aid") int aid);

}
