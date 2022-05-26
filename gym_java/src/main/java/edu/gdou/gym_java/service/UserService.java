package edu.gdou.gym_java.service;

import edu.gdou.gym_java.entity.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-05-25
 */
public interface UserService extends IService<User> {
    Boolean register(@NonNull User user);
    User getUser(@NonNull String username);
    List<User> queryManagerByUsername(String username);
}
