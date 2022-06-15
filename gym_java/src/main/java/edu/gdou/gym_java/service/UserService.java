package edu.gdou.gym_java.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.gdou.gym_java.entity.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.NonNull;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-05-25
 */
public interface UserService extends IService<User> {
    User currentUser();
    Boolean register(@NonNull User user,@NonNull String id);
    User getUser(@NonNull String username);
    List<User> queryManagerByUsername(String username);
    Boolean addManager(User user);
    Boolean deleteManager(Integer ID);
    User queryUserByID(Integer ID);
    IPage<User> selectUserPage(Page<User> page);
    Boolean changePassword(@NonNull String username, @Nullable String prePassword, String newPassword, Boolean isForced);
    Map<String,Object> selectInfoByUid(@NonNull Integer id);
    Map<String,Object> selectInfoById(@NonNull String id);
    Map<String,Integer> exportInfo(List<Map<String,String>> mapList) ;
    Map<String,Integer> exportInfoByFile(MultipartFile excel);
    Set<Map<String,Object>> getUserListBySingle(Integer uid,String name,String truename);
}
