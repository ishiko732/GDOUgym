package edu.gdou.gym_java.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import edu.gdou.gym_java.entity.bean.ResponseBean;
import edu.gdou.gym_java.entity.model.MyPage;
import edu.gdou.gym_java.entity.model.User;
import edu.gdou.gym_java.service.RoleService;
import edu.gdou.gym_java.service.UserService;
import edu.gdou.gym_java.shiro.redis.Constant;
import edu.gdou.gym_java.shiro.redis.JedisUtil;
import edu.gdou.gym_java.shiro.jwt.JWTUtil;
import edu.gdou.gym_java.utils.MD5;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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
@Slf4j
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final MD5 md5;
    private final Gson gson;

    public UserController(UserService userService, RoleService roleService, MD5 md5, Gson gson) {
        this.userService = userService;
        this.roleService = roleService;
        this.md5 = md5;
        this.gson = gson;
    }
    @RequestMapping(value = "/currentUser",method=RequestMethod.GET)
    @RequiresAuthentication
    public ResponseBean currentUser(){
        val user = userService.currentUser();
        return new ResponseBean(200, "当前登录的用户信息", user);
    }
    /**
     * 获取当前用户的信息
     * @return ResponseBean
     */
    @RequestMapping(value = "/currentUserInfo",method = {RequestMethod.GET,RequestMethod.POST})
    @RequiresAuthentication
    public ResponseBean currentUserInfoByUid(){
        val user = userService.currentUser();
        val map = userService.selectInfoByUid(user.getId());
        if (map.containsKey("uname")){
            return new ResponseBean(200, "获取到的用户信息("+map.get("name")+")", map);
        }else{
            return new ResponseBean(200, "未获取到用户信息", null);
        }
    }

    /**
     * 登录 （普通用户或管理员登录）
     * @param username 用户名
     * @param password 密码
     * @return ResponseBean
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseBean login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        User user = userService.getUser(username);
        if (Objects.isNull(user)) {
            return new ResponseBean(200, "用户未注册", null);
        }
        val md5_password = this.md5.md5(password);
        if (user.getPassword().equals(md5_password)) {
            // 清除可能存在的Shiro权限信息缓存
            if(JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN+user.getName())){
                JedisUtil.delKey(Constant.PREFIX_SHIRO_REFRESH_TOKEN+user.getName());
            }
            if (JedisUtil.exists(Constant.PREFIX_SHIRO_ACCESS_TOKEN+user.getName())){
                JedisUtil.delKey(Constant.PREFIX_SHIRO_ACCESS_TOKEN+user.getName());
            }
            val token = JWTUtil.sign(username, md5_password);
            return new ResponseBean(200, "Login success", token);
        } else {
            log.info("用户尝试登录失败：账号"+username+"密码："+password);
            return new ResponseBean(200, "Login failed", null);
        }
    }

    /**
     * 注册
     * @param username 用户名
     * @param password 密码
     * @param id 学工号 需要验证学号信息并绑定
     * @param role 角色
     * @return ResponseBean
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseBean register(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("id") String id,
                                 @RequestParam("role") String role) {
        if (!("Teacher".equalsIgnoreCase(role) || "Student".equalsIgnoreCase(role))) {
            return new ResponseBean(401, "越权注册", null);
        }
        User user = userService.getUser(username);
        if (Objects.nonNull(user)) {
            return new ResponseBean(200, "用户已注册", null);
        }
        val role_entity = roleService.getIdByInfo(role);
        val register_user = new User(null, username, password, role_entity.getId(), role_entity,null);
        val aBoolean = userService.register(register_user, id);
        if(aBoolean == null){
            log.info("用户尝试注册失败：账号"+username+"，密码："+password+"，角色："+role+"学号："+id);
            return new ResponseBean(200, "学工号不在系统内部，请联系用户管理员添加", null);
        }else if(aBoolean){
            return new ResponseBean(200, "注册成功！", null);
        } else {
            log.info("用户尝试注册失败：账号"+username+"，密码："+password+"，角色："+role+"学号："+id);
            return new ResponseBean(200, "注册失败", null);
        }
    }

    /**
     * 导入用户信息
     * @param excel excel文件
     * @param map 导入信息（单条）
     * @return ResponseBean
     */
    @PostMapping("/exportUser")
    @RequiresPermissions(logical = Logical.AND, value = {"导入学生信息", "导入教师信息"})
    public ResponseBean excelReader(@RequestParam(value = "file",required = false) MultipartFile excel,
                                    @RequestParam(value="map",required = false) String map) {
        if (excel!=null){
            val map1 = userService.exportInfoByFile(excel);
            return new ResponseBean(200,map1!=null?"导入信息":"导入失败",map1);
        }
        if(map!=null){
            val mapArrayList = new ArrayList<Map<String,String>>();
            Object message = gson.fromJson(map, Object.class);
            if(message instanceof List){
                return new ResponseBean(200,"导入失败,类型错误",null);
            }
            val map2 = gson.fromJson(map, Map.class);
            if((map2.containsKey("id") && (map2.get("id") instanceof String))){
                mapArrayList.add(map2);
                val map1 = userService.exportInfo(mapArrayList);
                return new ResponseBean(200,map1!=null?"导入信息":"导入失败",map1);
            } else{
                return new ResponseBean(200,"导入失败,未获取到有效的数据", "所有类型都必须为String类型");
            }
        }
        return new ResponseBean(200,"导入失败",null);
    }

    /**
     * 修改密码
     * @param username 用户名 (没有强制权限，只能修改自身的用户密码)
     * @param prePassword 原先密码 (如果当前用户权限包括强制修改密码可跳过验证)
     * @param newPassword 新密码
     * @return 修改逻辑
     */
    @RequestMapping(value = "changePassword",method = RequestMethod.POST)
    @RequiresPermissions(logical = Logical.OR, value = {"修改密码", "修改密码_强制"})
    public ResponseBean changePasswordByUsername(@RequestParam("username")String username,
                                                 @RequestParam(value = "pre",required = false)String prePassword,
                                                 @RequestParam(value = "new")String newPassword){
        val currentUser = userService.currentUser();
        boolean isForced = currentUser.getRole().getPermissions().contains("修改密码_强制");
        String name = isForced?username:currentUser.getName();
        val ret =userService.changePassword(name,prePassword,newPassword,isForced);
        return new ResponseBean(200,ret?"修改成功":"验证原密码失败","修改的用户为:"+name);
    }

    @RequestMapping(value = "/queryManagerByName",method = {RequestMethod.GET,RequestMethod.POST})
    @RequiresPermissions(logical = Logical.AND, value = {"查询管理员信息"})
    public ResponseBean queryManagerByName(@RequestParam(value = "username",required = false)String username){
        List<User> users = userService.queryManagerByUsername(username);
        Collections.sort(users);
        for (User user : users) {
            val objectMap = userService.selectInfoByUid(user.getId());
            user.setRole(roleService.getById(user.getRoleId()));
            user.setInfo(objectMap);
        }
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
        val manager = new User(null, username, password, role_entity.getId(), role_entity,null);
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
     * 更新角色
     * @param ID 用户id
     * @param RID 角色id
     * @return ResponseBean
     */
    @RequestMapping(value = "changeRole",method = RequestMethod.POST)
    @RequiresPermissions("更新管理员角色")
    public ResponseBean updateRole(@RequestParam("ID")String ID,@RequestParam("RID")String RID){
        int managerID = Integer.parseInt(ID);
        int roleID =Integer.parseInt(RID);
        User user = userService.queryUserByID(managerID);
        if (user!=null && roleID>=1 && roleID<=7){
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",user.getId()).set("role_id",roleID);
            if (userService.update(null,updateWrapper)){
                return new ResponseBean(200, "修改角色信息成功！", null);
            }else{
                return new ResponseBean(200, "修改角色信息失败！", null);
            }
        }else {
            return new ResponseBean(401, "该用户不存在", null);
        }
    }



    /**
     * 查询普通用户信息，不包括密码
     * @return ResponseBean
     */
    @RequestMapping(value = "/queryUsers",method = RequestMethod.GET)
    @RequiresAuthentication
    public ResponseBean queryUsers(@RequestParam("current") Integer current,
                                   @RequestParam("size")Integer size,
                                   @RequestParam("cnt")Integer cnt) {
        MyPage<User> myPage = new MyPage<User>(current, size).setSelectInt(cnt);
        IPage<User> userMyPage = userService.selectUserPage(myPage);
        Map<String,Object> map =new HashMap<>();
        map.put("total",userMyPage.getTotal());
        map.put("currentPage",userMyPage.getCurrent());
        map.put("currentSize",userMyPage.getSize());
        map.put("pages",userMyPage.getPages());
        val users = userMyPage.getRecords();
        Collections.sort(users);
        map.put("users",users);
        val infos = new ArrayList<Map<String,Object>>();
        for (User user : users) {
            val objectMap = userService.selectInfoByUid(user.getId());
            infos.add(objectMap);
        }
        map.put("infos",infos);
        return new ResponseBean(200, "获取到的用户信息", map);
    }

    /**
     * 获取用户的信息
     * @param ID uid
     * @return ResponseBean
     */
    @RequestMapping(value = "/queryUserInfo",method = {RequestMethod.GET,RequestMethod.POST})
    @RequiresPermissions("查询用户个人信息")
    public ResponseBean queryUserInfoByUid(@RequestParam("ID")String ID){
        val map = userService.selectInfoByUid(Integer.parseInt(ID));
        if (map!=null && map.containsKey("uname")){
            return new ResponseBean(200, "获取到的用户信息("+map.get("uname")+")", map);
        }else{
            return new ResponseBean(200, "未获取到用户信息", null);
        }
    }
    /**
     * 获取用户的信息
     * @param ID uid
     * @return ResponseBean
     */
    @RequestMapping(value = "/queryUserInfoById",method = {RequestMethod.GET,RequestMethod.POST})
    @RequiresPermissions("查询用户个人信息")
    public ResponseBean queryUserInfoById(@RequestParam("ID")String ID){
        val map = userService.selectInfoById(ID);
        if (map!=null && map.containsKey("uname")){
            return new ResponseBean(200, "获取到的用户信息("+map.get("uname")+")", map);
        }else{
            return new ResponseBean(200, "未获取到用户信息", null);
        }
    }

    // shiro
    /**
     * 注销登录
     * @return ResponseBean
     */
    @RequestMapping(value = "/logout",method = {RequestMethod.GET,RequestMethod.POST})
    @RequiresAuthentication
    public ResponseBean logout(){
        // 清除可能存在的Shiro权限信息缓存
        val user = userService.currentUser();
        if(JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN+user.getName())){
            JedisUtil.delKey(Constant.PREFIX_SHIRO_REFRESH_TOKEN+user.getName());
        }
        if (JedisUtil.exists(Constant.PREFIX_SHIRO_ACCESS_TOKEN+user.getName())){
            JedisUtil.delKey(Constant.PREFIX_SHIRO_ACCESS_TOKEN+user.getName());
        }
        SecurityUtils.getSubject().logout();
        return new ResponseBean(200, "注销成功", null);
    }
    @RequestMapping(value = "/newToken",method = {RequestMethod.GET,RequestMethod.POST})
    @RequiresAuthentication
    public ResponseBean newToken(){
        val httpServletRequest = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        val oldToken = httpServletRequest.getHeader("Authorization");
        val hashMap = new HashMap<String, String>();
        val username = JWTUtil.getUsername(oldToken);
        val newToken = JedisUtil.getJson(Constant.PREFIX_SHIRO_ACCESS_TOKEN + username);
        hashMap.put("old_token",oldToken);
        hashMap.put("new_token",newToken);
        hashMap.put("new_token_create_time",JWTUtil.getCreateTime(newToken));
        return new ResponseBean(200,"新token",hashMap);
    }

    /**
     * 获取简单的用户信息
     */
    @RequestMapping(value = "/getSingleUser",method = {RequestMethod.GET})
    @RequiresAuthentication
    public ResponseBean getSingle(@RequestParam(value = "uid",required = false)String uid,
                                  @RequestParam(value = "username",required = false)String username,
                                  @RequestParam(value = "truename",required = false)String truename){
        return new ResponseBean(200,"用户数据",userService.getUserListBySingle(uid==null?null:Integer.parseInt(uid),username,truename));
    }

    /**
     * 获取用户信息通过用户名
     */
    @RequestMapping(value = "/getUserByName",method = {RequestMethod.GET})
    @RequiresAuthentication
    public ResponseBean getUserByName(@RequestParam("username")String username){
        val user = userService.getUser(username);
        user.setPassword(null);
        return new ResponseBean(200,"用户数据", user);
    }

}

