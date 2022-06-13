package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ylhy
 * @since 2022-05-31
 */
@Getter
@Setter
@TableName("field_type")
@ApiModel(value = "FieldType对象", description = "")
public class FieldType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("场地类型id")
    @TableId(value = "tid", type = IdType.AUTO)
    private Integer tid;

    @ApiModelProperty("场地类型名称")
    @TableField("type_name")
    private String typeName;

    @ApiModelProperty("场地类型删除标识符")
    @TableField("is_del")
    private Integer isDel;

    @TableField(exist = false)
    private List<Field> fieldList;

}
