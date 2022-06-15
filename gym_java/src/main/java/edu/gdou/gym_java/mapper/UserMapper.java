package edu.gdou.gym_java.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.gdou.gym_java.entity.model.Role;
import edu.gdou.gym_java.entity.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-05-25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from User")
    @Results({
            @Result(property = "id",column = "id")
    })
    List<User> getUserList ();

    @Select("select * from User where name=#{name}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "roleId",column = "role_id"),
            @Result(property = "role",javaType = Role.class,column="role_id",
                    one = @One(select="edu.gdou.gym_java.mapper.RoleMapper.getById")),
    })
    User getUserByName(@Param("name")String name);

    @Select("select * from User where id=#{id}")
    @Results({
            @Result(property = "id",column = "id")
    })
    User getById(@Param("id")Long id);

    //根据用户名[可选]查询管理员用户
    List<User> queryManagerByName(String username);

    /**
     * 查询普通用户信息,不包含密码信息
     * @param page 分页
     * @return IPage
     */
    @Select("select id,name,role_id from User where role_id in (6,7)")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "roleId",column = "role_id"),
            @Result(property = "role",javaType = Role.class,column="role_id",
                    one = @One(select="edu.gdou.gym_java.mapper.RoleMapper.getById")),
    })
    IPage<User> selectPageUsers(Page page);


    @Select("select * from UserInfo where uid=#{uid}")
    Map<String,Object> selectInfoByUid(@Param("uid")int uid);

    @Select("select * from UserInfo where id=#{id}")
    Map<String,Object> selectInfoById(@Param("id")String id);

    @Insert("insert into UserInfo(uid, uname) values (#{uid},#{name})")
    Boolean insertUserInfo(@Param("uid")int uid,@Param("name")String name);

    @Insert("update UserInfo set uid=#{uid}, uname=#{name} where id=#{id}")
    Boolean updateUserInfo(@Param("id")String id,@Param("uid")int uid,@Param("name")String name);

    @Insert({"<script>",
            "insert into UserInfo(<foreach collection=\"map\" item=\"value\" index=\"key\" separator=\",\">",
            "${key} </foreach> )",
            "values (",
            "<foreach collection=\"map\" item=\"value\" index=\"key\" separator=\",\">",
            "#{value} </foreach> )",
            "</script>"})
    int exportInfo(@Param("map")Map<String,String> map);

    @Update({"<script>",
            "update UserInfo set <foreach collection=\"map\" item=\"value\" index=\"key\" separator=\",\">",
            "${key}=#{value} </foreach>",
            "where id=#{id}",
            "</script>"})
    int updateInfo(@Param("id") String id,@Param("map")Map<String,String> map);

    @Select("SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE table_name = 'UserInfo'")
    Set<String> getUserInfoRow();

    @Select({
            "<script>",
            "select User.id,name,truename from User",
            "left join UserInfo ui on uid=User.id ",
            "<where>",
            "<if test='uid !=null'>",
            "uid=#{uid}",
            "</if>",
            "<if test='name !=null'>",
            " and name=#{name}",
            "</if>",
            "<if test='truename !=null'>",
            " and truename=#{truename}",
            "</if>",
            "</where>",
            "order by User.id",
            "</script>"
    })
    Set<Map<String,Object>> getUserListBySingle(Integer uid,String name,String truename);
}
