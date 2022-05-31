package edu.gdou.gym_java.service;

import edu.gdou.gym_java.entity.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-05-25
 */
public interface RoleService extends IService<Role> {
    Role getIdByInfo(String name);
    Set<Role> listRoles();
    Set<String> getPermissionsById(int id);
}
