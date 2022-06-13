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

import java.util.Date;

/**
 * <p>
 * 器材租用表
 * </p>
 *
 * @author lzh
 * @since 2022-06-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Rent_equipment")
@ApiModel(value = "Rent_equipment", description = "")
public class RentEquipment {
    @ApiModelProperty("器材租用rid")
    @TableId(value = "rid", type = IdType.AUTO)
    private Integer rid;

    @ApiModelProperty("器材eid")
    @TableField(value="eid")
    private Integer eid;

    @ApiModelProperty("器材名称")
    @TableField(value="eName")
    private String eName;

    @ApiModelProperty("用户uid")
    @TableField(value="uid")
    private Integer uid;

    @ApiModelProperty("用户名username")
    @TableField(value="username")
    private String username;

    @ApiModelProperty("租用时间（小时）")
    @TableField(value="rentTime")
    private Integer rentTime;

    @ApiModelProperty("租用数量")
    @TableField(value="number")
    private Integer number;

    @ApiModelProperty("器材租用日期")
    @TableField(value="rentDate")
    private Date rentDate;

    @ApiModelProperty("器材归还状态")
    @TableField(value="status")
    private Integer status;
}
