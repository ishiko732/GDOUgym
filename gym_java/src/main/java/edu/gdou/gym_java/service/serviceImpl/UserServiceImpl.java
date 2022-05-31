package edu.gdou.gym_java.service.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.gdou.gym_java.entity.model.User;
import edu.gdou.gym_java.mapper.UserMapper;
import edu.gdou.gym_java.service.UserService;
import edu.gdou.gym_java.testDemo.test;
import edu.gdou.gym_java.utils.JWTUtil;
import edu.gdou.gym_java.utils.MD5;
import edu.gdou.gym_java.utils.SpringContextHolder;
import lombok.NonNull;
import lombok.val;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public UserServiceImpl(MD5 md5) {
        this.md5 = md5;
    }

    @Override
    public User currentUser() {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String username = JWTUtil.getUsername(token);
        if (username!=null){
            User user = getUser(username);
            user.setPassword(null);
            return user;
        }else{
            return null;
        }
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
        final User user = getBaseMapper().getUserByName(username);
        if (Objects.isNull(user)) {
            return null;
        }
        return user;
    }

    @Override
    public List<User> queryManagerByUsername(String username) {
        return getBaseMapper().queryManagerByName(username);
    }

    @Override
    public Boolean addManager(User user) {
        val md5_password = md5.md5(user.getPassword());
        user.setPassword(md5_password);
        val insert = getBaseMapper().insert(user);
        return insert == 1;
    }

    @Override
    public Boolean deleteManager(Integer ID) {
        val delete = getBaseMapper().deleteById(ID);
        return delete == 1;
    }

    @Override
    public User queryUserByID(Integer ID) {
        return getBaseMapper().selectById(ID);
    }

    @Override
    public IPage<User> selectUserPage(Page<User> page) {
        return getBaseMapper().selectPageUsers(page);
    }

    @Override
    public Boolean changePassword(@NonNull String username, @Nullable String prePassword, String newPassword, Boolean isForced) {
        User user = getUser(username);
        String pre_md5 = "";
        String new_md5 = "";
        if (isForced) {
            pre_md5=user.getPassword();
        }else if (prePassword!=null){
            pre_md5=md5.md5(prePassword);
        }else{
            return false;
        }
        new_md5 = md5.md5(newPassword);

        if (user.getPassword().equals(pre_md5)){
            user.setPassword(new_md5);
            return getBaseMapper().updateById(user)!=0;
        }else{
            return false;
        }

    }
}
