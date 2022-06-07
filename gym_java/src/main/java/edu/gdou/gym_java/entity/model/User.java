package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-05-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("User")
@ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable,Comparable<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名")
    @TableField("name")
    private String name;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("用户类型")
    @TableField("role_id")
    private Integer roleId;

    @TableField(exist = false)
    private Role role;

    @Override
    public int compareTo(User o) {
        return this.getId()-o.getId();
    }

    @TableField(exist = false)
    private Map<String,Object> info;
}
