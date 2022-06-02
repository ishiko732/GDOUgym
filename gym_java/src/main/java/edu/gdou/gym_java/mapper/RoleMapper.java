package edu.gdou.gym_java.mapper;

import edu.gdou.gym_java.entity.model.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.Set;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-05-25
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select * from role where id=#{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "permissions", javaType = Set.class, column = "id",
                    many = @Many(select = "edu.gdou.gym_java.mapper.RoleMapper.getPermissionByRid"))
    })
    Role getById(@Param("id") Integer id);


    @Select("select pname from permission join role as r on (pid & r.permissions)= pid where r.id=#{id}")
    Set<String> getPermissionByRid(@Param("id") Integer id);

    @Select("select * from role where info=#{name}")
    Role getIdByName(@Param("name") String name);

    @Select("select * from role")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "permissions", javaType = Set.class, column = "id",
                    many = @Many(select = "edu.gdou.gym_java.mapper.RoleMapper.getPermissionByRid"))
    })
    Set<Role> listRoles();
}
