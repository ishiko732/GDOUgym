package edu.gdou.gym_java.service.serviceImpl;

import edu.gdou.gym_java.entity.model.Role;
import edu.gdou.gym_java.mapper.RoleMapper;
import edu.gdou.gym_java.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-05-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public Role getIdByInfo(String name) {
        return getBaseMapper().getIdByName(name);
    }

    @Override
    public Set<Role> listRoles() {
        return getBaseMapper().listRoles();
    }

    @Override
    public Set<String> getPermissionsById(int id) {
        return getBaseMapper().getPermissionByRid(id);
    }
}
