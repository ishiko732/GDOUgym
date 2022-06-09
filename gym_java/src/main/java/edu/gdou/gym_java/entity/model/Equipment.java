package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * <p>
 * 器材表
 * </p>
 *
 * @author lzh
 * @since 2022-06-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Equipment")
@ApiModel(value = "Equipment对象", description = "")
public class Equipment {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("器材id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("器材名称")
    @TableField(value="name")
    private String name;

    @ApiModelProperty("器材类型")
    @TableField(value = "types")
    private String types;

    @ApiModelProperty("器材数量")
    @TableField(value = "number")
    private Integer number;

    @ApiModelProperty("器材价格")
    @TableField(value = "price")
    private Double price;

    @ApiModelProperty("器材价格")
    @TableField(value = "rentPrice")
    private Double rentPrice;

    @ApiModelProperty("器材价格")
    @TableField(value = "maxRentTime")
    private Integer maxRentTime;
}
