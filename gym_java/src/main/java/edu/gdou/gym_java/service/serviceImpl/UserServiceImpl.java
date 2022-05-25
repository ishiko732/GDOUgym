package edu.gdou.gym_java.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gdou.gym_java.entity.model.User;
import edu.gdou.gym_java.mapper.UserMapper;
import edu.gdou.gym_java.service.UserService;
import edu.gdou.gym_java.testDemo.test;
import edu.gdou.gym_java.utils.MD5;
import edu.gdou.gym_java.utils.SpringContextHolder;
import lombok.NonNull;
import lombok.val;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-05-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(test.class);
    private final MD5 md5;

    public UserServiceImpl() {
        md5 = new MD5();
    }

    @Override
    public Boolean register(User user) {
        val md5_password = md5.md5(user.getPassword());
        user.setPassword(md5_password);
        val insert = getBaseMapper().insert(user);
        return insert == 1;
    }

    @Override
    public User getUser(@NonNull String username) {
        final User user= getBaseMapper().getUserByName(username);
        if (Objects.isNull(user)){
            return null;
        }
        return user;
    }

}
