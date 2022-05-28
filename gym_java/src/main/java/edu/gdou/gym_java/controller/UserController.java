package edu.gdou.gym_java.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import edu.gdou.gym_java.entity.bean.ResponseBean;
import edu.gdou.gym_java.entity.model.MyPage;
import edu.gdou.gym_java.entity.model.User;
import edu.gdou.gym_java.service.RoleService;
import edu.gdou.gym_java.service.UserService;
import edu.gdou.gym_java.testDemo.test;
import edu.gdou.gym_java.utils.JWTUtil;
import edu.gdou.gym_java.utils.MD5;
import lombok.val;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @RequestMapping(value = "/currentUser",method=RequestMethod.GET)
    @RequiresAuthentication
    public ResponseBean currentUser(){
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String username = JWTUtil.getUsername(token);
        assert username != null;
        User user = userService.getUser(username);
        user.setPassword(null);
        return new ResponseBean(200, "当前登录的用户信息", user);
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
    @PostMapping("/queryManagerByName")
    public ResponseBean queryManagerByName(@RequestParam("username")String username){
        List<User> users = userService.queryManagerByUsername(username);
        return new ResponseBean(200,users.size()>0?"查询成功":"查询结果为空",users);
    }

    @PostMapping("/addManager")
    @RequiresRoles("超级管理员")
    public ResponseBean addManager(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("role") String role){
        if (("Teacher".equalsIgnoreCase(role) || "Student".equalsIgnoreCase(role))) {
            return new ResponseBean(401, "选择的权限非管理员", null);
        }
        val role_entity = roleService.getIdByInfo(role);
        val manager = new User(null, username, password, role_entity.getId(), role_entity);
        if (userService.addManager(manager)) {
            return new ResponseBean(200, "管理员添加成功！", null);
        } else {
            return new ResponseBean(200, "管理员添加失败", null);
        }
    }

    @PostMapping("/deleteManager")
    @RequiresRoles("超级管理员")
    public ResponseBean deleteManager(@RequestParam("ID")String ID){
        int managerID = Integer.parseInt(ID);
        User user = userService.queryUserByID(managerID);
        if (user!=null && user.getRoleId()!=6 && user.getRoleId()!=7){
            if(userService.deleteManager(managerID)){
                return new ResponseBean(200, "管理员删除成功！", null);
            }else {
                return new ResponseBean(200, "管理员删除失败", null);
            }
        }else {
            return new ResponseBean(401, "该用户不存在或者该用户不是管理员", null);
        }
    }


    /**
     * 查询普通用户信息，不包括密码
     * @return ResponseBean
     */
    @PostMapping("/queryUsers")
    public ResponseBean queryUsers() {
        MyPage<User> myPage = new MyPage<User>(1, 5).setSelectInt(20);
        IPage<User> userMyPage = userService.selectUserPage(myPage);
//        System.out.println("总条数:" + userMyPage.getTotal());
//        System.out.println("当前页数: " + userMyPage.getCurrent());
//        System.out.println("当前每页显示数:" + userMyPage.getSize());
        val users = userMyPage.getRecords();
        return new ResponseBean(200, "获取到的用户信息", users);
    }
}
