package edu.gdou.gym_java.controller;


import edu.gdou.gym_java.entity.bean.ResponseBean;
import edu.gdou.gym_java.entity.model.User;
import edu.gdou.gym_java.service.RoleService;
import edu.gdou.gym_java.service.UserService;
import edu.gdou.gym_java.testDemo.test;
import edu.gdou.gym_java.utils.JWTUtil;
import edu.gdou.gym_java.utils.MD5;
import lombok.val;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-05-25
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(test.class);
    private final UserService userService;
    private final RoleService roleService;
    private final MD5 md5;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
        this.md5 = new MD5();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseBean login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        User user = userService.getUser(username);
        if (Objects.isNull(user)) {
            return new ResponseBean(200, "用户未注册", null);
        }
        val md5_password = this.md5.md5(password);
        if (user.getPassword().equals(md5_password)) {
            return new ResponseBean(200, "Login success", JWTUtil.sign(username, md5_password));
        } else {
            return new ResponseBean(200, "Login failed", null);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseBean register(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("role") String role) {
        if (!("Teacher".equalsIgnoreCase(role) || "Student".equalsIgnoreCase(role))) {
            return new ResponseBean(401, "越权注册", null);
        }
        val role_entity = roleService.getIdByInfo(role);
        val register_user = new User(null, username, password, role_entity.getId(), role_entity);
        if (userService.register(register_user)) {
            return new ResponseBean(200, "注册成功！", null);
        } else {
            return new ResponseBean(200, "注册失败", null);
        }

    }
}
