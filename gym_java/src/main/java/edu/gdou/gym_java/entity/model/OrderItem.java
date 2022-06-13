package edu.gdou.gym_java.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author ylhy
 * @since 2022-05-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @ApiModelProperty("安排表id")
    @TableField("time_id")
    private Integer timeId;


}
