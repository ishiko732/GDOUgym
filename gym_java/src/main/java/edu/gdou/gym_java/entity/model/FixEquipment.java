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
 * 器材维修表
 * </p>
 *
 * @author lzh
 * @since 2022-06-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Fix_equipment")
@ApiModel(value = "Fix_equipment", description = "")
public class FixEquipment {
    @ApiModelProperty("器材id")
    @TableId(value = "fid")
    private Integer fid;

    @ApiModelProperty("器材名称")
    @TableField(value="name")
    private String name;

    @ApiModelProperty("器材种类")
    @TableField(value="type")
    private String type;

    @ApiModelProperty("报修数量")
    @TableField(value="number")
    private Integer number;
}
