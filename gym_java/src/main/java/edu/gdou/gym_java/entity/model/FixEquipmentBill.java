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

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Fix_equipment_bill")
@ApiModel(value = "Fix_equipment_bill", description = "")
public class FixEquipmentBill {
    @ApiModelProperty("器材维修账单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("器材id")
    @TableField("eid")
    private Integer eid;;

    @ApiModelProperty("器材名称")
    @TableField("number")
    private Integer number;;

    @ApiModelProperty("器材租用费用")
    @TableField("price")
    private Double price;

    @ApiModelProperty("器材维修时间")
    @TableField("fixDate")
    private Date fixDate;

}
