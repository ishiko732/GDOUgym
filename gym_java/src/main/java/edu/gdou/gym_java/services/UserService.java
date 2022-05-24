package edu.gdou.gym_java.services;

import edu.gdou.gym_java.entity.User;
import edu.gdou.gym_java.utils.DataSource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    public User getUser(String username) {
        // 没有此用户直接返回null
        if (! DataSource.getData().containsKey(username))
            return null;

        User user = new User();
        Map<String, String> detail = DataSource.getData().get(username);

        user.setUsername(username);
        user.setPassword(detail.get("password"));
        user.setRole(detail.get("role"));
        user.setPermission(detail.get("permission"));
        return user;
    }
}