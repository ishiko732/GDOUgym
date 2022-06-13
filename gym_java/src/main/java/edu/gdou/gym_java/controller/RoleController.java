package edu.gdou.gym_java.controller;

import edu.gdou.gym_java.entity.bean.ResponseBean;
import edu.gdou.gym_java.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-05-31
 */
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = "/listRoles",method = {RequestMethod.GET,RequestMethod.POST})
    @RequiresPermissions(logical = Logical.AND, value = {"查询管理员信息"})
    public ResponseBean listRoles(){
        return new ResponseBean(200,"角色和权限信息",roleService.listRoles());
    }


    @RequestMapping(value = "/permissions",method = {RequestMethod.GET,RequestMethod.POST})
    @RequiresPermissions(logical = Logical.AND, value = {"查询管理员信息"})
    public ResponseBean permissionsByRid(@RequestParam("rid")String rid){
        val role_id = Integer.parseInt(rid);
        return new ResponseBean(200,"权限信息",roleService.getPermissionsById(role_id));
    }
}
