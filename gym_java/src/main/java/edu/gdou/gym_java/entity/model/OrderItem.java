package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
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
@TableName("order_item")
@ApiModel(value = "OrderItem对象", description = "")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("预约订单主键")
    @TableId(value="oid",type = IdType.AUTO)
    private Integer oid;

    @ApiModelProperty("审核表id")
    @TableField("fcid")
    private Integer fcid;

    @TableField(exist = false)
    private TimeArrange timeArrange;

    @ApiModelProperty("订单状态")
    @TableField("order_status")
    private LocalDate orderStatus;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private String createTime;


}
