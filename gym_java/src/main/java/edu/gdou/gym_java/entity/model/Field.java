package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("field")
@ApiModel(value = "Field对象", description = "")
public class Field implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("场地id")
    @TableId("fid")
    private Integer fid;

    @TableField(exist = false)
    private FieldType fieldType;

    @ApiModelProperty("收费标准")
    @TableField("tid")
    private Integer tid;

    @ApiModelProperty("收费标准")
    @TableField("money")
    private Double money;

    @ApiModelProperty("场地描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("场地数量")
    @TableField("num")
    private Integer num;

    @ApiModelProperty("场地删除标识符")
    @TableField("is_del")
    private Integer isDel;

}
