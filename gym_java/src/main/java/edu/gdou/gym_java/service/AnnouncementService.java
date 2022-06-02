package edu.gdou.gym_java.service;

import edu.gdou.gym_java.entity.model.Announcement;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.NonNull;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 公告表 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-02
 */
public interface AnnouncementService extends IService<Announcement> {
    boolean insertAnnouncement(String type, int uid, String content);

    boolean updateAnnouncement(int aid, int uid, String content);

    Set<Announcement> queryNewAnnouncement(@Param("type")@Nullable String type);

    List<Announcement> queryAnnouncementLogs(@Param("type") @NonNull String type);

    Set<String> queryAnnouncementType();

    boolean checkAnnouncementIsNew(@Param("aid") int aid);
}
