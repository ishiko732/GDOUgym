package edu.gdou.gym_java.entity.model;

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
 * 器材回收表
 * </p>
 *
 * @author lzh
 * @since 2022-06-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Recycle_equipment")
@ApiModel(value = "Recycle_equipment", description = "")
public class RecycleEquipment {
    @ApiModelProperty("器材回收reid")
    @TableId(value = "reid")
    private Integer reid;

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
