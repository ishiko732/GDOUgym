package edu.gdou.gym_java.service.serviceImpl;

import edu.gdou.gym_java.entity.model.Announcement;
import edu.gdou.gym_java.mapper.AnnouncementMapper;
import edu.gdou.gym_java.service.AnnouncementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 公告表 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-06-02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {
    @Override
    public boolean insertAnnouncement(String type, int uid, String content) {
        return getBaseMapper().insertAnnouncement(type, uid, content);
    }

    @Override
    public boolean updateAnnouncement(int aid, int uid, String content) {
        return getBaseMapper().updateAnnouncement(aid,uid,content);
    }

    @Override
    public Set<Announcement> queryNewAnnouncement(@Nullable String type) {
        return getBaseMapper().queryNewAnnouncement(type);
    }

    @Override
    public List<Announcement> queryAnnouncementLogs(@NonNull String type) {
        return getBaseMapper().queryAnnouncementLogs(type);
    }

    @Override
    public Set<String> queryAnnouncementType() {
        return getBaseMapper().queryAnnouncementType();
    }

    @Override
    public boolean checkAnnouncementIsNew(int aid) {
        return getBaseMapper().checkAnnouncementIsNew(aid);
    }
}
