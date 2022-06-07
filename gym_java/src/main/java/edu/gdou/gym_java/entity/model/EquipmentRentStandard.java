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
 * 器材费用标准表
 * </p>
 *
 * @author lzh
 * @since 2022-06-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Equipment_rent_standard")
@ApiModel(value = "EquipmentRentStandard对象", description = "")
public class EquipmentRentStandard {
    @ApiModelProperty("器材租用标准id")
    @TableId(value = "erid", type = IdType.AUTO)
    private Integer erid;

    @ApiModelProperty("器材ID")
    @TableField(value="eid")
    private Integer eid;

    @ApiModelProperty("器材名称")
    @TableField(value="eid")
    private String name;

    @ApiModelProperty("器材租用费用")
    @TableField(value="eid")
    private Double price;

    @ApiModelProperty("器材租用时间")
    @TableField(value="rentTime")
    private Integer rentTime;
}
